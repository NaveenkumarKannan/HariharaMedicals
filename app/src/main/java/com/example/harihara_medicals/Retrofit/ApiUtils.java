package com.example.harihara_medicals.Retrofit;

public class ApiUtils {
    private ApiUtils(){}
    public  static final String BASE_URL="https://grubby.co.za/hariharaMedicals/harihara_medicals/API/";


    public  static final String URL="https://grubby.co.za/hariharaMedicals/harihara_medicals/json-hariharan/";

    public  static Productapi getProductapi(){
        return RetrofitClient.getClient(BASE_URL).create(Productapi.class);
    }
    public  static Productapi getScalarProductapi(){
        return RetrofitClient.getScalarClient(BASE_URL).create(Productapi.class);
    }



    public  static Productapi getUrl(){
        return RetrofitClient.getClient(URL).create(Productapi.class);
    }
}
