package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Browser
import com.wutsi.tracking.domain.Device
import com.wutsi.tracking.domain.OS
import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.service.pipeline.Step
import ua_parser.Client
import ua_parser.Parser

class StepUserAgent : Step {
    companion object {
        private const val MOBILE = "Mobile"
    }

    private val uaParser = Parser()

    override fun process(track: Track) {
        extractUserAgentInfo(track)
    }

    private fun extractUserAgentInfo(track: Track) {
        val client = uaParser.parse(track.userAgent)

        track.device = Device(
            family = nullToEmpty(client.device?.family),
            type = deviceType(client)
        )
        track.os = OS(
            name = nullToEmpty(client.os?.family),
            version = version(client.os)
        )
        track.browser = Browser(
            name = browserName(client),
            version = nullToEmpty(client.userAgent?.major)
        )
        track.bot = isBot(client)
    }

    private fun deviceType(client: Client): String {
        if (isBot(client)) {
            return "other"
        }

        val family = client.userAgent.family
        if (family.indexOf(MOBILE) >= 0) {
            return "mobile"
        } else {
            return "desktop"
        }
    }

    private fun browserName(client: Client): String {
        val family = client.userAgent.family
        val len = MOBILE.length
        if (family.startsWith(MOBILE)) {
            return family.substring(len).trim()
        } else if (family.endsWith(MOBILE)) {
            return family.substring(0, family.length - len).trim()
        }
        return family
    }

    private fun version(os: ua_parser.OS?) = nullToEmpty(os?.major)

    private fun isBot(client: Client) = "Spider" == client.device?.family

    private fun nullToEmpty(str: String?) = str ?: ""
}
