package com.github.erayfeleksonarsource.fakesonarlintplugin

import com.github.erayfeleksonarsource.fakesonarlintplugin.services.HighlightExternalAnnotator
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.components.service
import com.intellij.psi.xml.XmlFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
import com.github.erayfeleksonarsource.fakesonarlintplugin.services.MyProjectService

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {
    fun testMessageBus() {
        val connection = project.messageBus.connect()
        //connection.subscribe(HighlightExternalAnnotator.HIGLIGHT_TOPIC, this)
    }

    fun testHighlighting() {
        myFixture.configureByFile("Asonar.java")

        myFixture.checkHighlighting()
    }

    override fun getTestDataPath() = "src/test/testData/MyPluginTest"
}
