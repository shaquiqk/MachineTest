package com.example.test.api

import com.example.test.api.requestData.ListRequestData
import com.example.test.api.requestData.ProductListRequestData
import com.example.test.data.responseModels.ListBean
import com.example.test.data.responseModels.ProductListBean
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @POST("/lcapi/backend/web/apk/get-commondata")
    @Headers("Content-Type: application/json")
    suspend fun getList(
        @Body param: ListRequestData
    ): Response<ListBean>

    @POST("/lcapi/backend/web/apk/get-commondata")
    @Headers("Content-Type: application/json")
    suspend fun getProducts(
        @Body param: ProductListRequestData
    ): Response<ProductListBean>

}