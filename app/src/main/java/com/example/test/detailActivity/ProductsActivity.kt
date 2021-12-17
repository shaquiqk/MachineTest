package com.example.test.detailActivity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.api.NetworkCall
import com.example.test.api.requestData.ProductListRequestData
import com.example.test.data.viewModel.MainViewModel
import com.example.test.databinding.ActivityDetailBinding

class ProductsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: MainViewModel
    var dialog: Dialog? = null
    var catID: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (intent.hasExtra("CAT_ID")) {
            catID = intent.extras?.getString("CAT_ID")
        }

        getDetail()

        binding.apply {
            back.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun getDetail() {
        val params = ProductListRequestData()
        params.idx = "100"
        params.c2code = "UAT0C1"
        params.fun_name = "HEADITEMLIST"
        params.res_format = "1"
        params.headCode = catID
        params.startLimit = "0"
        params.endLimit = "50"

        viewModel.getProducts(this, params).observe(this, Observer {
            when (it) {
                is NetworkCall.Success -> {
                    if (it.response.headItemList?.status == "200") {
                        if (!it.response.headItemList?.content.isNullOrEmpty()) {
                            binding.recProducts.apply {
                                it.response.headItemList?.content?.let {list->
                                    adapter = ProductAdapter(this@ProductsActivity, list)
                                }
                            }
                        }
                    }
                }
            }
        })

    }
}