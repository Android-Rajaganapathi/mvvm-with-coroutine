package com.mvvm.coroutines.data.model

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.moshi.Json

data class MenuResponse(
    @field:Json(name = "chef") var chef: String = "",
    @field:Json(name = "description") var description: String = "",
    @field:Json(name = "name") var name: String = "",
    @field:Json(name = "price") var price: Int = 0,
    @field:Json(name = "thumbnail") var thumbnail: String = "",
    @field:Json(name = "timestamp") var timestamp: String = ""
)