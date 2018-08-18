package akshay.myplaces.bean

import com.google.gson.annotations.SerializedName

data class MainPlaceBean(@SerializedName("next_page_token")
                         val nextPageToken: String = "",
                         @SerializedName("results")
                         val results: List<ResultsItem>?,
                         @SerializedName("status")
                         val status: String = "")