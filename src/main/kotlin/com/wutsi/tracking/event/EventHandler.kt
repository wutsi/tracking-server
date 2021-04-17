package com.wutsi.tracking.event

import com.wutsi.stream.Event
import com.wutsi.stream.ObjectMapperBuilder
import com.wutsi.tracking.delegate.PushDelegate
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class EventHandler(private val pushDelegate: PushDelegate) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(EventHandler::class.java)
    }

    @EventListener
    fun onEvent(event: Event) {
        LOGGER.info("onEvent(${event.type},...)")

        if (TrackingEventType.TRACK_SUBMITTED.urn == event.type) {
            val payload = ObjectMapperBuilder().build().readValue(event.payload, TrackSubmittedEventPayload::class.java)
            pushDelegate.invoke(payload.request)
        } else {
            LOGGER.info("Event ignored")
        }
    }
}
