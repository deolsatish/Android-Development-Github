package com.example.foodrescueapp2;




        import androidx.annotation.NonNull;
        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Matrix;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.graphics.RectF;
        import android.hardware.camera2.CameraAccessException;
        import android.hardware.camera2.CameraCharacteristics;
        import android.hardware.camera2.CameraManager;
        import android.media.Image;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Log;
        import android.util.SparseIntArray;
        import android.view.Surface;
        import android.view.View;
        import android.widget.ImageView;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.mlkit.vision.common.InputImage;
        import com.google.mlkit.vision.objects.DetectedObject;
        import com.google.mlkit.vision.objects.ObjectDetection;
        import com.google.mlkit.vision.objects.ObjectDetector;
        import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
        import com.google.mlkit.vision.objects.defaults.PredefinedCategory;

        import java.io.ByteArrayOutputStream;
        import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.util.List;

        import static com.example.foodrescueapp2.R.drawable.img_meal_one;
        import static com.example.foodrescueapp2.R.drawable.img_meal_two;


public class ObjectDetectionActivity extends AppCompatActivity {

    private static final String TAG = "MLKIT";
    private static final String MY_CAMERA_ID = "my_camera_id";

    Bitmap bitmap;

    private InputImage imageFromBitmap(Bitmap bitmap) {
        int rotationDegree = 0;
        // [START image_from_bitmap]
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);
        // [END image_from_bitmap]
        return image;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detection);

        /*// Live detection and tracking
        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                        .enableClassification()  // Optional
                        .build();*/

        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()  // Optional
                        .build();

        ObjectDetector objectDetector = ObjectDetection.getClient(options);


        Intent intent = getIntent();
        String filename=intent.getStringExtra("filename");
        Bitmap bitmaps=null;
        try {
            bitmaps = BitmapFactory.decodeStream(ObjectDetectionActivity.this.openFileInput("myImage"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           bitmaps=null;
        }
        ImageView imageView=findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmaps);
        if(bitmaps==null)
        {
            bitmaps = BitmapFactory.decodeResource(ObjectDetectionActivity.this.getResources(), img_meal_two);
        }


        //Bitmap bitmaps = BitmapFactory.decodeResource(ObjectDetectionActivity.this.getResources(), img_meal_two);
        //Bitmap bitmaps=getResizedBitmap(bitmap1,bitmap1.getWidth()*0.2,bitmap1.getHeight()*0.2);
        InputImage image;
        //InputImage image = imageFromBitmap(bitmap);
        image = InputImage.fromBitmap(bitmaps, 0);
        Log.i("TAG",String.valueOf(487984146));



        Bitmap outputBitmap=bitmaps.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas= new Canvas(outputBitmap);
        Paint pen= new Paint();
        pen.setTextAlign(Paint.Align.LEFT);


        objectDetector.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<DetectedObject>>() {
                            @RequiresApi(api = Build.VERSION_CODES.Q)
                            @Override
                            public void onSuccess(List<DetectedObject> detectedObjects) {
                                for (DetectedObject detectedObject : detectedObjects) {
                                    Rect boundingBox = detectedObject.getBoundingBox();
                                    Integer trackingId = detectedObject.getTrackingId();
                                    for (DetectedObject.Label label : detectedObject.getLabels()) {
                                        String text = label.getText();

                                        if (PredefinedCategory.FOOD.equals(text)) {

                                        }
                                        int index = label.getIndex();
                                        if (PredefinedCategory.FOOD_INDEX == index) {

                                        }
                                        float confidence = label.getConfidence();

                                        Log.i("PROCESSFOOD index",String.valueOf(index));
                                        Log.i("PROCESSFOOD text",text);
                                        Log.i("PROCESSFOOD confidence",String.valueOf(confidence));


                                        pen.setColor(Color.RED);
                                        pen.setStrokeWidth(8F);
                                        pen.setStyle(Paint.Style.STROKE);
                                        Rect box = boundingBox;
                                        canvas.drawRect(box,pen);
                                        Rect tagSize;
                                        tagSize=new Rect(0,0,0,0);
                                        pen.setStyle(Paint.Style.FILL_AND_STROKE);
                                        pen.setColor(-256);
                                        pen.setStrokeWidth(2.0F);
                                        pen.setTextSize(96.0F);
                                        pen.getTextBounds(label.getText(),0,label.getText().length(),tagSize);
                                        float fontSize= pen.getTextSize() * box.width() / (float)tagSize.width();
                                        float margin;
                                        if (fontSize < pen.getTextSize()) {
                                            pen.setTextSize(fontSize);
                                        }
                                        margin = (box.width() - (float)tagSize.width()) / 2.0F;
                                        if (margin < 0.0F) {
                                            margin = 0.0F;
                                        }
                                        canvas.drawText(label.getText(),box.left+margin,box.top+tagSize.height()*(1F),pen);





                                    }
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });

        Log.i("Finished","Finished properly");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(outputBitmap!=null)
                {
                    ImageView imageView=findViewById(R.id.imageView);
                    imageView.setImageBitmap(outputBitmap);
                }

            }
        });
    }
    byte[] image;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(is);
            ImageView imageView=findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            image=stream.toByteArray();
            Log.i("bitmap",bitmap.toString());

        }

    }
    private void selectImage() {

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);*/


        Intent in = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        in.putExtra("crop", "true");
        in.putExtra("outputX", 100);
        in.putExtra("outputY", 100);
        in.putExtra("scale", true);
        in.putExtra("return-data", true);
        startActivityForResult(in, 1);
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



    public void buttonfunction(View view) {

    }
}