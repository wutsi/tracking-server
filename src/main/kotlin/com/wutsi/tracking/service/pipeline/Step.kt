package com.wutsi.tracking.service.pipeline

import com.wutsi.tracking.domain.Track

interface Step {
    fun process(track: Track)
}
