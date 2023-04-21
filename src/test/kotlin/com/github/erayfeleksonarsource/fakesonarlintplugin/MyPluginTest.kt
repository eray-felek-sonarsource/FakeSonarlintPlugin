package com.github.erayfeleksonarsource.fakesonarlintplugin

import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HIGHLIGHT_TOPIC
import com.github.erayfeleksonarsource.fakesonarlintplugin.listeners.HighlightListener
import com.github.erayfeleksonarsource.fakesonarlintplugin.services.HighlightExternalAnnotator
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.components.service
import com.intellij.psi.xml.XmlFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
import com.github.erayfeleksonarsource.fakesonarlintplugin.services.MyProjectService
import com.intellij.util.messages.MessageBusConnection
import com.intellij.util.messages.Topic
import org.junit.Assert

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase(), HighlightListener {
    fun testMessageBus() {
        val connection = project.messageBus.connect()
        connection.subscribe(HIGHLIGHT_TOPIC, this)

        myFixture.configureByFile("Asonar.java")
        myFixture.checkHighlighting()
    }

    fun testHighlighting() {
        myFixture.configureByFile("Asonar.java")

        myFixture.checkHighlighting()
    }

    override fun notifyCount(count: Int) {
        Assert.assertEquals(3, count)
    }

    override fun getTestDataPath() = "src/test/testData/MyPluginTest"
}
