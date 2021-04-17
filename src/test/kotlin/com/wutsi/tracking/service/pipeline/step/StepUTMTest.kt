package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Track
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class StepUTMTest {
    private val step = StepUTM()

    @Test
    fun processWithoutURL() {
        val track = createTrack(null)
        step.process(track)

        assertNull(track.source)
        assertNull(track.medium)
        assertNull(track.campaign)
    }

    @Test
    fun processWithURLWithNoUTMParameter() {
        val track = createTrack("https://www.google.com")
        step.process(track)

        assertNull(track.source)
        assertNull(track.medium)
        assertNull(track.campaign)
    }

    @Test
    fun processWithBadURL() {
        val track = createTrack("invalid")
        step.process(track)

        assertNull(track.source)
        assertNull(track.medium)
        assertNull(track.campaign)
    }

    @Test
    fun processWithURLWithUTMParameter() {
        val track = createTrack("https://www.google.com?utm_source=app&utm_medium=pwa&utm_campaign=test")
        step.process(track)

        assertEquals("app", track.source)
        assertEquals("pwa", track.medium)
        assertEquals("test", track.campaign)
    }

    private fun createTrack(url: String?) = Track(url = url)
}
