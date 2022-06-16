package com.airlinesdemoapp.data.api_response

data class Response(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<ApiResponse>,
    val message: String
)
