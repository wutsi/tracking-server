package com.wutsi.tracking.endpoint

import com.wutsi.tracking.`delegate`.PushDelegate
import com.wutsi.tracking.dto.PushTrackRequest
import com.wutsi.tracking.dto.PushTrackResponse
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class PushController(
    private val `delegate`: PushDelegate
) {
    @PostMapping("/v1/tracks")
    public fun invoke(@Valid @RequestBody request: PushTrackRequest): PushTrackResponse =
        delegate.invoke(request)
}
