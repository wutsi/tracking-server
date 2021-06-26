package com.wutsi.tracking.service

import com.wutsi.tracking.dto.Browser
import com.wutsi.tracking.dto.Device
import com.wutsi.tracking.dto.OS
import com.wutsi.tracking.dto.Track
import com.wutsi.tracking.dto.TrafficType.seo
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

internal class TrackWriterTest {
    @Test
    fun write() {
        val track = createTrack()
        val out = ByteArrayOutputStream()

        TrackWriter().write(arrayListOf(track), out)
        System.out.println(out)

        val expected = """
            "time","hitid","deviceid","userid","page","event","productid","value","os","osversion","devicetype","browser","ip","long","lat","traffic","referer","bot","ua","source","medium","campaign","url","siteid","impressions"
            "3333","123","sample-device","","SR","pageview","1234","1","Windows","7","desktop","IE","1.1.2.3","111.0","222.0","seo","https://www.google.ca","false","Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)","app","pwa","test","https://www.wutsi.com/read/123/this-is-nice?utm_source=email&utm_campaign=test&utm_medium=email","1","11|12|13"
        """

        assertEquals(expected.trimIndent(), out.toString().trimIndent())
    }

    private fun createTrack() = Track(
        userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)",
        hitId = "123",
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
        campaign = "test",
        siteid = "1",
        impressions = "11|12|13"
    )
}
