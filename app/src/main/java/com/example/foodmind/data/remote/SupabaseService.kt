package com.example.foodmind.data.remote

import com.example.foodmind.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseService @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun upsert(
        table: String,
        payload: JSONArray,
        onConflict: String
    ) {
        if (!SupabaseConfig.isConfigured) return
        withContext(ioDispatcher) {
            val endpoint = buildUrl(table, onConflict)
            val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("apikey", SupabaseConfig.anonKey)
                setRequestProperty("Authorization", "Bearer ${SupabaseConfig.anonKey}")
                setRequestProperty("Prefer", "resolution=merge-duplicates")
                doOutput = true
                connectTimeout = 15000
                readTimeout = 20000
            }
            try {
                BufferedWriter(OutputStreamWriter(connection.outputStream)).use { writer ->
                    writer.write(payload.toString())
                }
                connection.inputStream.close()
            } finally {
                connection.disconnect()
            }
        }
    }

    private fun buildUrl(table: String, onConflict: String): String {
        val base = SupabaseConfig.url.trimEnd('/')
        return "$base/rest/v1/$table?on_conflict=$onConflict"
    }
}
