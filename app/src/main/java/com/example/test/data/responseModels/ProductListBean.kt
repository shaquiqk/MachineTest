package com.example.test.data.responseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductListBean {
    @SerializedName("HEADITEMLIST")
    @Expose
    var headItemList: HeadItemList? = null
}

class HeadItemList {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("content")
    @Expose
    var content: List<Products>? = null
}

class Products {
    @SerializedName("1")
    @Expose
    var name: String? = null

    @SerializedName("4")
    @Expose
    var image: String? = null

    @SerializedName("10")
    @Expose
    var price: String? = null

    @SerializedName("15")
    @Expose
    var pack: String? = null
}
