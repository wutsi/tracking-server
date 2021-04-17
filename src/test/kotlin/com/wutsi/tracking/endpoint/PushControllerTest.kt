package com.wutsi.tracking.endpoint

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.dto.PushTrackRequest
import com.wutsi.tracking.dto.PushTrackResponse
import com.wutsi.tracking.service.pipeline.Pipeline
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PushControllerTest {
    @LocalServerPort
    private val port = 0

    private lateinit var url: String

    private val rest: RestTemplate = RestTemplate()

    @MockBean
    private lateinit var pipeline: Pipeline

    @BeforeEach
    fun setUp() {
        url = "http://localhost:$port/v1/tracks"
    }

    @Test
    operator fun invoke() {
        val request = createRequest("xxx", 11)
        val response = rest.postForEntity(url, request, PushTrackResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        val arg = argumentCaptor<Track>()
        verify(pipeline).process(arg.capture())

        val track = arg.firstValue
        assertNotNull(track)
        assertEquals(request.hid, track.hitId)
        assertEquals(request.time, track.time)
        assertEquals(request.duid, track.deviceId)
        assertEquals(request.uid, track.userId)
        assertEquals(request.pid, track.productId)
        assertEquals(request.referer, track.referer)
        assertEquals(request.lat, track.latitude)
        assertEquals(request.long, track.longitude)
        assertEquals(request.ua, track.userAgent)
        assertEquals(request.value, track.value)
        assertEquals(request.ip, track.ip)
    }

    private fun createRequest(event: String, userId: Long? = null) = PushTrackRequest(
        time = System.currentTimeMillis(),
        duid = UUID.randomUUID().toString(),
        uid = userId?.toString(),
        event = event,
        ip = "192.168.1.12",
        page = "page.SR",
        pid = System.currentTimeMillis().toString(),
        value = "3",
        lat = 0.32093,
        long = 1.12121,
        hid = UUID.randomUUID().toString(),
        ua = "Mozilla/5.0 (Linux; Android 8.0.0;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36",
        url = "https://www.google.com?utm_source=app&utm_medium=pwa&utm_campaign=test",
        referer = "https://www.google.ca"
    )
}
