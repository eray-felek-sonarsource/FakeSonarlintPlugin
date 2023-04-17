package com.github.erayfeleksonarsource.fakesonarlintplugin.services

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType.IDENTIFIER
import com.intellij.psi.JavaTokenType.STRING_LITERAL
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import com.intellij.ui.JBColor
import org.jetbrains.annotations.NotNull
import java.awt.Font


class HighlightAnnotator : Annotator {
    override fun annotate(@NotNull element: PsiElement, @NotNull holder: AnnotationHolder) {
        val elementType = element.elementType;
        val value = element.text;

        if (validate(elementType, value)) return

        val indexes = fetchSubStringIndexes(element.text.lowercase())

        indexes.forEach { i ->
            val prefixRange = TextRange.from(element.textRange.startOffset + i, HIGHLIGHT_TEXT.length)
            highlight(holder, prefixRange)
        }

    }

    private fun validate(elementType: IElementType?, value: @NlsSafe String): Boolean {
        if ((elementType?.equals(STRING_LITERAL) == false) && elementType != IDENTIFIER) {
            return true
        }

        if (!value.toString().lowercase().contains(HIGHLIGHT_TEXT)) {
            return true
        }
        return false
    }

    private fun fetchSubStringIndexes(originalString: String): MutableList<Int> {
        val indexes: MutableList<Int> = ArrayList()

        var index = originalString.indexOf(HIGHLIGHT_TEXT)
        val subStringLength = HIGHLIGHT_TEXT.length

        indexes.add(index)

        while (index >= 0) {
            index = originalString.indexOf(HIGHLIGHT_TEXT, index + subStringLength)
            if (index >= 0) {
                indexes.add(index)
            }
        }

        return indexes;
    }

    private fun highlight(holder: AnnotationHolder, prefixRange: TextRange?) {
        if (prefixRange != null) {
            holder.newAnnotation(HighlightSeverity.WEAK_WARNING, "Unresolved property")
                    .range(prefixRange)
                    .textAttributes(createTextAttributesKey("SONAR_HIGHLIGHT", TextAttributes(JBColor.blue, JBColor.yellow, JBColor.blue, EffectType.LINE_UNDERSCORE,
                            Font.PLAIN)))
                    .highlightType(ProblemHighlightType.GENERIC_ERROR)
                    .create()
        }
    }

    companion object {
        const val HIGHLIGHT_TEXT = "sonar"
    }
}