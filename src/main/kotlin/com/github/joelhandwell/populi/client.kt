package com.github.joelhandwell.populi

import org.slf4j.Logger
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import org.slf4j.LoggerFactory
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        private var debugFlag: Boolean = false

        fun withUsername(username: String) = apply { this.username = username }
        fun withPassword(password: String) = apply { this.password = password }
        fun withAccessKey(accessKey: String) = apply { this.accessKey = accessKey }
        fun withBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun withDebugFlag(debugFlag: Boolean) = apply { this.debugFlag = debugFlag }

        fun build(): Populi {

            val builder = Retrofit.Builder().baseUrl(baseUrl ?: throw RuntimeException("baseUrl is null"))
                .addConverterFactory(JaxbConverterFactory.create())

            val api = if(debugFlag){
                builder.addConverterFactory(ScalarsConverterFactory.create())
            }else{
                builder
            }.build().create(PopuliApi::class.java)

            if (accessKey == null) {
                log.info("fetching accessKey with username and password")
                val response = api.requestAccessKey(
                    username ?: throw RuntimeException("username null"),
                    password ?: throw RuntimeException("password null")
                )

                val body = sendRequest(response)

                if (body.access_key.isNullOrBlank()) {
                    throw RuntimeException("accessKey was null or blank")
                } else {
                    Populi.log.info("We got accessKey!")
                }

                accessKey = body.access_key.trim()
            } else {
                log.info("using existing accessKey")
            }

            return Populi(accessKey!!, api)
        }
    }

    /**
     * Returns information about each degree configured at the school. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCountries)
     */
    fun getDegrees(): MutableList<Degree>? = sendRequest(this.api.getDegrees(accessKey)).degree

    /**
     * Returns all users. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getUsers)
     */
    fun getUsers(): MutableList<User> = sendRequest(this.api.getUsers(accessKey)).person

}

interface PopuliApi {
    @FormUrlEncoded @POST(API_URI) fun requestAccessKey(@Field("username") username: String, @Field("password") password: String): Call<AccessKeyResponse>
    @FormUrlEncoded @POST(API_URI) fun getDegrees(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getDegrees"): Call<DegreeResponse>
    @FormUrlEncoded @POST(API_URI) fun getUsers(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getUsers"): Call<UserResponse>

    //for debug
    //@FormUrlEncoded @POST(API_URI) fun getUsersRaw(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getUsers"): Call<String>
}

private const val API_URI = "api/"
private const val FIELD_ACCESS_KEY = "access_key"
private const val FIELD_TASK = "task"

private fun <T> sendRequest(call: Call<T>): T {
    val response: Response<T> = call.execute()
    if (!response.isSuccessful) {
        throw RuntimeException("request not success, error body: ${response.errorBody()}")
    }
    return response.body() ?: throw RuntimeException("accessKey response body was null")
}
