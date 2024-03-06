package com.example.application.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CountryRepository {
    private val COUNTRY_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

    suspend fun getCountryData(): String {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(COUNTRY_URL)
                .build()
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    return@withContext response.body!!.string()
                } else {
                    throw IOException("Unexpected HTTP code: ${response.code}")
                }
            } catch (e: IOException) {
                throw e
            }
        }
    }
}