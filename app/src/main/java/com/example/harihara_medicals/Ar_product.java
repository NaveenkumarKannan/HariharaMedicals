package com.example.harihara_medicals;


import android.app.Activity;
import android.app.ActivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.RequiresApi;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Vertex;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.common.math.Quantiles;
import java.util.Vector;

import static com.example.harihara_medicals.R.raw.pills;


public class Ar_product extends AppCompatActivity {
    private static final String TAG = Ar_product.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;


    //Create a member variable for ModelRenderable//

    private ModelRenderable dinoRenderable,pills;


    //Create a member variable for ArFragment//

    private ArFragment arCoreFragment,arFragment;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkDevice((this))) {
            return;
        }

        setContentView(R.layout.activity_ar_product);
        arCoreFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        arFragment=(ArFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        if (Build.VERSION.SDK_INT >= VERSION_CODES.N) {
            ModelRenderable.builder()
                    .setSource(this, R.raw.pills2)
                    .build()
                    .thenAccept(renderable -> dinoRenderable = renderable)
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Unable to load renderable");
                                return null;
                            });
            ModelRenderable.builder()
                    .setSource(this,R.raw.pills)
                    .build()
                    .thenAccept(modelRenderable -> pills=modelRenderable)
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG,"Unable to load renderable");
                                return  null;
                            });

        }

        arCoreFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

                   /* if (dinoRenderable == null) {
                        return;
                    }*/


                    Anchor anchor = hitResult.createAnchor();

                    //Build a node of type AnchorNode//

                    AnchorNode anchorNode = new AnchorNode(anchor);

                    //Connect the AnchorNode to the Scene//

                    anchorNode.setParent(arCoreFragment.getArSceneView().getScene());

                    //Build a node of type TransformableNode//

                    TransformableNode transformableNode = new TransformableNode(arCoreFragment.getTransformationSystem());
                    transformableNode.getScaleController().setMaxScale(0.19f);
                    transformableNode.getScaleController().setMinScale(0.01f);

                    //transformableNode.getLocalRotation(Quantiles.scale(new Vector<1f,0,0>),90f);
                    //Connect the TransformableNode to the AnchorNode//

                    transformableNode.setParent(anchorNode);

                    //Attach the Renderable//

                    transformableNode.setRenderable(dinoRenderable);

                    //Set the node//

                    transformableNode.select();
                });
    }
    public static boolean checkDevice(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or higher");
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()

                 //Check the version of OpenGL ES//

                        .getGlEsVersion();

                 //If the device is running anything less than OpenGL ES 3.0...//

        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {

                 //...then print the following message to Logcat//

            Log.e(TAG, "Requires OpenGL ES 3.0 or higher");
            activity.finish();
            return false;
        }
        return true;
    }
}
