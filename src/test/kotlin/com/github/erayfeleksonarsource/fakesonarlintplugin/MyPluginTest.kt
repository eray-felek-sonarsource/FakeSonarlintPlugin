package com.github.erayfeleksonarsource.fakesonarlintplugin

import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HIGHLIGHT_TOPIC
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {
    @Test
    fun testMessageBus() {
        val listener = HighlightListenerAccumulator()

        val connection = project.messageBus.connect()
        connection.subscribe(HIGHLIGHT_TOPIC, listener)

        var psiFile = myFixture.configureByText("Sample.java", """public class Sample { int heisenberg1 = 1;
            |int heisenberg12 = 12;}""".trimMargin())

        myFixture.doHighlighting()

        assertThat(listener.receivedCount).containsExactly(2)

        var psiFile2 = myFixture.configureByText("Sample2.java", """public class Sample2 { int heisenberg1 = 1;
            |int heisenberg12 = 12;
            |int heisenberg123 = 123;}""".trimMargin())

        myFixture.doHighlighting()

        assertThat(listener.receivedCount).containsExactly(2, 3)
    }

    @Test
    fun testHighlighting() {
        myFixture.configureByFile("Asonar.java")

        myFixture.checkHighlighting()
    }

    override fun getTestDataPath() = "src/test/testData/MyPluginTest"
}
