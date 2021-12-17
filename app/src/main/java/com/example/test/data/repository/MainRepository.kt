package com.example.test.data.repository

import android.content.Context
import com.example.test.api.ApiHelper
import com.example.test.api.BaseResponse
import com.example.test.api.NetworkCall
import com.example.test.api.requestData.ListRequestData
import com.example.test.api.requestData.ProductListRequestData
import com.example.test.data.responseModels.ListBean
import com.example.test.data.responseModels.ProductListBean

class MainRepository() {

    suspend fun getList(
        context: Context,
        param: ListRequestData
    ): NetworkCall<ListBean> =
        ApiHelper.safeApiCall(context = context,
            handleLoading = true,
            call = {
                ApiHelper.apiService.getList(
                   param
                )
            }
        )

    suspend fun getProducts(
        context: Context,
        param: ProductListRequestData
    ): NetworkCall<ProductListBean> =

        ApiHelper.safeApiCall(context = context,
            handleLoading = true,
            call = {
                ApiHelper.apiService.getProducts(param)
            }
        )

}