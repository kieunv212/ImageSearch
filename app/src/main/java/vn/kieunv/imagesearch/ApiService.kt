package vn.kieunv.imagesearch

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import vn.kieunv.imagesearch.model.ImageModel

interface ApiService {

    @GET("search")
    fun searchImage(@Query("page") page: Int, @Query("per_page") per_page: Int, @Query("query") query: String) : Call<ImageModel>

    companion object {

        var BASE_URL = "https://api.pexels.com/v1/"

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(AppInterceptor())

        fun create() : ApiService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}