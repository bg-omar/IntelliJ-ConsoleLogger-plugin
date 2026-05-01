package com.github.bgomar.bgconsolelogger.toolwindow.setup;

import com.github.bgomar.bgconsolelogger.chapters.Chapter;
import com.github.bgomar.bgconsolelogger.chapters.ChapterCollector;
import com.github.bgomar.bgconsolelogger.tools.ChapterPresetBean;
import com.github.bgomar.bgconsolelogger.tools.ConsoleLoggerSettings;
import com.github.bgomar.consolelogger.ChapterSettingsDialog;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class ChapterToolSetup  implements Disposable {

    Logger logger = Logger.getLogger(getClass().getName());
    
    private final Project project;
    private final DefaultListModel<String> chapterListModel;
    private final JList<String> chapterList;
    private static JTextField chapterTextField = new JTextField();
    private final JTextField sectionTextField;
    private final JTextField subsectionTextField;
    private final JTextField chapterPatternNameTextField;
    private final JTextField sectionPatternNameTextField;
    private final JTextField subsectionPatternNameTextField;
    private final JButton chapterSettingsButton;


    public ChapterToolSetup(Project project, DefaultListModel<String> chapterListModel, JList<String> chapterList, JButton chapterSettingsButton, JTextField chapterTextField, JTextField sectionTextField, JTextField subsectionTextField, JTextField chapterPatternNameTextField, JTextField sectionPatternNameTextField, JTextField subsectionPatternNameTextField) {
        this.project = project;
        this.chapterListModel = chapterListModel;
        this.chapterList = chapterList;
        ChapterToolSetup.chapterTextField = chapterTextField;
        this.sectionTextField = sectionTextField;
        this.subsectionTextField = subsectionTextField;
        this.chapterPatternNameTextField = chapterPatternNameTextField;
        this.sectionPatternNameTextField = sectionPatternNameTextField;
        this.subsectionPatternNameTextField = subsectionPatternNameTextField;
        this.chapterSettingsButton = new JButton("Settings");
        this.chapterList.setCellRenderer(new ChapterListCellRenderer());
        // Add the button to the UI panel containing chapterPanel fields
        // Example: If you have a panel, add: panel.add(chapterSettingsButton);
    }


    public void setup() {
        updateChapterList();
        loadPresetForCurrentFileType();

        // Add DocumentListeners to refresh the chapter list and save preset for current file type
        DocumentListener presetUpdater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { savePresetFromFieldsAndRefresh(); }
            @Override
            public void removeUpdate(DocumentEvent e) { savePresetFromFieldsAndRefresh(); }
            @Override
            public void changedUpdate(DocumentEvent e) { savePresetFromFieldsAndRefresh(); }
        };
        chapterTextField.getDocument().addDocumentListener(presetUpdater);
        sectionTextField.getDocument().addDocumentListener(presetUpdater);
        subsectionTextField.getDocument().addDocumentListener(presetUpdater);
        chapterPatternNameTextField.getDocument().addDocumentListener(presetUpdater);
        sectionPatternNameTextField.getDocument().addDocumentListener(presetUpdater);
        subsectionPatternNameTextField.getDocument().addDocumentListener(presetUpdater);

        // ✅ Handle file selection changes
        project.getMessageBus().connect().subscribe(
                FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {
                    @Override
                    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                        logger.info("📂 File opened: " + file.getName());
                        loadPresetForCurrentFileType();
                        updateChapterList();
                    }

                    @Override
                    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                        VirtualFile newFile = event.getNewFile();
                        if (newFile != null) {
                            logger.info("🔄 Switched to file: " + newFile.getName());
                            loadPresetForCurrentFileType();
                            updateChapterList();
                        }
                    }

                    @Override
                    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                        // Handle file closed event if necessary
                    }
                }
        );

        // ✅ Handle clicks on the list items
        chapterList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // ✅ Double-click to navigate
                    String selectedTitle = chapterList.getSelectedValue();
                    if (selectedTitle != null) {
                        navigateToChapter(selectedTitle);
                    }
                }
            }
        });

        // ✅ Handle keyboard selection (Enter key)
        chapterList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String selectedTitle = chapterList.getSelectedValue();
                    if (selectedTitle != null) {
                        navigateToChapter(selectedTitle);
                    }
                }
            }
        });

        // Add settings button event listener
        chapterSettingsButton.addActionListener(e -> {
            String fileType = getCurrentFileType();
            ChapterPresetBean current = ConsoleLoggerSettings.getChapterPresetForFileType(fileType);
            ChapterSettingsDialog dialog = new ChapterSettingsDialog(
                current.getChapter(),
                current.getSection(),
                current.getSubsection(),
                current.getChapterPatternName(),
                current.getSectionPatternName(),
                current.getSubsectionPatternName(),
                fileType
            );
            dialog.show();
            if (dialog.isOK()) {
                loadPresetForCurrentFileType();
                updateChapterList();
            }
        });
    }

    private String getCurrentFileType() {
        PsiFile file = getCurrentFile();
        if (file == null || file.getVirtualFile() == null) return "";
        String ext = file.getVirtualFile().getExtension();
        return ext != null ? ext : "";
    }

    private void loadPresetForCurrentFileType() {
        String fileType = getCurrentFileType();
        ChapterPresetBean preset = ConsoleLoggerSettings.getChapterPresetForFileType(fileType);
        chapterTextField.setText(preset.getChapter());
        sectionTextField.setText(preset.getSection());
        subsectionTextField.setText(preset.getSubsection());
        chapterPatternNameTextField.setText(preset.getChapterPatternName());
        sectionPatternNameTextField.setText(preset.getSectionPatternName());
        subsectionPatternNameTextField.setText(preset.getSubsectionPatternName());
    }

    private void savePresetFromFieldsAndRefresh() {
        String fileType = getCurrentFileType();
        ChapterPresetBean preset = new ChapterPresetBean(
            fileType,
            chapterTextField.getText(),
            sectionTextField.getText(),
            subsectionTextField.getText(),
            chapterPatternNameTextField.getText(),
            sectionPatternNameTextField.getText(),
            subsectionPatternNameTextField.getText()
        );
        if (fileType.isEmpty()) {
            ConsoleLoggerSettings.setPattern(27, preset.getChapter());
            ConsoleLoggerSettings.setPattern(28, preset.getSection());
            ConsoleLoggerSettings.setPattern(29, preset.getSubsection());
            ConsoleLoggerSettings.setPattern(30, preset.getChapterPatternName());
            ConsoleLoggerSettings.setPattern(31, preset.getSectionPatternName());
            ConsoleLoggerSettings.setPattern(32, preset.getSubsectionPatternName());
        } else {
            ConsoleLoggerSettings.setChapterPresetForFileType(fileType, preset);
        }
        updateChapterList();
    }

    private void navigateToChapter(String selectedTitle) {
        PsiFile file = getCurrentFile();
        if (file == null) {
            logger.info("❌ No active file found.");
            return;
        }

        List<Chapter> chapters = ChapterCollector.collectChapters(file);
        for (Chapter chapter : chapters) {
            if (chapter.getTitle().equals(selectedTitle)) {
                navigateToLine(chapter.getLineNumber());
                break;
            }
        }
    }

    private void navigateToLine(int lineNumber) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor != null) {
            editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(lineNumber - 1, 0));
            editor.getScrollingModel().scrollToCaret(com.intellij.openapi.editor.ScrollType.CENTER);
        } else {
            logger.info("❌ No active editor found.");
        }
    }

    @Override
    public void dispose() {
        // ✅ Cleanup resources when the plugin is disposed
        logger.info("ChapterToolSetup disposed.");
    }

    public void updateChapterList() {
        logger.info("🔍 Checking for chapters...");
        PsiFile file = getCurrentFile();

        if (file == null) {
            logger.info("❌ No active file found. Waiting for a file to be opened.");
            return;
        }

        List<Chapter> chapters = ChapterCollector.collectChapters(file);

        chapterListModel.clear();
        if (chapters.isEmpty()) {
            logger.info("⚠️ No chapters detected in the current file.");
        } else {
            for (Chapter chapter : chapters) {
                chapterListModel.addElement(chapter.getTitle());
            }
        }

        // ✅ Ensure UI updates after the list changes
        SwingUtilities.invokeLater(() -> {
            chapterList.updateUI();
            chapterList.setSelectedIndex(0); // Optional: Auto-select first chapter
        });
    }




    private PsiFile getCurrentFile() {
        if (project == null) {
            logger.info("❌ Project is null!");
            return null;
        }

        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        if (fileEditorManager == null) {
            logger.info("❌ FileEditorManager is null!");
            return null;
        }

        Editor editor = fileEditorManager.getSelectedTextEditor();
        if (editor == null) {
            logger.info("❌ No active editor found!");
            return null;
        }

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
        if (psiFile == null) {
            logger.info("❌ No PSI file found for the current document.");
        } else {
            logger.info("✅ Current file: " + psiFile.getName());
        }
        return psiFile;
    }

    public class ChapterListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String text = value.toString();

            // High-contrast colors for dark backgrounds
            Color chapterColor = new Color(102, 178, 255);    // Light blue
            Color sectionColor = new Color(144, 238, 144);    // Light green
            Color subsectionColor = new Color(255, 200, 100); // Light orange
            Color defaultColor = new Color(220, 220, 220);    // Light gray

            // Get current pattern names from the text fields
            String chapterPattern = chapterPatternNameTextField.getText();
            String sectionPattern = sectionPatternNameTextField.getText();
            String subsectionPattern = subsectionPatternNameTextField.getText();

            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                if (!chapterPattern.isEmpty() && text.startsWith(chapterPattern)) {
                    label.setForeground(chapterColor);
                } else if (!sectionPattern.isEmpty() && text.startsWith(sectionPattern)) {
                    label.setForeground(sectionColor);
                } else if (!subsectionPattern.isEmpty() && text.startsWith(subsectionPattern)) {
                    label.setForeground(subsectionColor);
                } else {
                    label.setForeground(defaultColor);
                }
            }
            label.setOpaque(true);
            return label;
        }
    }
}