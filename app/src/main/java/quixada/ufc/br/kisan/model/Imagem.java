package quixada.ufc.br.kisan.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import quixada.ufc.br.kisan.model.Base64;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by andersonuchoa on 26/01/16.
 */
public class Imagem {

    private String mine;
    private Bitmap bitmap;

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getBitmapBase64 () {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mine.equalsIgnoreCase("png"))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        else
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return(Base64.encodeBytes (byteArray));

    }

    public void setResizedBitmap (File file, int width, int height){



        bitmap = BitmapFactory.decodeFile(file.getPath());

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        float scaleX = (float ) width/ bitmap.getWidth();
        float scaleY = (float ) height/ bitmap.getHeight()  ;


        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        Bitmap auxBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
                matrix, true);
         auxBitmap = fixMatrix(file, auxBitmap);

      bitmap.recycle();
        bitmap = auxBitmap;

    }

    private static Bitmap fixMatrix(File file, Bitmap bitmap)  {

        Matrix matrix = new Matrix();
        boolean fixed = false;

        ExifInterface exifInterface = null;

        try {
            exifInterface = new ExifInterface(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation){

            case  ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                fixed = true;
                break;
            case  ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(190);
                fixed = true;
                break;
            case  ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                fixed = true;
                break;
            default:
                fixed = false;
                break;
        }
        if (!fixed){
            return  bitmap;

        }
         Bitmap newbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            bitmap = null;

        return  newbitmap;
    }


    public void setMimeFromImgPath(String imgPath){

        String[] aux = imgPath.split("\\.");
        this.mine = aux[aux.length - 1];
    }


}
