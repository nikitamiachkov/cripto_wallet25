package com.example.kaban2.Domain

import io.github.jan.supabase.auth.Auth
//import io.github.jan.supabase.auth.status.SessionSource.Storage
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage


/**
 * Cинглтон-объект Constant, содержащий предварительно настроенного клиента Supabase
 * */

object Constant {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://cjljxpfssrgpwtwzxaln.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNqbGp4cGZzc3JncHd0d3p4YWxuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczNzkzMzksImV4cCI6MjA2Mjk1NTMzOX0.IP1YUhcG89FjDZ2hTBA4LPGK3OiZDHHGboVIH7ard68"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
}