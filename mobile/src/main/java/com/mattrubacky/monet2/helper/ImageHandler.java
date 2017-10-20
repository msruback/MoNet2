package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by mattr on 9/12/2017.
 */

public class ImageHandler {

    public ImageHandler(){}

    public boolean imageExists(String location,String name,Context cxt){
        File dir = cxt.getFileStreamPath(location);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(location,name);
        if(file.exists()) {
            return true;
        }else{
            return false;
        }
    }

    public Bitmap loadImage(String location,String name){
        File file = new File(location,name);
        try{
            Bitmap image = BitmapFactory.decodeStream(new FileInputStream(file));
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void downloadImage(final String location,final String name,String url,Context cxt){
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {

                    File myDir = new File(location, name);
                    FileOutputStream out = new FileOutputStream(myDir);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch(Exception e){
                    // some action
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        switch (location){
            case "stage":
                Picasso.with(cxt).load(url).resize(1280,720).into(target);
                break;
            default:
                Picasso.with(cxt).load(url).into(target);
                break;
        }
    }
}
