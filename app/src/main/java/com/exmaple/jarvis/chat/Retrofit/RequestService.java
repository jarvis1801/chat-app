package com.exmaple.jarvis.chat.Retrofit;

import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.ChatRoomMessage;
import com.exmaple.jarvis.chat.Model.Group;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.Model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RequestService {
    @GET("user/{username}")
    Observable<User> getUser(@Path("username") String username);

    @GET("user/getRandom/{username}")
    Observable<List<User>> getRandomUserList(@Path("username") String username);

    @Multipart
    @POST("user")
    Observable<User> postUser(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @PUT("user/{username}")
    Observable<User> updateUser(@Path("username") String username, @Field("displayName") String displayname, @Field("password") String password, @Field("email") String email);

    @GET("group/{username}")
    Observable<List<ChatMessageListItem>> getChatMsgList(@Path("username") String username);

    @FormUrlEncoded
    @POST("group")
    Observable<Group> postChatRoomGroup(@Field("user1") String user1, @Field("user2") String user2);

    @GET("message/group/{groupid}")
    Observable<List<ChatRoomMessage>> getChatRoomMsgList(@Path("groupid") String groupId);

    @FormUrlEncoded
    @POST("message")
    Observable<ChatRoomMessage> postChatRoomMsg(@Field("sender") String sender, @Field("group_id") String groupId, @Field("group") String group, @Field("message") String message);
}