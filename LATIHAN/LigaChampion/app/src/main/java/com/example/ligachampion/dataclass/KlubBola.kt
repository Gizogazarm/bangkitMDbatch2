package com.example.ligachampion.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KlubBola (
    val namaBola: String,
    val description: String,
    val gelar: String,
    val photo: String,
    val photoPlayer: String,
    val namaPemainBola:String
) : Parcelable


