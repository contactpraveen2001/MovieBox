package com.backbase.assignment.ui.util

interface INetworkCall {
    var onError: (Exception) -> Unit
    suspend fun getJsonString(uri: String): String
}