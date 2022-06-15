package com.airlinesdemoapp.data.api_response

import com.google.gson.annotations.SerializedName

data class AirLine(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("slogan") val slogan: String,
    @SerializedName("head_quaters") val head_quaters: String,
    @SerializedName("website") val website: String,
    @SerializedName("established") val established: String,
)
