package com.wutsi.tracking.event

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.stream.Event
import com.wutsi.stream.ObjectMapperBuilder
import com.wutsi.tracking.delegate.PushDelegate
import com.wutsi.tracking.dto.PushTrackRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EventHandlerTest {
    private lateinit var delegate: PushDelegate
    private lateinit var handler: EventHandler

    @BeforeEach
    fun setUp() {
        delegate = mock()
        handler = EventHandler(delegate)
    }

    @Test
    fun handleTrackEvent() {
        val request = PushTrackRequest(
            url = "http://www.google.ca",
            value = "xxx"
        )
        val event = Event(
            type = TrackingEventType.TRACK_SUBMITTED.urn,
            payload = ObjectMapperBuilder().build().writeValueAsString(TrackSubmittedEventPayload(request))
        )
        handler.onEvent(event)

        verify(delegate).invoke(request)
    }
}
