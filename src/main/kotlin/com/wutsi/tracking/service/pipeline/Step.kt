package com.wutsi.tracking.service.pipeline

import com.wutsi.tracking.dto.Track

interface Step {
    fun process(track: Track)
}
