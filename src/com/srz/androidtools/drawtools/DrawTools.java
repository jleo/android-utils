package com.srz.androidtools.drawtools;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Button;

public class DrawTools {
    
    //drawable 变灰
    public static Drawable xx(Drawable mDrawable ,float sat) { 
        mDrawable.mutate();
        ColorMatrix cm = new ColorMatrix();  
        cm.setSaturation(sat); 
        mDrawable.setColorFilter(new ColorMatrixColorFilter(cm)); 
        return mDrawable ;
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTempStorage = new byte[128 * 1024];
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8; 
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565; // 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable getgetRoundedCornerDrawable(Drawable drawable) {
        return new BitmapDrawable(getRoundedCornerBitmap(
                drawableToBitmap(drawable), 5));

    }

    // drawable缩放,w希望缩放到的width
    public static Drawable zoomDrawable(Drawable drawable, int w) {
        return zoomDrawable(drawable, w, 5f);
    }

    public static Drawable zoomDrawable(Drawable drawable, int w, float roundPx) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scale = ((float) w / width);
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        oldbmp.recycle();
        drawable = new BitmapDrawable(getRoundedCornerBitmap(newbmp, 5));
        return drawable;
    }

    public static Bitmap limitBitmap(Bitmap bitmap, int maxlength) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        float scale;
        if (width > maxlength || height > maxlength) {
            if (width >= height) {
                scale = ((float) maxlength / width);
            } else {
                scale = ((float) maxlength / height);
            }
            matrix.postScale(scale, scale);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);

        } else {
            return bitmap;
        }

    }

    // 画圆角
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
