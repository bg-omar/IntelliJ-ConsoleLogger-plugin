package com.github.bgomar.consolelogger

import com.github.bgomar.bgconsolelogger.tools.ChapterPresetBean
import com.github.bgomar.bgconsolelogger.tools.ConsoleLoggerSettings
import com.intellij.openapi.ui.DialogWrapper
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class ChapterSettingsDialog(
    initialChapter: String = "",
    initialSection: String = "",
    initialSubsection: String = "",
    initialChapterPatternName: String = "",
    initialSectionPatternName: String = "",
    initialSubsectionPatternName: String = "",
    currentFileType: String = ""
) : DialogWrapper(true) {
    val chapterTextField = JTextField(initialChapter, 30)
    val sectionTextField = JTextField(initialSection, 30)
    val subsectionTextField = JTextField(initialSubsection, 30)
    val chapterPatternNameTextField = JTextField(initialChapterPatternName, 15)
    val sectionPatternNameTextField = JTextField(initialSectionPatternName, 15)
    val subsectionPatternNameTextField = JTextField(initialSubsectionPatternName, 15)

    private val fileTypeCombo: JComboBox<String>
    private val fileTypeModel: DefaultComboBoxModel<String>

    init {
        title = "Chapter Panel Settings"
        val normalizedCurrent = ConsoleLoggerSettings.normalizeFileType(currentFileType)
        val savedTypes = ConsoleLoggerSettings.getChapterPresetFileTypes()
        fileTypeModel = DefaultComboBoxModel<String>().apply {
            addElement(DEFAULT_LABEL)
            if (normalizedCurrent.isNotEmpty() && !savedTypes.any { ConsoleLoggerSettings.normalizeFileType(it) == normalizedCurrent }) {
                addElement(if (normalizedCurrent == currentFileType) currentFileType else ".$normalizedCurrent")
            }
            savedTypes.forEach { addElement(it) }
        }
        fileTypeCombo = JComboBox(fileTypeModel).apply {
            preferredSize = java.awt.Dimension(120, height)
        }
        val toSelect = when {
            normalizedCurrent.isEmpty() -> DEFAULT_LABEL
            else -> (0 until fileTypeModel.size).map { fileTypeModel.getElementAt(it) }
                .firstOrNull { ConsoleLoggerSettings.normalizeFileType(it) == normalizedCurrent }
                ?: DEFAULT_LABEL
        }
        fileTypeCombo.selectedItem = toSelect
        loadPresetForSelectedFileType()
        fileTypeCombo.addActionListener { loadPresetForSelectedFileType() }
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints().apply {
            insets = Insets(5, 5, 5, 5)
            fill = GridBagConstraints.HORIZONTAL
        }
        gbc.gridx = 0
        gbc.gridy = 0
        panel.add(JLabel("For file type:"), gbc)
        gbc.gridx = 1
        gbc.gridwidth = 2
        panel.add(fileTypeCombo, gbc)
        gbc.gridwidth = 1
        gbc.gridy++
        gbc.gridx = 0
        panel.add(JLabel("Chapter:"), gbc)
        gbc.gridx = 1
        panel.add(chapterTextField, gbc)
        gbc.gridx = 2
        panel.add(chapterPatternNameTextField, gbc)

        gbc.gridy++
        gbc.gridx = 0
        panel.add(JLabel("Section:"), gbc)
        gbc.gridx = 1
        panel.add(sectionTextField, gbc)
        gbc.gridx = 2
        panel.add(sectionPatternNameTextField, gbc)

        gbc.gridy++
        gbc.gridx = 0
        panel.add(JLabel("Subsection:"), gbc)
        gbc.gridx = 1
        panel.add(subsectionTextField, gbc)
        gbc.gridx = 2
        panel.add(subsectionPatternNameTextField, gbc)

        return panel
    }

    private fun loadPresetForSelectedFileType() {
        val selected = fileTypeCombo.selectedItem?.toString() ?: return
        val preset = if (selected == DEFAULT_LABEL) {
            ChapterPresetBean(
                "",
                ConsoleLoggerSettings.getPattern(27),
                ConsoleLoggerSettings.getPattern(28),
                ConsoleLoggerSettings.getPattern(29),
                ConsoleLoggerSettings.getPattern(30),
                ConsoleLoggerSettings.getPattern(31),
                ConsoleLoggerSettings.getPattern(32)
            )
        } else {
            ConsoleLoggerSettings.getChapterPresetForFileType(selected)
        }
        chapterTextField.text = preset.chapter
        sectionTextField.text = preset.section
        subsectionTextField.text = preset.subsection
        chapterPatternNameTextField.text = preset.chapterPatternName
        sectionPatternNameTextField.text = preset.sectionPatternName
        subsectionPatternNameTextField.text = preset.subsectionPatternName
    }

    override fun doOKAction() {
        val selected = fileTypeCombo.selectedItem?.toString() ?: DEFAULT_LABEL
        val preset = ChapterPresetBean(
            ConsoleLoggerSettings.normalizeFileType(selected),
            chapterTextField.text,
            sectionTextField.text,
            subsectionTextField.text,
            chapterPatternNameTextField.text,
            sectionPatternNameTextField.text,
            subsectionPatternNameTextField.text
        )
        if (selected == DEFAULT_LABEL) {
            ConsoleLoggerSettings.setPattern(27, preset.chapter)
            ConsoleLoggerSettings.setPattern(28, preset.section)
            ConsoleLoggerSettings.setPattern(29, preset.subsection)
            ConsoleLoggerSettings.setPattern(30, preset.chapterPatternName)
            ConsoleLoggerSettings.setPattern(31, preset.sectionPatternName)
            ConsoleLoggerSettings.setPattern(32, preset.subsectionPatternName)
        } else {
            ConsoleLoggerSettings.setChapterPresetForFileType(selected, preset)
        }
        super.doOKAction()
    }

    fun getChapter() = chapterTextField.text
    fun getSection() = sectionTextField.text
    fun getSubsection() = subsectionTextField.text
    fun getChapterPatternName() = chapterPatternNameTextField.text
    fun getSectionPatternName() = sectionPatternNameTextField.text
    fun getSubsectionPatternName() = subsectionPatternNameTextField.text

    /** Selected file type for this session ("" means Default). */
    fun getSelectedFileType(): String {
        val s = fileTypeCombo.selectedItem?.toString() ?: return ""
        return if (s == DEFAULT_LABEL) "" else ConsoleLoggerSettings.normalizeFileType(s)
    }

    companion object {
        private const val DEFAULT_LABEL = "Default (all files)"
    }
}
