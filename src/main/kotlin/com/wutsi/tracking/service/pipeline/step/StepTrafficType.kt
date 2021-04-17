package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.domain.TrafficType
import com.wutsi.tracking.service.pipeline.Step

class StepTrafficType : Step {
    companion object {
        private val SEO_DOMAINS = arrayListOf(
            "google",
            "bing",
            "yahoo"
        )
        private val SOCIAL_DOMAINS = arrayListOf(
            "facebook.com",
            "twitter.com",
            "linkedin.com",
            "pinterest.com",
            "instagram.com",
            "whatsapp.com",
            "snapchat.com"
        )
        private val EMAIL_DOMAINS = arrayListOf(
            "mail.google.com",
            "mail.yahoo.com",
            "outlook.live.com"
        )
    }

    override fun process(track: Track) {
        val domain = extractDomainName(track.referer?.toLowerCase())
        track.trafficType = getTrafficType(domain)
    }

    private fun getTrafficType(domain: String): TrafficType {
        if (isDirect(domain)) {
            return TrafficType.direct
        } else if (isSocialMedia(domain)) {
            return TrafficType.social_media
        } else if (isEmail(domain)) {
            return TrafficType.email
        } else if (isSEO(domain)) {
            return TrafficType.seo
        }
        return TrafficType.unknown
    }

    private fun isDirect(domain: String) = domain.isEmpty()

    private fun isSEO(domain: String) = SEO_DOMAINS.find { domain.startsWith(it) } != null

    private fun isSocialMedia(domain: String) = SOCIAL_DOMAINS.find { domain.contains(it) } != null

    private fun isEmail(domain: String) = EMAIL_DOMAINS.contains(domain)

    private fun extractDomainName(url: String?): String {
        if (url == null) {
            return ""
        }

        var domainName = url

        var index = domainName.indexOf("://")

        if (index != -1) {
            // keep everything after the "://"
            domainName = domainName.substring(index + 3)
        }

        index = domainName.indexOf('/')

        if (index != -1) {
            // keep everything before the '/'
            domainName = domainName.substring(0, index)
        }

        // check for and remove a preceding 'www'
        // followed by any sequence of characters (non-greedy)
        // followed by a '.'
        // from the beginning of the string
        domainName = domainName.replaceFirst("^www.*?\\.".toRegex(), "")

        return domainName.toLowerCase()
    }
}
