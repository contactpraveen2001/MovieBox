package com.backbase.assignment.ui.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

open class NetworkCall @Inject constructor() : INetworkCall {
    override var onError = { _: Exception -> }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getJsonString(uri: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(uri)
                with(url.openConnection() as HttpURLConnection) {
                    inputStream.bufferedReader().use { bufferedReader ->
                        val stringBuilder = StringBuilder()
                        var line: String? = bufferedReader.readLine()
                        while (line != null) {
                            stringBuilder.append(line).append("\n")
                            line = bufferedReader.readLine()
                        }
                        bufferedReader.close()
                        stringBuilder.toString()
                    }
                }
            } catch (ex: Exception) {
                onError(ex)
                ""
            }
        }
    }
}