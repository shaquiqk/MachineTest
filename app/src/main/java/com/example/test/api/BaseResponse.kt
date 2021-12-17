package com.example.test.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse<T> {

    @SerializedName("HEAD")
    @Expose
    var data: T? = null
        private set
}

