package com.example.harihara_medicals.utils;

import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageRequest {
    private static final int IMAGE_WIDTH = 2000;
    private static final int IMAGE_HEIGHT = 2000;

    //get crop image request when user wants to change his photo
    public static CropImage.ActivityBuilder getCropImageRequest() {
        return CropImage.activity()
                .setMaxCropResultSize(IMAGE_WIDTH, IMAGE_HEIGHT)
                .setAspectRatio(1,1)
                //.setCropShape(CropImageView.CropShape.OVAL)
                //.setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(false);
    }

    public static CropImage.ActivityBuilder getCropImageRequest(Uri uri) {
        return CropImage.activity(uri)
                .setMaxCropResultSize(IMAGE_WIDTH, IMAGE_HEIGHT)
                .setAspectRatio(1,1)
                //.setCropShape(CropImageView.CropShape.RECTANGLE)
                //.setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(false);
    }

    //get crop image request when user wants to change his photo
    public static CropImage.ActivityBuilder getrCropImageRequest() {
        return CropImage.activity()
                //.setMaxCropResultSize(RIMAGE_WIDTH, RIMAGE_HEIGHT)
                //.setAspectRatio(1,4)
                //.setCropShape(CropImageView.CropShape.OVAL)
                //.setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(false);
    }

    public static CropImage.ActivityBuilder getrCropImageRequest(Uri uri) {
        return CropImage.activity(uri)
                //.setMaxCropResultSize(IMAGE_WIDTH, IMAGE_HEIGHT)
                //.setAspectRatio(1,4)
                //.setCropShape(CropImageView.CropShape.RECTANGLE)
                //.setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(false);
    }
}
