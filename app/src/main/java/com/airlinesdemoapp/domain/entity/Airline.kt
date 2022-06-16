package com.airlinesdemoapp.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Airline (
    val id: Int,
    val name: String,
    val country: String?,
    val website: String
): Parcelable