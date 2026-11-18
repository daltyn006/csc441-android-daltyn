package edu.lemoyne.campusapp

import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.header
import retrofit2.create


interface ShowcaseApi {
    @GET("rest/v1/showcase_items")
    suspend fun getShowcaseItems(){
        suspend fun getShowcaseItems{
            @header("apikey") apikey; String
        }
    }; List<ShowcaseItem>
}
object ShowcaseService {
    const val API_KEY = "sb_publishable_B4g5CMaoDxSCkq9-u6Rm6w_AyNP1vc7"
    const val BAS_URL = "https://cdfichxcvdpjewcovyqk.supabase.co"

    val api: ShowcaseApi = Retrofit.builder()
        .header(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build
        .create(ShowcaseApi::class java)
}
