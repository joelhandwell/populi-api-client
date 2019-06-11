package com.github.joelhandwell.populi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

fun populiClientOf(baseUrl: String): Populi = Retrofit.Builder().baseUrl(baseUrl)
    .addConverterFactory(JaxbConverterFactory.create())
    .build().create(Populi::class.java)

interface Populi {
    @FormUrlEncoded
    @POST("api/")
    fun degreeRequest(@Field("access_key") accessKey: String, @Field("task") task: String = "getDegrees"): Call<DegreeRequest>
}
