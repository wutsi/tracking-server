package com.wutsi.tracking.service.pipeline

import com.wutsi.tracking.domain.Track

open class Pipeline(private val steps: List<Step>) : Step {
    override fun process(track: Track) {
        steps.forEach { it.process(track) }
    }
}
