package com.github.bgomar.bgconsolelogger.tools;

/**
 * Persistable bean for per-file-type chapter patterns.
 * Used by ConsoleLoggerSettings for chapter panel presets (e.g. .tex vs .java).
 */
public class ChapterPresetBean {
    public String fileType = "";
    public String chapter = "";
    public String section = "";
    public String subsection = "";
    public String chapterPatternName = "";
    public String sectionPatternName = "";
    public String subsectionPatternName = "";

    @SuppressWarnings("unused")
    public ChapterPresetBean() {
    }

    public ChapterPresetBean(String fileType, String chapter, String section, String subsection,
                             String chapterPatternName, String sectionPatternName, String subsectionPatternName) {
        this.fileType = fileType != null ? fileType : "";
        this.chapter = chapter != null ? chapter : "";
        this.section = section != null ? section : "";
        this.subsection = subsection != null ? subsection : "";
        this.chapterPatternName = chapterPatternName != null ? chapterPatternName : "";
        this.sectionPatternName = sectionPatternName != null ? sectionPatternName : "";
        this.subsectionPatternName = subsectionPatternName != null ? subsectionPatternName : "";
    }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType != null ? fileType : ""; }

    public String getChapter() { return chapter; }
    public void setChapter(String chapter) { this.chapter = chapter != null ? chapter : ""; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section != null ? section : ""; }

    public String getSubsection() { return subsection; }
    public void setSubsection(String subsection) { this.subsection = subsection != null ? subsection : ""; }

    public String getChapterPatternName() { return chapterPatternName; }
    public void setChapterPatternName(String chapterPatternName) { this.chapterPatternName = chapterPatternName != null ? chapterPatternName : ""; }

    public String getSectionPatternName() { return sectionPatternName; }
    public void setSectionPatternName(String sectionPatternName) { this.sectionPatternName = sectionPatternName != null ? sectionPatternName : ""; }

    public String getSubsectionPatternName() { return subsectionPatternName; }
    public void setSubsectionPatternName(String subsectionPatternName) { this.subsectionPatternName = subsectionPatternName != null ? subsectionPatternName : ""; }
}
