package com.github.bgomar.bgconsolelogger.chapters

import com.github.bgomar.bgconsolelogger.tools.ConsoleLoggerSettings
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiFile

object ChapterCollector {
    @JvmStatic  // ✅ Makes it accessible from Java
    fun collectChapters(file: PsiFile): List<Chapter> {
        val chapters = mutableListOf<Chapter>()
        val document: Document = file.viewProvider.document ?: return chapters

        // ✅ Use per-file-type preset, fallback to global patterns
        val fileType = file.virtualFile?.extension ?: ""
        val preset = ConsoleLoggerSettings.getChapterPresetForFileType(fileType)
        val chapterPattern = preset.chapter.trim()
        val sectionPattern = preset.section.trim()
        val subsectionPattern = preset.subsection.trim()
        val chapterPatternName = preset.chapterPatternName.trim()
        val sectionPatternName = preset.sectionPatternName.trim()
        val subsectionPatternName = preset.subsectionPatternName.trim()

        val lines = document.text.split("\n")
        for ((index, line) in lines.withIndex()) {
            val trimmedLine = line.trim()
            when {
                chapterPattern.isNotEmpty() && trimmedLine.startsWith(chapterPattern) -> {
                    val title = trimmedLine.removePrefix(chapterPattern).trim()
                    chapters.add(Chapter("$chapterPatternName $title", index + 1, Chapter.Type.CHAPTER))
                }
                sectionPattern.isNotEmpty() && trimmedLine.startsWith(sectionPattern) -> {
                    val title = trimmedLine.removePrefix(sectionPattern).trim()
                    chapters.add(Chapter("$sectionPatternName $title", index + 1, Chapter.Type.SECTION))
                }
                subsectionPattern.isNotEmpty() && trimmedLine.startsWith(subsectionPattern) -> {
                    val title = trimmedLine.removePrefix(subsectionPattern).trim()
                    chapters.add(Chapter("$subsectionPatternName $title", index + 1, Chapter.Type.SUBSECTION))
                }
            }
        }
        return chapters
    }
}