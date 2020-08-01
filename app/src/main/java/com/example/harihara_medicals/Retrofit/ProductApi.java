package com.example.harihara_medicals.Retrofit;

import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.MRecords;
import com.example.harihara_medicals.Model.ModelRes;
import com.example.harihara_medicals.Model.Notif;
import com.example.harihara_medicals.Model.Response;
import com.google.gson.JsonObject;

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
    @POST("make_apmt.php")
    Call<Void> makeAppointment(
            @Field("doctor_id") String doctor_id,
            @Field("user_id") String user_id,
            @Field("dname") String dname,
            @Field("spcl") String spcl,
            @Field("time") String time,
            @Field("date") String date,
            @Field("fees") String fees,
            @Field("exp") String exp);

    @FormUrlEncoded
    @POST("make_rem.php")
    Call<Void> makeReminder(
            @Field("desc") String desc,
            @Field("date") String date,
            @Field("title") String title,
            @Field("loc") String loc,
            @Field("time") String time,
            @Field("userid") String userid);

    @FormUrlEncoded
    @POST("reminder.php")
    Call<String> getReminders(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("appointment.php")
    Call<String> getAppoinments(@Field("uid") String uid);

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

    @FormUrlEncoded
    @POST("cart.php")
    Call<String> getItem(@Field("userid") String userid);

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

    @Multipart
    @POST("edit_user.php")
    Call<LoginData> edtUserinfo(@Part MultipartBody.Part imageFile,
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
                             @Part("user_id") RequestBody user_id);

    @Multipart
    @POST("mrecords.php")
    Call<Response> mrecords(@Part MultipartBody.Part imageFile, @Part("userid") RequestBody userid);

    @FormUrlEncoded
    @POST("canclAp.php")
    Call<String> cancelAppointment(@Field("apid") String apid);

    @FormUrlEncoded
    @POST("getmrecords.php")
    Call<String> getmrecords(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("getnotification.php")
    Call<String> getnotification(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("dltreminder.php")
    Call<String> deleteReminder(@Field("reid") String reid);

    @FormUrlEncoded
    @POST("addtocart.php")
    Call<String> addtcart(@Field("userid") String userid, @Field("prid") String prid, @Field("prname") String prname, @Field("prcount") String prcount, @Field("prprice") String prprice);

    @FormUrlEncoded
    @POST("remcart.php")
    Call<String> remvcart(@Field("cid") String cid);

    @FormUrlEncoded
    @POST("plcorder.php")
    Call<ModelRes> plcord(@Field("userid") String userid,
                          @Field("topr") String topr);

    @FormUrlEncoded
    @POST("myorders.php")
    Call<String> myorders(@Field("uid") String uid);

    @Multipart
    @POST("uploadpres.php")
    Call<Response> uploadpre(@Part MultipartBody.Part image,
                             @Part("user_id") RequestBody user_id,
                             @Part("dname") RequestBody dname,
                             @Part("vdate") RequestBody vdate,
                             @Part("place") RequestBody place,
                             @Part("pvisit") RequestBody pvisit);

    @GET("listdctrs.php")
    Call<String> listDoctors();


}