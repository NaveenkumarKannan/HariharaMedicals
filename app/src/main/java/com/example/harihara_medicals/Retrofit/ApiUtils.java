package com.example.harihara_medicals.Retrofit;

public class ApiUtils {
    private ApiUtils(){}
    //public  static final String HOST_URL="https://grubby.co.za/hariharaMedicals/harihara_medicals/";
    public  static final String HOST_URL="https://grubby.co.za/harihara/";
    //public  static final String HOST_URL="http://192.168.0.114/harihara_medicals/";
    public  static final String BASE_URL=HOST_URL+"API/";
    public  static final String URL=BASE_URL+"json-hariharan/";
    public  static final String PROFILE_IMAGE_URL=BASE_URL+"register/";

    public  static ProductApi getProductApi(){
        return RetrofitClient.getClient(BASE_URL).create(ProductApi.class);
    }

    public  static ProductApi getScalarProductApi(){
        return RetrofitClient.getScalarClient(BASE_URL).create(ProductApi.class);
    }

    public  static ProductApi getUrl(){
        return RetrofitClient.getClient(URL).create(ProductApi.class);
    }

    public  static ProductApi getScalarUrl(){
        return RetrofitClient.getScalarClient(URL).create(ProductApi.class);
    }
}
