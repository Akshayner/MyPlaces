package akshay.myplaces

import akshay.myplaces.bean.MainPlaceBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI {
    @GET("maps/api/place/nearbysearch/json?radius=1000&key=AIzaSyDdCGdR2cnWw0AB0LeN3jOTjKmkKag4tew")
    fun getPlaces(@Query("location") loc: String, @Query("type") t : String ) : Call<MainPlaceBean>
}