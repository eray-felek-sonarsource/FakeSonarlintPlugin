package com.github.erayfeleksonarsource.fakesonarlintplugin.services

import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HIGHLIGHT_TOPIC
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.ui.JBColor
import java.awt.Font

class HighlightExternalAnnotator : ExternalAnnotator<String, List<TextRange>>() {

    val HIGHLIGHT_TEXT = "heisenberg"

    override fun collectInformation(file: PsiFile): String? {
        return file.text
    }

    override fun doAnnotate(collectedInfo: String?): List<TextRange> {
        if (collectedInfo.isNullOrEmpty()) return emptyList()

        var startIndex = 0
        val textRanges = mutableListOf<TextRange>()

        var index = getIndex(collectedInfo, startIndex)

        while (index != -1) {
            val endIndex = index.plus(HIGHLIGHT_TEXT.length)

            startIndex = endIndex
            textRanges.add(TextRange(index, endIndex))

            index = getIndex(collectedInfo, startIndex)
        }

        return textRanges;
    }

    private fun getIndex(collectedInfo: String, startIndex: Int): Int =
            collectedInfo.indexOf(HIGHLIGHT_TEXT, startIndex, ignoreCase = true)

    override fun apply(file: PsiFile, annotationResult: List<TextRange>?, holder: AnnotationHolder) {
        val count = if (annotationResult.isNullOrEmpty()) 0 else annotationResult.size

        file.project.messageBus.syncPublisher(HIGHLIGHT_TOPIC).notifyCount(count)

        if (count == 0) {
            return
        } else {
            annotationResult!!.forEach {
                annotate(holder, it)
            }
        }
    }

    private fun annotate(holder: AnnotationHolder, range: TextRange) {
        holder.newAnnotation(HighlightSeverity.WEAK_WARNING, "Unresolved property")
                .range(range)
                .textAttributes(TextAttributesKey.createTextAttributesKey("HEISENBERG_HIGHLIGHT", TextAttributes(JBColor.blue, JBColor.CYAN, JBColor.blue, EffectType.LINE_UNDERSCORE,
                        Font.PLAIN)))
                .highlightType(ProblemHighlightType.GENERIC_ERROR)
                .create()
    }
}