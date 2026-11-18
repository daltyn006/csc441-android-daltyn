package edu.lemoyne.campusapp

import com.google.gson.annotations.SerializedName

data class ShowcaseItem (

    @SerializedName("url" ) var userName : String,
    @SerializedName("description"     ) var email    : String,
    @SerializedName("category"      ) var category     : String,
    @SerializedName("title"      ) var title     : String,
    @SerializedName("created_at"      ) var createdAt     : String,
    @SerializedName("image_url"      ) var imageUrl     : String

)