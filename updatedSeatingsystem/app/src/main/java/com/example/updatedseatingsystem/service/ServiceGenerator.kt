package com.example.seatingsystem.service

import com.example.updatedseatingsystem.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {


    companion object{

        //  http://10.244.35.170:4000/api/
        private val BASE_URL = "https://new-seating-system.herokuapp.com/api/"

        // create OkHttp client
        private val okBuilder = OkHttpClient.Builder()

        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        // create Retrofit instance
        private var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var retrofit = builder.build()
        fun <S> createService(serviceClass: Class<S>): S {
            if (!okBuilder.interceptors().contains(logging)) {
                if (BuildConfig.DEBUG) {
                    okBuilder.addInterceptor(logging)
                }
                builder = builder.client(okBuilder.build())

                retrofit = builder.build()

            }

            return retrofit.create(serviceClass)
        }
    }

}