package com.example.gulmadetektor.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseModule {
    // SECURITY NOTE: In a production app, do NOT hardcode keys here. Use BuildConfig or a secure backend.
    private const val SUPABASE_URL = "https://mknwenfdlefakvloggzq.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1rbndlbmZkbGVmYWt2bG9nZ3pxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjkwNjUwNjEsImV4cCI6MjA4NDY0MTA2MX0.KwBx-LXPOD4gXYwIq2Phtnz_e60o256oWfTk66x5ATM"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Auth) {
            scheme = "weedguard"
            host = "reset-password"
        }
        install(Postgrest)
        install(Storage)
    }
}
