package com.demo.pins.services.feed.model

import com.demo.pins.utils.parser.BooleanFromInt
import com.google.type.DateTime
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Feed(
    @Json(name = "feed_id") val id: String,
    @BooleanFromInt @Json(name = "in_beta") val inBeta: Boolean,
    @Json(name = "feed_code") val code: String?,
    @Json(name = "feed_name") val name: String?,
    @Json(name = "feed_network_name") val networkName: String?,
    @Json(name = "feed_location") val location: String?,
    val timezone: String?,
    @Json(name = "country_codes") val countryCodes: String?,
    @Json(name = "sub_country_codes") val subCountryCodes: String?, val language: String?,
    @Json(name = "raw_data_downloaded_at") val rawDataDownloadedAt: Date?,
    @Json(name = "raw_data_start_at") val rawDataStartAt: Date?,
    @Json(name = "raw_data_end_at") val rawDataEndAt: Date?,
    @Json(name = "processed_data_start_at") val processedDataStartAt: Date?,
    @Json(name = "processed_data_end_at") val processedDataEndAt: Date?,
    @Json(name = "bgtfs_uploaded_at") val bgtfsUploadedAt: Date?,
    @Json(name = "bgtfs_version") val bgtfsVersion: String?,
    @Json(name = "feed_start_date") val startDate: Date?,
    @Json(name = "feed_end_date") val endDate : Date?,
    val stars: Int,
    @Json(name = "main_source_hash") val mainSourceHash : String?,
    @Json(name = "bgtfs_hash") val bgtfsHash : String?,
    @Json(name = "gtfs_url") val gtfsUrl : String?,
    @Json(name = "gtfs_rt_trip_updates_url") val gtfsRtTripUpdatesUrl : String?,
    @Json(name = "gtfs_rt_vehicle_positions_url") val gtfsRtVehiclePositionsUrl : String?,
    @Json(name = "gtfs_rt_service_alerts_url") val gtfsRtServiceAlertsUrl : String?,
    @Json(name = "feed_priority") val priority: String?,
    @Json(name = "bgtfs_feed_version") val bgtfsFeedVersion: String?,
    @Json(name = "prediction_engine") val predictionEngine: Int,
    @Json(name = "service_change_site_url") val serviceChangeSiteUrl : String?,
    @Json(name = "service_change_site_from_date") val serviceChangeSiteFromDate: DateTime?,
    @Json(name = "service_change_site_to_date") val serviceChangeSiteToDate: DateTime?,
    @Json(name = "preview_feed") val previewFeed: PreviewFeed?,
    val bounds: Bounds?,
    val conditions: List<Condition>?,
    @Json(name = "has_overlay") val hasOverlay: Boolean
)
