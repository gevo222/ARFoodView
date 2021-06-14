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
    String actualModel = " ";
    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        String modelChosen = Menu_Window.model;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("Model chosen in Ar is: " + modelChosen);
        switch (modelChosen) {
            case "Ramen":
                actualModel = "model.sfb";
                break;
            case "applePie":
                actualModel = "applePie.sfb";
                break;
            case "Candy Bowl":
                actualModel = "objCandtBowl.sfb";
                break;
            case "Pizza":
                actualModel = "pizza.sfb";
                break;
            case "Apple Strudel":
                actualModel = "AppleStrudel.sfb";
                break;
            default:
                System.out.println("3D file not found");
                break;
        }
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                Anchor anchor = hitResult.createAnchor();
                ModelRenderable.builder().setSource(this, Uri.parse(actualModel)).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
            });
        }
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        transformableNode.getScaleController().setMaxScale(0.5f);// this is actually for sizing
        transformableNode.getScaleController().setMinScale(0.4f);
        Vector3 vector3 = new Vector3(0.0f, 0.0f, 0.0f);
        transformableNode.setLocalScale(vector3);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
