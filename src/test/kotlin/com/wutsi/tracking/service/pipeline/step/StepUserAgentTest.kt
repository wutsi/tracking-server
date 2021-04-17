package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Track
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class StepUserAgentTest {
    private val step = StepUserAgent()

    @Test
    fun testSafariMobile() {
        val track =
            createTrack("Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/11.0 Mobile/14E304 Safari/602.1")
        step.process(track)

        assertEquals("Safari", track.browser.name)
        assertEquals("11", track.browser.version)
        assertEquals("iOS", track.os.name)
        assertEquals("10", track.os.version)
        assertEquals("iPhone", track.device.family)
        assertEquals("mobile", track.device.type)
        assertFalse(track.bot)
    }

    @Test
    fun testChomeDesktop() {
        val track = createTrack("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
        step.process(track)

        assertEquals("Chrome", track.browser.name)
        assertEquals("51", track.browser.version)
        assertEquals("Linux", track.os.name)
        assertEquals("", track.os.version)
        assertEquals("Other", track.device.family)
        assertEquals("desktop", track.device.type)
        assertFalse(track.bot)
    }

    @Test
    fun testChomeAndroid() {
        val track = createTrack("Mozilla/5.0 (Linux; Android 8.0.0;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36")
        step.process(track)

        assertEquals("Chrome", track.browser.name)
        assertEquals("80", track.browser.version)
        assertEquals("Android", track.os.name)
        assertEquals("8", track.os.version)
        assertEquals("Generic Smartphone", track.device.family)
        assertEquals("mobile", track.device.type)
        assertFalse(track.bot)
    }

    @Test
    fun testWindowsMobile() {
        val track = createTrack("Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)")
        step.process(track)

        assertEquals("IE", track.browser.name)
        assertEquals("9", track.browser.version)
        assertEquals("Windows Phone", track.os.name)
        assertEquals("7", track.os.version)
        assertEquals("Generic Smartphone", track.device.family)
        assertEquals("mobile", track.device.type)
        assertFalse(track.bot)
    }

    @Test
    fun testGooglebot() {
        val track = createTrack("Googlebot/2.1 (+http://www.google.com/bot.html)")
        step.process(track)

        assertEquals("Googlebot", track.browser.name)
        assertEquals("2", track.browser.version)
        assertEquals("Other", track.os.name)
        assertEquals("", track.os.version)
        assertEquals("Spider", track.device.family)
        assertEquals("other", track.device.type)
        assertTrue(track.bot)
    }

    private fun createTrack(ua: String) = Track(
        userAgent = ua
    )
}
