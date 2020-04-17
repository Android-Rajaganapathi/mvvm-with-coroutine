package com.mvvm.coroutines.data.model

import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "name") var name: String = "",
    @field:Json(name = "image") var image: String = "",
    @field:Json(name = "phone") var phone: String = ""
)