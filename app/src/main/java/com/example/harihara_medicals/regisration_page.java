package com.example.harihara_medicals;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.harihara_medicals.Retrofit.ApiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@RuntimePermissions
public class regisration_page extends AppCompatActivity {
    EditText firstname,lastname,dob,email,address,drname,height,weight,blood_pressure,sugar_level;
    RadioGroup radio_group;
    ImageView res_pro_pic;
    Button register;
    private int PICK_IMAGE_REQUEST = 1;
    Uri fileUri;
    String g_ender="";
    final Calendar mycalender=Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regisration_page);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }*/

        firstname=findViewById(R.id.regs_firstname);
        lastname=findViewById(R.id.regs_lastname);
        dob=findViewById(R.id.regs_dob);
        email=findViewById(R.id.regs_email);
        address=findViewById(R.id.regs_address);
        drname=findViewById(R.id.regs_doctor_name);
        height=findViewById(R.id.regs_height);
        weight=findViewById(R.id.regs_weight);
        radio_group=findViewById(R.id.radio_group);

        blood_pressure=findViewById(R.id.regs_bp);
        sugar_level=findViewById(R.id.regs_sugar);
        res_pro_pic=findViewById(R.id.regs_upload);


        firstname.setText("Bhuvi");
        lastname.setText("Bhuvi");
        dob.setText("01/12/2020");
        email.setText("maill@gmail.com");
        address.setText("addr");
        drname.setText("Bhuvi");
        height.setText("165");
        weight.setText("65");
        blood_pressure.setText("165");
        sugar_level.setText("65");
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(regisration_page.this,date,mycalender.get(Calendar.MONTH),
                        mycalender.get(Calendar.DAY_OF_MONTH),mycalender.get(Calendar.YEAR)).show();
            }
        });
        res_pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(checkedId==R.id.regs_male_radio){
                    g_ender = "Male";
                }else if (checkedId==R.id.regs_female_radio){
                    g_ender = "Female";
                }else {
                    g_ender = "Others";
                }
                Log.e("Gender",g_ender);
            }
        });
        register = (Button) findViewById(R.id.regs_btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name=firstname.getText().toString().trim();
                String last_name=lastname.getText().toString().trim();
                String d_o_b=dob.getText().toString().trim();
                if (first_name.isEmpty()){
                    firstname.setError("enter your  first name");
                    firstname.requestFocus();
                }else if (last_name.isEmpty()){
                    lastname.setError("enter your last name");
                    lastname.requestFocus();
                }else if (d_o_b.isEmpty()){
                    dob.setError("enter your date of birth");
                    dob.requestFocus();
                }else if (fileUri==null){
                    Toast.makeText(regisration_page.this,"Choose Image",Toast.LENGTH_SHORT).show();
                }else {

                    //calling the upload file method after choosing the file
                   sendPost();
                    /*sendPost(firstname.getText().toString(),lastname.getText().toString(),dob.getText().toString()
                            ,email.getText().toString(),address.getText().toString(),height.getText().toString()
                            ,weight.getText().toString(),sugar_level.getText().toString(),blood_pressure.getText().toString(),dname.getText().toString()
                   );*/
                }
            }
        });


    }
    //Permission Url
    //https://github.com/permissions-dispatcher/PermissionsDispatcher
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        res_pro_pic.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            Uri selectedImage = data.getData();
            upload(selectedImage);



        }*/
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            fileUri = uri;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.regs_profile);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                log("Path = "+getRealPathFromURI(fileUri));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

  /*  private void upload(Uri fileUri) {
          File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        Call<Myresponse> call=ApiUtils.getProductapi().getImage(requestFile);
        call.enqueue(new Callback<Myresponse>() {
            @Override
            public void onResponse(Call<Myresponse> call, Response<Myresponse> response) {

            }

            @Override
            public void onFailure(Call<Myresponse> call, Throwable t) {

            }
        });
    }*/

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result="";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //result = contentUri.getPath();
            String id = DocumentsContract.getDocumentId(contentUri);
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(contentUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String uniqueString = UUID.randomUUID().toString();
            File file = new File(getCacheDir().getAbsolutePath()+"/"+"image"+uniqueString+".jpg");
            writeFile(inputStream, file);
            result = file.getAbsolutePath();

        }else
            result = cursor.getString(column_index);
        cursor.close();
        return result;

        /*String[] projection={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri,projection,null,null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;*/
    }

    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( out != null ) {
                    out.close();
                }
                in.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public   void updateLabel(){
        String myfromat="MM/dd/yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myfromat, Locale.UK);
        dob.setText(sdf.format(mycalender.getTime()));
    }

    private void sendPost(){//String fname, String lname, String d_ob, String gender, String m_ail, String a_ddress, String c_urrent_height, String c_urrent_weight, String s_ugar, String b_p,  String dname,Uri fileUri) {
        String path = getRealPathFromURI(fileUri);
        if(path == null){
            log("Path null");
            return;
        }
        else {
            log("Path not null");
        }
        File file = new File(path);
        RequestBody f_name = RequestBody.create(MediaType.parse("text/plain"), firstname.getText().toString());
        RequestBody l_name = RequestBody.create(MediaType.parse("text/plain"), lastname.getText().toString());
        RequestBody d_o_b = RequestBody.create(MediaType.parse("text/plain"), dob.getText().toString());
        RequestBody gen = RequestBody.create(MediaType.parse("text/plain"), g_ender);
        RequestBody e_mail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody user_address = RequestBody.create(MediaType.parse("text/plain"), address.getText().toString());
        RequestBody h_eight = RequestBody.create(MediaType.parse("text/plain"), height.getText().toString());
        RequestBody w_eight = RequestBody.create(MediaType.parse("text/plain"), weight.getText().toString());
        RequestBody sugarlevel = RequestBody.create(MediaType.parse("text/plain"), sugar_level.getText().toString());
        RequestBody bp = RequestBody.create(MediaType.parse("text/plain"), blood_pressure.getText().toString());
        RequestBody d_name = RequestBody.create(MediaType.parse("text/plain"), drname.getText().toString());
        //RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(),requestFile);

        Call<String> call= ApiUtils.getScalarProductapi().getResgister(body,f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp);
        //Call<String> call=ApiUtils.getProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp,requestFile);
        //Call<Myresponse> call=ApiUtils.getProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp);
        //Call<String> call=ApiUtils.getScalarProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                log("onResponse "+response);
                Toast.makeText(regisration_page.this, "data Inserted successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(regisration_page.this, HomePageActivity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                log("onFailure "+t.getMessage());
                Toast.makeText(regisration_page.this, "Error Occurred: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void log(String message) {
        Log.e(getClass().getSimpleName(),message);
    }


}
