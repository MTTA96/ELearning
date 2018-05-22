package com.eways.elearning.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nguyentandat on 11/27/17.
 */

abstract public class OnClickTransparentListener implements View.OnTouchListener{

    private boolean touchTransparent;

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
                int color = bmp.getPixel((int) event.getX(), (int) event.getY());
                if (color == Color.TRANSPARENT) {   // If we touch down on something transparent, don't care --> pass it.
                    touchTransparent = true;

                    return false;
                }
                else {  // If we touch down on something not transparent, steal it.
                    touchTransparent = false;

                    return true;
                }

//                break;

            case MotionEvent.ACTION_UP:

                if(touchTransparent){
                    click_transparent(view);
                }else {
                    click_not_transparent(view);
                }

                break;
        }

        return false;
    }

    abstract public void click_not_transparent(View view);

    abstract public void click_transparent(View view);
}
