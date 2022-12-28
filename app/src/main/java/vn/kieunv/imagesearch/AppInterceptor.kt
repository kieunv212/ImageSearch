package vn.kieunv.imagesearch

import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
        requestBuilder.addHeader(
            "Authorization",
            "Bearer 563492ad6f91700001000001dc1d5f0fd899428c9852c3c74bb50ded"
        )
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}