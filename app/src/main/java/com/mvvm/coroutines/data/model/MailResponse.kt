package com.mvvm.coroutines.data.model

import com.squareup.moshi.Json

data class MailResponse(
    @field:Json(name = "id") var id: String = "",
    @field:Json(name = "isImportant") var isImportant: Boolean = false,
    @field:Json(name = "from") var from: String = "",
    @field:Json(name = "picture") var picture: String = "",
    @field:Json(name = "subject") var subject: String = "",
    @field:Json(name = "message") var message: String = "",
    @field:Json(name = "timestamp") var timestamp: String = "",
    @field:Json(name = "isRead") var isRead: Boolean = false
)