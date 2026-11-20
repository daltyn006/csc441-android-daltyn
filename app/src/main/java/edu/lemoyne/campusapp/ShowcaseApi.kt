package edu.lemoyne.campusapp

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header


interface ShowcaseApi {
    @GET("rest/v1/showcase_items")
    suspend fun getShowcaseItems(
        @Header("apikey") apikey: String,
        @Header("authorization") authorization: String,
    ): List<ShowcaseItem>

    @GET("/auth/v1/tolken?grant_type=password")
    suspend fun signIn(
        @Header("apikey") apikey: String,
        @Body request: LoginRequest,
    ): LoginResponse

}

object ShowcaseService {
    const val API_KEY = "sb_publishable_B4g5CMaoDxSCkq9-u6Rm6w_AyNP1vc7"
    const val BASE_URL = "https://cdfichxcvdpjewcovyqk.supabase.co"

    val api: ShowcaseApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShowcaseApi::class.java)
}


// Recreate as one folder for data class; one for requests etc. etc.
// Move this function from here to clean up and better swap this

data class LoginRequest(
    val username: String,
    val password: String,
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String
)



