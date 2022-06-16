package com.airlinesdemoapp.domain.entity

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
class UserInfo (
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,
): Parcelable