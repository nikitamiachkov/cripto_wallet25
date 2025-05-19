package com.example.kaban2.Domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Cripto(

    val id:Int,
    val name: String,
    val cost: Double,
    val image:String,
    val last_cost: Double

)
