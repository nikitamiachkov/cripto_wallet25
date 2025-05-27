package com.example.kaban2.Domain.Utils

import android.text.TextUtils

fun String.isEmailValid () : Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun String.isEmailValid2(): Boolean {
    val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    return this.matches(regex)
}
