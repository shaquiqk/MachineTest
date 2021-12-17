package com.example.test.data.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListBean {
    @SerializedName("HEAD")
    @Expose
    var head: Head? = null
}

class Head {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("content")
    @Expose
    var content: List<Contents>? = null
}

class Contents {
    @SerializedName("c_code")
    @Expose
    var c_code: String? = null

    @SerializedName("c_name")
    @Expose
    var c_name: String? = null

    @SerializedName("c_image")
    @Expose
    var c_image: String? = null
}
