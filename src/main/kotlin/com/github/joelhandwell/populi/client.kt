package com.github.joelhandwell.populi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class Populi(
    private val accessKey: String
) {

    var api: PopuliApi? = null

    class Builder {
        private var accessKey: String? = null
        private var baseUrl: String? = null

        fun withAccessKey(accessKey: String) = apply { this.accessKey = accessKey }
        fun withBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun build(): Populi {

            val api = Retrofit.Builder().baseUrl(baseUrl ?: throw RuntimeException("baseUrl is null"))
                .addConverterFactory(JaxbConverterFactory.create())
                .build().create(PopuliApi::class.java)

            val client = Populi(accessKey ?: throw RuntimeException("accessKey is null"))
            client.api = api
            return client
        }
    }

    fun getDegrees(): MutableList<Degree>? = this.api!!.getDegrees(accessKey).execute().body()!!.degree
}

interface PopuliApi {
    @FormUrlEncoded
    @POST("api/")
    fun getDegrees(@Field("access_key") accessKey: String, @Field("task") task: String = "getDegrees"): Call<DegreeRequest>
}
