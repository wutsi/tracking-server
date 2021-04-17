package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.domain.TrafficType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StepTrafficTypeTest {
    private val step = StepTrafficType()

    @Test
    fun nullReferer() {
        val track = createTrack(null)
        step.process(track)
        assertEquals(TrafficType.direct, track.trafficType)
    }

    @Test
    fun emptyReferer() {
        val track = createTrack("")
        step.process(track)
        assertEquals(TrafficType.direct, track.trafficType)
    }

    @Test
    fun unknownReferer() {
        val track = createTrack("????")
        step.process(track)
        assertEquals(TrafficType.unknown, track.trafficType)
    }

    @Test
    fun googleReferer() {
        val track = createTrack("https://www.google.ca")
        step.process(track)
        assertEquals(TrafficType.seo, track.trafficType)
    }

    @Test
    fun gmailReferer() {
        val track = createTrack("https://mail.google.com/m/fdkfdkjfkdj")
        step.process(track)
        assertEquals(TrafficType.email, track.trafficType)
    }

    @Test
    fun hotmailReferer() {
        val track = createTrack("https://outlook.live.com/msg/fdfdkjfdkj")
        step.process(track)
        assertEquals(TrafficType.email, track.trafficType)
    }

    @Test
    fun facebookReferer() {
        val track = createTrack("https://www.facebook.com/post/349304903")
        step.process(track)
        assertEquals(TrafficType.social_media, track.trafficType)
    }

    @Test
    fun twitterReferer() {
        val track = createTrack("https://www.twitter.com")
        step.process(track)
        assertEquals(TrafficType.social_media, track.trafficType)
    }

    @Test
    fun linkedInReferer() {
        val track = createTrack("https://www.linkedin.com/p/0950490594")
        step.process(track)
        assertEquals(TrafficType.social_media, track.trafficType)
    }

    @Test
    fun facebookMobileReferer() {
        val track = createTrack("https://m.facebook.com")
        step.process(track)
        assertEquals(TrafficType.social_media, track.trafficType)
    }

    private fun createTrack(referer: String?) = Track(
        referer = referer
    )
}
