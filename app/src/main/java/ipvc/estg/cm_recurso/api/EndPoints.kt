package ipvc.estg.cm_recurso.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/myslim/api/markers")
    fun getMarkers(): Call<List<Post>>

    @GET("/myslim/api/markers/{id}")
    fun getMarkersById(@Path("id") id: Int): Call<Post>

    @POST("/myslim/api/marker")
    fun post(@Field("latitude") latitude: String?,
             @Field("longitude") longitude: String?,
             @Field("rue") rue: String,
             @Field("desc") desc: String?,
             @Field("imagem") imagem: String?): Call<OutputPost>

    @GET("/myslim/api/users")
    fun getUsers(): Call<List<User>>

    @GET("/myslim/api/users/{id}")
    fun getUserssById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<OutputLogin>
}