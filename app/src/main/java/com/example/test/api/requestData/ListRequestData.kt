package com.example.test.api.requestData

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListRequestData {
    @SerializedName("idx")
    @Expose
    var idx: String? = null

    @SerializedName("c2code")
    @Expose
    var c2code: String? = null

    @SerializedName("fun_name")
    @Expose
    var fun_name: String? = null

    @SerializedName("res_format")
    @Expose
    var res_format: String? = null
}