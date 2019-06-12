package com.github.joelhandwell.populi

import org.slf4j.Logger
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import org.slf4j.LoggerFactory

inline fun <reified R : Any> R.logger(): Logger =
    LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

class Populi(
    private val accessKey: String, private val api: PopuliApi
) {
    companion object {
        private val log = logger()
    }

    class Builder {
        private var username: String? = null
        private var password: String? = null
        private var accessKey: String? = null
        private var baseUrl: String? = null

        fun withUsername(username: String) = apply { this.username = username }
        fun withPassword(password: String) = apply { this.password = password }
        fun withAccessKey(accessKey: String) = apply { this.accessKey = accessKey }
        fun withBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun build(): Populi {

            val api = Retrofit.Builder().baseUrl(baseUrl ?: throw RuntimeException("baseUrl is null"))
                .addConverterFactory(JaxbConverterFactory.create())
                .build().create(PopuliApi::class.java)

            if (accessKey == null) {
                log.info("fetching accessKey with username and password")
                val response = api.requestAccessKey(
                    username ?: throw RuntimeException("username null"),
                    password ?: throw RuntimeException("password null")
                ).execute()
                if (!response.isSuccessful) {
                    throw RuntimeException("accessKey request not success, error body: ${response.errorBody()}")
                }
                val body = response.body() ?: throw RuntimeException("accessKey response body was null")
                if (body.access_key.isNullOrBlank()) {
                    throw RuntimeException("accessKey was null or blank")
                } else {
                    log.info("We got accessKey!")
                }
                accessKey = body.access_key.trim()
            } else {
                log.info("using existing accessKey")
            }

            return Populi(accessKey!!, api)
        }
    }

    fun getDegrees(): MutableList<Degree>? = this.api!!.getDegrees(accessKey).execute().body()!!.degree
}

interface PopuliApi {

    @FormUrlEncoded @POST("api/") fun requestAccessKey(@Field("username") username: String, @Field("password") password: String): Call<AccessKeyResponse>

    @FormUrlEncoded @POST("api/") fun getDegrees(@Field("access_key") accessKey: String, @Field("task") task: String = "getDegrees"): Call<DegreeResponse>
}
