package com.mvvm.coroutines.data.local.entity

import androidx.room.Entity
import java.io.Serializable

@Entity
data class User(
    var name: String? = "",
    var password: String? = ""
) : BaseEntity(), Serializable