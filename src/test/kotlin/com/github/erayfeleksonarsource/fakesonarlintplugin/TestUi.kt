package com.github.erayfeleksonarsource.fakesonarlintplugin

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.Locator
import com.intellij.remoterobot.search.locators.byXpath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class TestUi {

    @Test
    fun checkFileText() = run {
        var remoteRobot = RemoteRobot("http://localhost:8082") // check that this is the default port

        var fileMenuLocator: Locator = byXpath("//div[contains(@text.key, 'group.FileMenu.text')]")

        var fileMenu = remoteRobot.find(ComponentFixture::class.java, fileMenuLocator)

        assertThat(fileMenu.hasText("File"))

        fileMenu.click()
    }

}
