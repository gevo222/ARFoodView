package com.example.arfoodview;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class Ar extends AppCompatActivity {
    private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

         arFragment =(ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
         arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) ->{
            Anchor anchor = hitResult.createAnchor();
           ModelRenderable.builder().setSource(this, Uri.parse("pizza.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable)).exceptionally(throwable ->{
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
              builder.setMessage(throwable.getMessage()).show();
             return null;
           });
         });
    }
    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable){
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        Vector3 vector3 = new Vector3(0.0f,0.0f,0.0f);// this is just for sizing
        transformableNode.setLocalScale(vector3);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
