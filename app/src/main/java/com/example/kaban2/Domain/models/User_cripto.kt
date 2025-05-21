package com.example.kaban2.Domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User_cripto(

    val id:Int,
    val cripto: Int,
    val quantity: Int,
    val purchase_price:Double,
    val user_id: String

)
