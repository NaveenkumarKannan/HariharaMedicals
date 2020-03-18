package com.example.harihara_medicals.Retrofit;

import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.Model.LoginData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProductApi {

    @GET("doctor.php")
    Call<String> getDoctors();

    @GET("product.php")
    Call<String> getMedicen();

    @FormUrlEncoded
    @POST("doctor.php")
    Call<String> getDoctorbySpe(@Field("spcl") String spcl);

    @FormUrlEncoded
    @POST("appointment.php")
    Call<Void> getAppointment(
            @Field("dname") String dname,
            @Field("fee") String fee,
            @Field("experience") String experience,
            @Field("spcl") String spcl,
            @Field("time") String time,
            @Field("date") String date);

    @FormUrlEncoded
    @POST("reminder.php")
    Call<Void> getReminder(
            @Field("desc") String desc,
            @Field("date") String date,
            @Field("title") String title,
            @Field("loc") String loc,
            @Field("time") String time);

    @GET("reminder.php")
    Call<String> getReminders();

    @GET("appointment.php")
    Call<String> getAppoinments();

    @FormUrlEncoded
    @POST("cart.php")
    Call<String> getCart(
            @Field("pname") String pname,
            @Field("pcount") String pcount,
            @Field("price") String price);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> checkLogin(
            @Field("user_id") String pname);

    @GET("cart.php")
    Call<String> getItem();

    @Multipart
    @POST("register.php")
    Call<LoginData> register(//@Part("image\"; filename=\"myfile.jpg\" ") RequestBody requestFile,
                             @Part MultipartBody.Part imageFile,
                             @Part("fname") RequestBody fname,
                             @Part("lname") RequestBody lname,
                             @Part("dob") RequestBody dob,
                             @Part("mail") RequestBody mail,
                             @Part("address") RequestBody address,
                             @Part("gender") RequestBody gender,
                             @Part("current_height") RequestBody current_height,
                             @Part("current_weight") RequestBody current_weight,
                             @Part("sugar") RequestBody sugar,
                             @Part("dname") RequestBody dname,
                             @Part("bp") RequestBody bp,
                             //@Part("bmi") RequestBody bmi,
                             @Part("user_id") RequestBody userId,
                             @Part("phone") RequestBody phone);


}
