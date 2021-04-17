package com.wutsi.tracking.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.storage.StorageService
import com.wutsi.tracking.domain.Browser
import com.wutsi.tracking.domain.Device
import com.wutsi.tracking.domain.OS
import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.domain.TrafficType.seo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.InputStream

internal class TrackPersisterTest {
    @Test
    fun `store track`() {
        val storage: StorageService = mock()

        val track1 = createTrack("111")
        val track2 = createTrack("222")

        TrackPersister(TrackWriter(), storage).persist(listOf(track1, track2))

        val path = argumentCaptor<String>()
        val input = argumentCaptor<InputStream>()
        verify(storage).store(path.capture(), input.capture(), eq("text/csv"), eq(Int.MAX_VALUE))

        assertTrue(path.firstValue.endsWith(".csv"))
    }

    @Test
    fun `donot store empty track`() {
        val storage: StorageService = mock()
        val writer: TrackWriter = mock()

        TrackPersister(writer, storage).persist(emptyList())

        verify(storage, never()).store(any(), any(), any(), any())
    }

    private fun createTrack(hitId: String) = Track(
        userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)",
        hitId = hitId,
        bot = false,
        transactionId = "tx-id",
        event = "pageview",
        productId = "1234",
        page = "SR",
        value = "1",
        longitude = 111.0,
        latitude = 222.0,
        ip = "1.1.2.3",
        deviceId = "sample-device",
        userId = null,
        browser = Browser(name = "IE", version = "9"),
        os = OS(name = "Windows", version = "7"),
        device = Device(family = "", type = "desktop"),
        time = 3333,
        trafficType = seo,
        referer = "https://www.google.ca",
        url = "https://www.wutsi.com/read/123/this-is-nice?utm_source=email&utm_campaign=test&utm_medium=email",
        source = "app",
        medium = "pwa",
        campaign = "test"
    )
}
