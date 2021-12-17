package com.example.test.api

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.IntRange
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection


object ApiHelper {

    const val base_url = "http://106.51.36.42:1333" //laravel url test

    lateinit var apiService: ApiService

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .apply {
            readTimeout(20, TimeUnit.MINUTES)
            connectTimeout(20, TimeUnit.MINUTES)
            writeTimeout(20, TimeUnit.MINUTES)
            interceptors().addAll(getInterceptors())
        }.build()

    init {
        makeService()
    }


    private fun makeService() {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    private fun getInterceptors(): List<Interceptor> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val authInterceptor: Interceptor = object : Interceptor {

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original: Request = chain.request()
                val builder: Request.Builder = original.newBuilder()
                    .header("Content-Type", "application/json")

                val request: Request = builder.build()
                return chain.proceed(request)
            }

        }
        return mutableListOf<Interceptor>().apply {
            add(loggingInterceptor)
            add(authInterceptor)
        }.toList()
    }

    fun getApi(): ApiService {
        return apiService
    }

    suspend fun <T> safeApiCall(
        handleError: Boolean = true,
        handleLoading: Boolean = true,
        context: Context,
        call: suspend () -> Response<T>
    ): NetworkCall<T> {
        var dialog: Dialog? = null
        if (handleLoading)
            Handler(context.mainLooper).post(Runnable {
                if (handleLoading)
                    dialog = ApiUiHelper.defaultLoadingDialog(context)
            })

        return try {
            val myResp = call.invoke()
            Handler(context.mainLooper).post(Runnable {
                dialog?.dismiss()
            })

            if (myResp.isSuccessful) {

                if (myResp.body() is JsonObject) {
                    NetworkCall.Success(myResp.body()!!)
                } else if (myResp.body() is String) {
                    NetworkCall.Success(myResp.body()!!)
                } else {
                    Log.e("Response", myResp.body().toString())
                    NetworkCall.Success(myResp.body()!!)
                }
            } else {
                Handler(context.mainLooper).post(Runnable {
                    dialog?.dismiss()
                })

                var errorMessage: String = myResp.message()
                if (myResp.code() == 400) {
                    val jObjError = JSONObject(myResp.errorBody()?.string())
                    Log.e("TAG", "safeApiCall: $jObjError")
                    errorMessage = jObjError.getString("message").toString()
                }

                if (handleError) {
                    if (myResp.code() != HttpsURLConnection.HTTP_FORBIDDEN) {
                        if (myResp.code() == 400) {
                            Handler(Looper.getMainLooper()).post(Runnable {
                                Toast.makeText(context, "$errorMessage", Toast.LENGTH_LONG).show()
                            })
                        } else {
                            Handler(Looper.getMainLooper()).post(Runnable {
                                Toast.makeText(
                                    context,
                                    "${myResp.code()} : $errorMessage : ${
                                        myResp.errorBody()?.toString()
                                    }", Toast.LENGTH_LONG
                                ).show()
                            })
                        }
                    }
                }
                NetworkCall.Error(myResp.code(), "${myResp.code()} : $errorMessage")
            }

        } catch (e: Exception) {
            Log.e("ApiHelper", "Exception", e)
            Handler(Looper.getMainLooper()).post(Runnable {
                dialog?.cancel()
            })
            if (getConnectionType(context) == 0) {
                Handler(context.mainLooper).post(Runnable {
                    dialog?.cancel()
                    ApiUiHelper.ShowApiRetryAlert(
                        "Oops! No internet connection",
                        "Please check your internet connection and try again",
                        context
                    ) {
                        Log.e("ERR", "DFS")
                        safeApiCall(handleError, handleLoading, context, call)
                    }
                })

                NetworkCall.Error(0, "")
            } else {
                Handler(Looper.getMainLooper()).post(Runnable {
                    if (!e?.localizedMessage.contains("JsonReader.setLenient(true)") && !e?.localizedMessage.contains(
                            "Job was cancelled"
                        )
                    )
                        Toast.makeText(
                            context,
                            "An error occurred : ${e?.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                })
                NetworkCall.Error(0, "An error occurred : ${e?.localizedMessage}")
            }

        }
    }


    @IntRange(from = 0, to = 2)
    fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = 2
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = 1
                }
            }
        } else {
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                // connected to the internet
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    result = 2
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    result = 1
                }
            }
        }
        return result
    }

}

