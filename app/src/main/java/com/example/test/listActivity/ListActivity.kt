package com.example.test.listActivity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.api.NetworkCall
import com.example.test.api.requestData.ListRequestData
import com.example.test.data.viewModel.MainViewModel
import com.example.test.databinding.ActivityMainBinding
import com.example.test.detailActivity.ProductsActivity
import com.example.test.listActivity.adapter.CategoryAdapter

class ListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        getList()
    }

    private fun getList() {
        val params = ListRequestData()
        params.idx = "103"
        params.c2code  = "UAT0C1"
        params.fun_name = "HEAD"
        params.res_format = "1"

        viewModel.getList(this, params).observe(this, Observer {
            when(it) {
                is NetworkCall.Success -> {
                    if (it.response.head?.status == "200") {
                        if (!it.response.head?.content.isNullOrEmpty()) {
                            binding.recProducts.apply {
                                it.response.head?.content?.let {list->
                                    adapter = CategoryAdapter(this@ListActivity, list) { item->
                                        startActivity(
                                            Intent(this@ListActivity, ProductsActivity::class.java)
                                            .putExtra("CAT_ID", item.c_code))
                                    }
                                }
                            }
                        }
                    }
                }
            }

        })
    }

}