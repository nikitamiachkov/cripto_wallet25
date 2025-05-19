package com.example.kaban2.Domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Resources(

    val user_id:String?,
    val balance_in_rubles: Double?

)
