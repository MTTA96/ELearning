package com.eways.elearning.Handler;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class XuLyHinhAnh_FirebaseStorage {

    public XuLyHinhAnh_FirebaseStorage() {
    }

    public byte[] XuLy(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

}
