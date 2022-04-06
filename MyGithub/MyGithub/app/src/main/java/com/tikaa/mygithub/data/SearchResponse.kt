package com.tikaa.mygithub.data

import com.google.gson.annotations.SerializedName
import com.tikaa.mygithub.data.User

data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_result")
    val incompleteResult: Boolean,

    val items: ArrayList<User>
)
