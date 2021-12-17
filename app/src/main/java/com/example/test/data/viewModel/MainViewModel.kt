package com.example.test.data.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.test.api.requestData.ListRequestData
import com.example.test.api.requestData.ProductListRequestData
import com.example.test.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel() : ViewModel() {

    private val mainRepo = MainRepository()

    fun getList(context: Context, params: ListRequestData) = liveData(Dispatchers.IO) {
        emit(mainRepo.getList(context, params))
    }

    fun getProducts(context: Context, param: ProductListRequestData) = liveData(Dispatchers.IO) {
        emit(
            mainRepo.getProducts(
                context,
                param
            )
        )
    }

}