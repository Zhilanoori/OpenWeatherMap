package com.noori.openweathermapapi6.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(
    val deg: Int,
    val speed: Double
):Parcelable