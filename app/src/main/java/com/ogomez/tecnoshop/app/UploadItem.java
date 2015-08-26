package com.ogomez.tecnoshop.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ogomez.tecnoshop.app.Objects.ItemP;
import com.ogomez.tecnoshop.app.RestClient.Upload;

public class UploadItem extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imgThumb;
    Button btn_guardar;
    EditText txt_nombre,txt_descripcion,txt_precio,txt_local;
    Spinner spn_categoria;
    String txt_categoria;
    File imgFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);

        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        txt_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        txt_precio = (EditText)findViewById(R.id.txt_precio);
        txt_local = (EditText)findViewById(R.id.txt_local);
        spn_categoria = (Spinner)findViewById(R.id.spn_categoria);


        imgThumb = (ImageView)findViewById(R.id.imgThumb);
        btn_guardar = (Button)findViewById(R.id.btn_guardar);

        //Para agregar una nueva imagen
        imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //para guardar
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_categoria = spn_categoria.getSelectedItem().toString();

                if(txt_categoria.equals("Selecciona una categoria")){
                    Snackbar.make(findViewById(android.R.id.content), "Debes seleccionar una categoria", Snackbar.LENGTH_LONG).show();
                }else {

                    if (imgFile != null) {

                        SharedPreferences prefs = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                        String id_user = prefs.getString("id_user", "");


                        ItemP o = new ItemP();
                        o.setId_user(Integer.parseInt(id_user));
                        o.setNombre(txt_nombre.getText().toString());
                        o.setCategoria(txt_categoria);
                        o.setDescripcion(txt_descripcion.getText().toString());

                        Double _precio = (!txt_precio.getText().toString().equals("")) ? Double.parseDouble(txt_precio.getText().toString()) : 0.0;
                        o.setPrecio(_precio);

                        int _local = (!txt_local.getText().toString().equals("")) ? Integer.parseInt(txt_local.getText().toString()) : 0;
                        o.setLocal(_local);
                        o.setImg(imgFile);

                        //crea el objeto y se lo enviamos al Api REST
                        Upload.insert(UploadItem.this, o);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Debes agregar una imagen", Snackbar.LENGTH_LONG).show();
                    }
                }


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Uri fileUri;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );


        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "TecnoApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                //Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String i = compressImage(fileUri.getPath(),UploadItem.this);
            imgFile = new  File(i);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imgThumb.setImageBitmap(myBitmap);

            }
            imgThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgThumb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imgThumb.setImageURI(Uri.fromFile(imgFile));


        }
    }


    static Activity act;
    public String compressImage(String imageUri, Activity actividad) {
        act = actividad;
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not
        // loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as
        // 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the
        // image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        // setting inSampleSize value allows to load a scaled down version of
        // the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low
        // on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            //Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                //Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                //Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                //Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

            // write the compressed bitmap at the destination specified by
            // filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "TecnoApp/Uploads");

        if (!file.exists()) {
            file.mkdirs();
            try {
                File output = new File(file.getAbsoluteFile(), ".nomedia");
                output.createNewFile();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor =  UploadItem.this.getApplicationContext().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
}
