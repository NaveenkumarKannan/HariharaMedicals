package com.example.harihara_medicals.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.harihara_medicals.Edit_details;
import com.example.harihara_medicals.HomePageActivity;
import com.example.harihara_medicals.Medicine.MedicalRecords;
import com.example.harihara_medicals.Model.Response;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Notification;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.chatbot.LiveChatBot;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Adapters.SliderAdapter;
import com.example.harihara_medicals.ui.Book_Dr.BookdrFragment;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.CropImageRequest;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static com.example.harihara_medicals.utils.Util.log;
import static com.ibm.watson.developer_cloud.android.library.camera.GalleryHelper.PICK_IMAGE_REQUEST;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    SliderView sliderView;
    TextView text1, tv_bmi_rate, tv_bmi_status, tv_blood_pressure_rate, tv_sugar_level_rate;
    Button btn1,btn2,btn_menu,live_chat;
    ImageView img1,img2,img3,img4,home_menu, notfy;
    CircleImageView home_user;
    RelativeLayout layout;
    User user;
    String upd, upv, upp, upde;
    String filePath = null;
    ImageView upload;
    Uri fileUri;
    EditText up_dtvst;
    final Calendar mycalender=Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
       /* homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

       notfy = root.findViewById(R.id.home_notification);
       notfy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment fragment = new Notification();
               FragmentTransaction transaction = getFragmentManager().beginTransaction();
               transaction.replace(R.id.nav_host_fragment, fragment);
               transaction.commit();
           }
       });

       sliderView =root.findViewById(R.id.imageSlider);
         final SliderAdapter  adapter = new SliderAdapter(root.getContext());
         adapter.setCount(3);
         sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
                Toast.makeText(getActivity(), " "+position, Toast.LENGTH_SHORT).show();
            }
        });

        text1 = root.findViewById(R.id.home_doctor_visit);
        user = SharedPreferencesManager.getCurrentUser();
        tv_bmi_rate = root.findViewById(R.id.tv_bmi_rate);
        tv_bmi_status = root.findViewById(R.id.tv_bmi_status);
        tv_blood_pressure_rate = root.findViewById(R.id.tv_blood_pressure_rate);
        tv_sugar_level_rate = root.findViewById(R.id.tv_sugar_level_rate);

        try {
            String bmiStatus="";
            float bmi = Float.parseFloat(user.getBmi());
            if(bmi<18.5){
                bmiStatus = "Below Normal";
            }else if(bmi>=18.5&&bmi<25){
                bmiStatus = "Normal";
            }else {
                bmiStatus = "Overweight";
            }
            tv_bmi_rate.setText("BMI "+user.getBmi());
            tv_bmi_status.setText(bmiStatus);
        }catch (Exception e){
            e.printStackTrace();
            tv_bmi_rate.setText("Incorrect BMI");
            tv_bmi_status.setText("Incorrect BMI");
        }
        tv_blood_pressure_rate.setText(user.getBpLevel());
        tv_sugar_level_rate.setText(user.getSugarLevel());
        home_user = root.findViewById(R.id.home_user);
        final String myPhoto = user.getUserLocalPhoto();
        Glide.with(root.getContext()).load(Uri.fromFile(new File(myPhoto)))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(home_user);
        home_menu = root.findViewById(R.id.home_menu);
        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity activity = (HomePageActivity) getActivity();
                if(activity!=null){
                    if(activity.drawerLayout.isDrawerOpen(GravityCompat.START)){
                        activity.drawerLayout.closeDrawer(GravityCompat.START);
                    }else {
                        activity.drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
        img3=root.findViewById(R.id.home_doctor_visit_yes);
        img4=root.findViewById(R.id.home_doctor_visit_no);
        img1=root.findViewById(R.id.home_visit_dr1);
        img2=root.findViewById(R.id.home_visit_dr2);
        layout=root.findViewById(R.id.home_visit_layout);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setVisibility(View.GONE);
                img1.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.move_left);
                img2.startAnimation(animation1);
                Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                img3.startAnimation(animation2);
                Animation animation3 = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in );
                img4.startAnimation(animation3);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog =new Dialog(getActivity());
                dialog.setContentView(R.layout.home_dailog_box);

                EditText up_dctname = dialog.findViewById(R.id.home_dialog_drname);
                up_dtvst = dialog.findViewById(R.id.home_dialog_visitdate);
                EditText up_plc = dialog.findViewById(R.id.home_dialog_place);
                EditText up_pur = dialog.findViewById(R.id.home_dialog_details);

                upload = dialog.findViewById(R.id.upload);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickImages();
                    }
                });

                final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateLabel();
                    }
                };

                up_dtvst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(getActivity(),date,mycalender.get(Calendar.YEAR),
                                mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });

                Button uplpadbtn=dialog.findViewById(R.id.home_dialog_upload);

                uplpadbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        upd = up_dctname.getText().toString();
                        upv = up_dtvst.getText().toString();
                        upp = up_plc.getText().toString();
                        upde = up_pur.getText().toString();

                        if(TextUtils.isEmpty(upd)){
                            up_dctname.setError("Enter doctor name");
                            up_dctname.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(upv)){
                            up_dtvst.setError("Choose date");
                            up_dtvst.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(upp)){
                            up_plc.setError("Enter place of visit");
                            up_plc.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(upde)){
                            up_pur.setError("Enter purpose of visit");
                            up_pur.requestFocus();
                            return;
                        }

                        //Toast.makeText(getContext(),""+upd+" "+upv+" "+upp+" "+upd,Toast.LENGTH_SHORT).show();

//                        String path = filePath;
//
//                        if (path == null) {
//                            Toast.makeText(getActivity(), "null "+path, Toast.LENGTH_SHORT).show();
//                            log("Path null");
//                            return;
//                        } else {
//                            Toast.makeText(getActivity(), "not null "+path, Toast.LENGTH_SHORT).show();
//                            log("Path not null");
//                        }
//
//                        File file = new File(path);
//
//                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//
//                        String uid = SharedPreferencesManager.getCurrentUser().getUid();
//
//                        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), uid);
//                        RequestBody upld = RequestBody.create(MediaType.parse("text/plain"), upd);
//                        RequestBody uplde = RequestBody.create(MediaType.parse("text/plain"), upde);
//                        RequestBody uplp = RequestBody.create(MediaType.parse("text/plain"), upp);
//                        RequestBody uplv = RequestBody.create(MediaType.parse("text/plain"), upv);
//
//                        Call<Response> call = ApiUtils.getProductApi().uploadpre(userid, upld, uplde, uplp, uplv, body);
//                        call.enqueue(new Callback<Response>() {
//                            @Override
//                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                                Toast.makeText(getContext(), "ok "+response, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onFailure(Call<Response> call, Throwable t) {
//                                Toast.makeText(getContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });

                        uploadPrescription();

                        text1.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.GONE);
                        img1.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                Button cancelbtn=dialog.findViewById(R.id.home_dialog_cancel);
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"upload cancel",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new BookdrFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.nav_host_fragment, newFragment);

                transaction.commit();
              /* Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.move_left);
                img1.startAnimation(animation1);
                Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.move_left);
                text1.startAnimation(animation2);*/
            }
        });
        live_chat=root.findViewById(R.id.home_btn_livechat);
        live_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LiveChatBot.class));
            }
        });


        return root;
    }

    private void pickImages() {
        CropImageRequest.getrCropImageRequest().start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            CropImageRequest.getrCropImageRequest(uri).start(getContext(), this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                fileUri = uri;
                if (fileUri != null)
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        upload.setImageBitmap(bitmap);

                        filePath = BitmapUtils.getImageFilePath(uri, getActivity());
                        log("Path = " + filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }


    private void uploadPrescription() {

        String path = filePath;
        if (path == null) {
            //Toast.makeText(getActivity(), "null "+path, Toast.LENGTH_SHORT).show();
            log("Path null");
            return;
        } else {
            //Toast.makeText(getActivity(), "not null "+path, Toast.LENGTH_SHORT).show();
            log("Path not null");
        }
        File file = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        user = SharedPreferencesManager.getCurrentUser();
        String user_idd = user.getUid();

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), user_idd);
        RequestBody upld = RequestBody.create(MediaType.parse("text/plain"), upd);
        RequestBody uplde = RequestBody.create(MediaType.parse("text/plain"), upde);
        RequestBody uplp = RequestBody.create(MediaType.parse("text/plain"), upp);
        RequestBody uplv = RequestBody.create(MediaType.parse("text/plain"), upv);

        Call<Response> call = ApiUtils.getScalarUrl().uploadpre(body, user_id, upld, uplde, uplp, uplv);

        call.enqueue(new Callback<com.example.harihara_medicals.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.harihara_medicals.Model.Response> call, retrofit2.Response<Response> response) {
                Toast.makeText(getContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                //Log.d("homefrg", ""+response);
            }

            @Override
            public void onFailure(Call<com.example.harihara_medicals.Model.Response> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //Log.d("homefrg", ""+t.getLocalizedMessage());
            }
        });

    }

    private void updateLabel() {
        String myfromat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(myfromat, Locale.UK);
        up_dtvst.setText(sdf.format(mycalender.getTime()));
    }

}