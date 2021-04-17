package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.service.pipeline.Step
import java.net.URL
import java.net.URLDecoder
import java.util.LinkedHashMap

open class StepUTM : Step {
    override fun process(track: Track) {
        val params = extractParams(track)
        track.source = params["utm_source"]
        track.medium = params["utm_medium"]
        track.campaign = params["utm_campaign"]
    }

    private fun extractParams(track: Track): Map<String, String?> {
        val url = track.url
            ?: return emptyMap()

        try {
            val params = LinkedHashMap<String, String>()
            val query = URL(url).query
            val pairs = query.split("&".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (pair in pairs) {
                val idx = pair.indexOf("=")
                val name = URLDecoder.decode(pair.substring(0, idx), "UTF-8")
                val value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
                params[name] = value
            }
            return params
        } catch (ex: Exception) {
            return emptyMap()
        }
    }
}
