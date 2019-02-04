package com.exmaple.jarvis.chat.Retrofit;import com.exmaple.jarvis.chat.Model.Message;import com.exmaple.jarvis.chat.Model.User;import java.util.List;import java.util.Map;import io.reactivex.Observable;import okhttp3.MultipartBody;import okhttp3.RequestBody;import retrofit2.http.GET;import retrofit2.http.Multipart;import retrofit2.http.POST;import retrofit2.http.Part;import retrofit2.http.PartMap;import retrofit2.http.Path;public interface RequestService {    @GET("user/{username}")    Observable<User> getUser(@Path("username") String username);    @Multipart    @POST("user")    Observable<User> postUser(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part avatar);    @GET("group/{username}")    Observable<List<Message>> getChatMsgList(@Path("username") String username);}