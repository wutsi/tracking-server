package com.wutsi.tracking.dto

public data class PushTrackRequest(
    public val time: Long = 0,
    public val duid: String? = null,
    public val uid: String? = null,
    public val hid: String? = null,
    public val pid: String? = null,
    public val ua: String? = null,
    public val ip: String? = null,
    public val lat: Double? = null,
    public val long: Double? = null,
    public val referer: String? = null,
    public val page: String? = null,
    public val event: String? = null,
    public val `value`: String? = null,
    public val url: String? = null,
    public val siteid: String? = null,
    public val impressions: String? = null
)
