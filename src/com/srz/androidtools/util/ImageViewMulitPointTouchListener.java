package com.srz.androidtools.util;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageViewMulitPointTouchListener implements OnTouchListener {
    private static final String TAG = "Touch";
    float scaleToview = 1f;
    float tempscale = 1f;
    private boolean isfirst = true;
    
    Matrix matrix = new Matrix();

    Matrix savedMatrix = new Matrix();
    //保存初始matrix
    Matrix originalMatrix = new Matrix() ;
   
    static final int NONE = 0;

    static final int DRAG = 1;

    static final int ZOOM = 2;

    int mode = NONE;

     
    PointF start = new PointF(); 
    PointF mid = new PointF(); 
    float oldDist = 1f;
    private boolean isZoomPostScaleLock =false;
    
    boolean istoosmall =false;
    //保持imageview初始上下左右用
    float originalright = 0f;
    float originalleft = 0f;
    float originaltop = 0f;
    float originalbottom = 0f;

    public ImageViewMulitPointTouchListener(Matrix imageMatrix) {
        this.originalMatrix.set(imageMatrix)  ;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        ImageView view = (ImageView) v;

        dumpEvent(event);
 
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:

            matrix.set(view.getImageMatrix());
            savedMatrix.set(matrix);
            start.set(event.getX(), event.getY());
 
            mode = DRAG;
 
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            oldDist = spacing(event);
           
            if (oldDist > 10f) {
                savedMatrix.set(matrix);
                midPoint(mid, event);
                mode = ZOOM;
              
            }
            break;
        case MotionEvent.ACTION_UP:
         
           if (istoosmall ) {
               matrix.set( this.originalMatrix); 
               istoosmall = false;
            }
           
             
        case MotionEvent.ACTION_POINTER_UP:
            mode = NONE;
 
            break;
        case MotionEvent.ACTION_MOVE:
            if (mode == DRAG) {
 
                matrix.set(savedMatrix);
                
                //得到图片其中drawable的上下左右    
                Rect rect = view.getDrawable().getBounds(); 
                float[] values = new float[9];
                matrix.getValues(values);
                float left = values[Matrix.MTRANS_X];
                float top =  values[Matrix.MTRANS_Y];
                float right = left + rect.width()*values[Matrix.MSCALE_X];
                float bottom = top + rect.height() *values[Matrix.MSCALE_X];
                if (isfirst) {
                    originalright = right;
                    originalleft = left;
                    originaltop = top;
                    originalbottom = bottom;
                    isfirst = false;
                } 
                float dx = event.getX() - start.x;
                float dy =event.getY() - start.y;
                if(left+dx >=originalleft) {
                    
                    dx = -left;
                    left = originalleft ; 
                }
                if(right+dx <=originalright) { 
                    dx = originalright-right;
                    right=originalright; 
                } 
                if(top+dy >= originaltop) { 
                    dy = -top;
                    top = originaltop;   
                }
        
              if(bottom+dy <= originalbottom) { 
                    dy = originalbottom-bottom;
                    bottom = originalbottom; 
                } 
              matrix.postTranslate(dx,dy);
            } else if (mode == ZOOM) {
                float newDist = spacing(event);
 
                if (newDist > 10f) {
                    matrix.set(savedMatrix);
                    float scale = newDist / oldDist; 
                    tempscale  =  scale; 
                         if(scale <1f&&scaleToview*scale<1f) {
                        istoosmall = true;
                         }      
                         else {
                             istoosmall = false;
                         }
                        matrix.postScale(scale, scale, mid.x, mid.y);
                     
                        
                
                }
            }
            break;
        }
      
        
        
        view.setImageMatrix(matrix);
        return true; 
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        // Log.d(TAG, sb.toString());
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}