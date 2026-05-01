package com.github.bgomar.bgconsolelogger.tools;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@State(name = "ConsoleLoggerSettings", storages = {@Storage("consolelogger.xml")})
public final class ConsoleLoggerSettings implements PersistentStateComponent<ConsoleLoggerSettings> {

    public static final String DEFAULT_PATTERN_1 = "console.log(\"%c 1 --> {LN}||{FN}\\n $$: \",\"color:#f0f;\", $$);";
    public static final String DEFAULT_PATTERN_2 = "console.log(\"%c 2 --> {LN}||{FN}\\n $$: \",\"color:#0f0;\", $$);";
    public static final String DEFAULT_PATTERN_3 = "console.log(\"%c 3 --> {LN}||{FN}\\n $$: \",\"color:#ff0;\", $$);";
    public static final String DEFAULT_PATTERN_4 = "console.log(\"%c 4 --> {LN}||{FN}\\n $$: \",\"color:#f00;\", $$);";
    public static final String DEFAULT_PATTERN_5 = "console.log(\"%c 5 --> {LN}||{FN}\\n $$: \",\"color:#0ff;\", $$);";
    public static final String DEFAULT_PATTERN_6 = "console.log(\"%c 6 --> {LN}||{FN}\\n $$: \",\"color:#00f;\", $$);";
    public static final String DEFAULT_PATTERN_7 = "console.log(\"%c 7 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";
    public static final String DEFAULT_PATTERN_8 = "console.log(\"%c 8 --> {LN}||{FN}\\n $$: \",\"color:#fca;\", $$);";
    public static final String DEFAULT_PATTERN_9 = "console.log(\"%c 9 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";

    public static final String DEFAULT_PATTERN_10 = "console.log(\"%c 10 --> {LN}||{FN}\\n $$: \",\"color:#f0f;\", $$);";
    public static final String DEFAULT_PATTERN_11 = "console.log(\"%c 11 --> {LN}||{FN}\\n $$: \",\"color:#0f0;\", $$);";
    public static final String DEFAULT_PATTERN_12 = "console.log(\"%c 12 --> {LN}||{FN}\\n $$: \",\"color:#ff0;\", $$);";
    public static final String DEFAULT_PATTERN_13 = "console.log(\"%c 13 --> {LN}||{FN}\\n $$: \",\"color:#f00;\", $$);";
    public static final String DEFAULT_PATTERN_14 = "console.log(\"%c 14 --> {LN}||{FN}\\n $$: \",\"color:#0ff;\", $$);";
    public static final String DEFAULT_PATTERN_15 = "console.log(\"%c 15 --> {LN}||{FN}\\n $$: \",\"color:#00f;\", $$);";
    public static final String DEFAULT_PATTERN_16 = "console.log(\"%c 16 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";
    public static final String DEFAULT_PATTERN_17 = "console.log(\"%c 17 --> {LN}||{FN}\\n $$: \",\"color:#fca;\", $$);";
    public static final String DEFAULT_PATTERN_18 = "console.log(\"%c 18 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";

    public static final String ACTIVE_PATTERN_1 = "console.log(\"%c 1 --> {LN}||{FN}\\n $$: \",\"color:#f0f;\", $$);";
    public static final String ACTIVE_PATTERN_2 = "console.log(\"%c 2 --> {LN}||{FN}\\n $$: \",\"color:#0f0;\", $$);";
    public static final String ACTIVE_PATTERN_3 = "console.log(\"%c 3 --> {LN}||{FN}\\n $$: \",\"color:#ff0;\", $$);";
    public static final String ACTIVE_PATTERN_4 = "console.log(\"%c 4 --> {LN}||{FN}\\n $$: \",\"color:#f00;\", $$);";
    public static final String ACTIVE_PATTERN_5 = "console.log(\"%c 5 --> {LN}||{FN}\\n $$: \",\"color:#0ff;\", $$);";
    public static final String ACTIVE_PATTERN_6 = "console.log(\"%c 6 --> {LN}||{FN}\\n $$: \",\"color:#00f;\", $$);";
    public static final String ACTIVE_PATTERN_7 = "console.log(\"%c 7 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";
    public static final String ACTIVE_PATTERN_8 = "console.log(\"%c 8 --> {LN}||{FN}\\n $$: \",\"color:#fca;\", $$);";
    public static final String ACTIVE_PATTERN_9 = "console.log(\"%c 9 --> {LN}||{FN}\\n $$: \",\"color:#acf;\", $$);";

    public static final String CHAPTER_PATTERN = "//";
    public static final String CHAPTER_PATTERN_NAME = "Comment: ";
    public static final String SECTION_PATTERN = "\\section";
    public static final String SECTION_PATTERN_NAME = "Sec: ";
    public static final String SUBSECT_PATTERN = "\\subsection";
    public static final String SUBSECT_PATTERN_NAME = "Sub: ";

    private static final int DEFAULT_PATTERN_COUNT = 33;

    public List<String> patterns = new ArrayList<>(Arrays.asList(
            ACTIVE_PATTERN_1,
            ACTIVE_PATTERN_2,
            ACTIVE_PATTERN_3,
            ACTIVE_PATTERN_4,
            ACTIVE_PATTERN_5,
            ACTIVE_PATTERN_6,
            ACTIVE_PATTERN_7,
            ACTIVE_PATTERN_8,
            ACTIVE_PATTERN_9,
            DEFAULT_PATTERN_1,
            DEFAULT_PATTERN_2,
            DEFAULT_PATTERN_3,
            DEFAULT_PATTERN_4,
            DEFAULT_PATTERN_5,
            DEFAULT_PATTERN_6,
            DEFAULT_PATTERN_7,
            DEFAULT_PATTERN_8,
            DEFAULT_PATTERN_9,
            DEFAULT_PATTERN_10,
            DEFAULT_PATTERN_11,
            DEFAULT_PATTERN_12,
            DEFAULT_PATTERN_13,
            DEFAULT_PATTERN_14,
            DEFAULT_PATTERN_15,
            DEFAULT_PATTERN_16,
            DEFAULT_PATTERN_17,
            DEFAULT_PATTERN_18,
            CHAPTER_PATTERN,
            SECTION_PATTERN,
            SUBSECT_PATTERN,
            CHAPTER_PATTERN_NAME,
            SECTION_PATTERN_NAME,
            SUBSECT_PATTERN_NAME
    ));

    /** Per-file-type chapter presets (e.g. "tex", "java"). Key is file extension without dot. */
    public List<ChapterPresetBean> chapterPresetsByFileType = new ArrayList<>();

    public String version = "0.0.35";

    public static ConsoleLoggerSettings getInstance() {
        ConsoleLoggerSettings settings = ApplicationManager.getApplication().getService(ConsoleLoggerSettings.class);
        if (settings.patterns.size() > DEFAULT_PATTERN_COUNT) {
            settings.patterns = settings.patterns.subList(0, DEFAULT_PATTERN_COUNT);
        }
        return settings;
    }

    @Override
    public ConsoleLoggerSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ConsoleLoggerSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    // Modify getPattern to retrieve from the List
    public static String getPattern(int index) {
        ConsoleLoggerSettings settings = getInstance();
        if (index >= 0 && index < settings.patterns.size()) {
            return settings.patterns.get(index);
        } else {
            return settings.patterns.get(settings.patterns.size() - 1);  // Default to last if out of bounds
        }
    }

    public static int getLogPatternsCount() {
        ConsoleLoggerSettings settings = getInstance();
        return settings.patterns.size() -1;  // Default to last if out of bounds

    }

    // Modify setPattern to update the List
    public static void setPattern(int index, String pattern) {
        ConsoleLoggerSettings settings = getInstance();
        if (index >= 0 && index < settings.patterns.size()) {
            settings.patterns.set(index, pattern);
        } else if (settings.patterns.size() < DEFAULT_PATTERN_COUNT) {
            settings.patterns.add(pattern);  // Add to the list if index is out of bounds and size is less than default
        } else {
            System.out.println("Cannot add more patterns. The list has reached its default limit.");
        }
    }

    // ----- Per-file-type chapter presets -----

    /** Normalize file type to extension without leading dot, lowercase. */
    public static String normalizeFileType(String fileType) {
        if (fileType == null || fileType.isEmpty()) return "";
        String ext = fileType.trim().toLowerCase();
        if (ext.startsWith(".")) ext = ext.substring(1);
        return ext;
    }

    /** Built-in defaults for TypeScript, JavaScript, SCSS, etc. when no saved preset exists. */
    private static ChapterPresetBean getBuiltInChapterPresetForFileType(String key) {
        switch (key) {
            case "ts":
            case "tsx":
            case "js":
            case "jsx":
                // Section comments: // ====== , // ----- , // ---
                return new ChapterPresetBean(key, "// =", "// -", "// ", "Ch:", "Sec:", "Sub:");
            case "scss":
            case "sass":
            case "css":
                // SCSS/CSS: // or /* section markers
                return new ChapterPresetBean(key, "// =", "// -", "// ", "Ch:", "Sec:", "Sub:");
            case "tex":
                return new ChapterPresetBean(key, "\\chapter", "\\section", "\\subsection", "Ch:", "Sec:", "Sub:");
            default:
                return null;
        }
    }

    /** Get chapter preset for the given file type. Order: saved preset → built-in (ts/js/scss/…) → global. */
    public static ChapterPresetBean getChapterPresetForFileType(String fileType) {
        ConsoleLoggerSettings settings = getInstance();
        String key = normalizeFileType(fileType);
        if (!key.isEmpty()) {
            for (ChapterPresetBean bean : settings.chapterPresetsByFileType) {
                if (key.equals(normalizeFileType(bean.getFileType()))) {
                    return new ChapterPresetBean(bean.getFileType(), bean.getChapter(), bean.getSection(),
                            bean.getSubsection(), bean.getChapterPatternName(), bean.getSectionPatternName(),
                            bean.getSubsectionPatternName());
                }
            }
            ChapterPresetBean builtIn = getBuiltInChapterPresetForFileType(key);
            if (builtIn != null) return builtIn;
        }
        return new ChapterPresetBean(key,
                getPattern(27), getPattern(28), getPattern(29),
                getPattern(30), getPattern(31), getPattern(32));
    }

    /** Save chapter preset for the given file type. */
    public static void setChapterPresetForFileType(String fileType, ChapterPresetBean preset) {
        if (preset == null) return;
        ConsoleLoggerSettings settings = getInstance();
        String key = normalizeFileType(fileType);
        if (key.isEmpty()) return;
        preset.setFileType(key);
        for (int i = 0; i < settings.chapterPresetsByFileType.size(); i++) {
            if (key.equals(normalizeFileType(settings.chapterPresetsByFileType.get(i).getFileType()))) {
                settings.chapterPresetsByFileType.set(i, preset);
                return;
            }
        }
        settings.chapterPresetsByFileType.add(preset);
    }

    /** File types that have a saved preset, for UI dropdown. */
    public static List<String> getChapterPresetFileTypes() {
        ConsoleLoggerSettings settings = getInstance();
        return settings.chapterPresetsByFileType.stream()
                .map(ChapterPresetBean::getFileType)
                .filter(ft -> ft != null && !ft.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}