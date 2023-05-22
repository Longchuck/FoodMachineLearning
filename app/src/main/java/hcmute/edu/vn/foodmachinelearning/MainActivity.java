package hcmute.edu.vn.foodmachinelearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.metadata.MetadataExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foodmachinelearning.ml.LiteModelAiyVisionClassifierFoodV11;

public class MainActivity extends AppCompatActivity {

    Button selectBtn, predictBtn, captureBtn;
//    TextView result;

    ListView listViewName;
    ImageView imageView;
    Bitmap bitmap;
    Interpreter interpreter;
    List<String>  labels;
    List<String> classes = new ArrayList<>();



    MappedByteBuffer byteBufferModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        selectBtn = findViewById(R.id.button_gallery);
        predictBtn = findViewById(R.id.button_identify);
        captureBtn = findViewById(R.id.button_capture);
        listViewName = findViewById(R.id.list_view_name);
        imageView = findViewById(R.id.image_view);

        getPermission();


        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("food_classification_model", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel model) {
                        // Download complete. Depending on your app, you could enable the ML
                        // feature, or switch from the local model to the remote model, etc.

                        // The CustomModel object contains the local path of the model file,
                        // which you can use to instantiate a TensorFlow Lite interpreter.
                        File modelFile = model.getFile();
                        if (modelFile != null) {
                            interpreter = new Interpreter(modelFile);

                            try {
                                byteBufferModel=loadModelToByteBuffer(modelFile);
                                labels= loadLabelsFromMetadata(byteBufferModel,"probability-labels-en.txt");
                                String labelmapUrl ="https://www.gstatic.com/aihub/tfhub/labelmaps/aiy_food_V1_labelmap.csv"; // URL của file CSV chứa danh sách tên lớp
                                new AsyncTask<Void, Void, List<String>>() {
                                    @Override
                                    protected List<String> doInBackground(Void... voids) {
                                        try {
                                            URL url = new URL(labelmapUrl);
                                            InputStream is = new URL(labelmapUrl).openStream();

                                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                            String line;
                                            while ((line = br.readLine()) != null) {
                                                String[] parts = line.split(",");
                                                if (parts.length >= 2) {
                                                    classes.add(parts[1]);
                                                }
                                            }
                                            br.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return classes;
                                    }

                                    @Override
                                    protected void onPostExecute(List<String> br) {
                                        super.onPostExecute(br);
                                        // Tiếp tục xử lý ảnh và đoạn mã của bạn ở đây
                                        if (br != null) {
                                        }
                                    }
                                }.execute();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }
                });


//        int cnt = 0;
//        String[] labels = new String [2023];
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("probability-labels.txt")));
//            String line = bufferedReader.readLine();
//            while (line!=null){
//                labels[cnt]= line;
//                cnt++;
//                line = bufferedReader.readLine();
//
//            }
//        }


        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 11);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                    MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(MainActivity.this);

//                 Creates inputs for reference.
//              TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3},  DataType.UINT8);

                //Use model deployed on firebase
                bitmap = Bitmap.createScaledBitmap(bitmap, 192, 192, true);
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//                ByteBuffer inputBuffer = ByteBuffer.allocateDirect(192 * 192 * 3).order(ByteOrder.nativeOrder());
//
//                inputBuffer.order(ByteOrder.nativeOrder());
//                int[] intValues = new int[192 * 192];
//                bitmap.getPixels(intValues, 0, 192, 0, 0, 192, 192);
//                for (int y = 0; y < 192; y++) {
//                    for (int x = 0; x < 192; x++) {
//                        int pixel = bitmap.getPixel(x, y);
//
//                        // Get the channel values from the pixel value
//                        int r = Color.red(pixel);
//                        int g = Color.green(pixel);
//                        int b = Color.blue(pixel);
//
//                        // Normalize the channel values to [0, 255]
//                        inputBuffer.put((byte) r);
//                        inputBuffer.put((byte) g);
//                        inputBuffer.put((byte) b);
//                    }
//                }
////
////                inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());
////                Tensor inputTensor = Tensor.(input);
//
//                //  allocate a ByteBuffer large enough to contain the model's output
//                //  allocate a ByteBuffer large enough to contain the model's output
//                int bufferSize = 1000 * Float.SIZE / Byte.SIZE;
//                ByteBuffer modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());
//                interpreter.run(inputBuffer, modelOutput);
//                modelOutput.rewind();
//
//                float[] outputValues = new float[modelOutput.capacity() / 4];
//                modelOutput.asFloatBuffer().get(outputValues);

                List<Category> probability = new ArrayList<>();
                try {
                    LiteModelAiyVisionClassifierFoodV11 model = LiteModelAiyVisionClassifierFoodV11.newInstance(MainActivity.this);

                    // Creates inputs for reference.
                    TensorImage image = TensorImage.fromBitmap(bitmap);

                    // Runs model inference and gets result.
                    LiteModelAiyVisionClassifierFoodV11.Outputs outputs = model.process(image);
                    probability = outputs.getProbabilityAsCategoryList();

//                    float maxProbability = 0f;
//                    int maxProbabilityIndex = -1;

                    List<Category> resultProbability = new ArrayList<>();
                    resultProbability.add(probability.get(0));  // the first food that have max score
                    resultProbability.add(probability.get(1));  // the second food that have second score
                    resultProbability.add(probability.get(2));  // the third food that have third score

//                  find three result that have highest score

                    for (int i = 0; i < probability.size(); i++) {
                        float currentProbability = probability.get(i).getScore();
                        if (currentProbability > resultProbability.get(0).getScore()) {
                            resultProbability.set(2,resultProbability.get(1));
                            resultProbability.set(1,resultProbability.get(0));
                            resultProbability.set(0,probability.get(i));
                        } else if (currentProbability > resultProbability.get(1).getScore()){
                            resultProbability.set(2,resultProbability.get(1));
                            resultProbability.set(1,probability.get(i));
                        } else if (currentProbability > resultProbability.get(2).getScore()){
                            resultProbability.set(2,probability.get(i));
                        }
                    }
//                    add list result into listView
                    System.out.println(resultProbability);
                    CustomApdapter customApdapter = new CustomApdapter(MainActivity.this, 0, resultProbability);
                    listViewName.setAdapter(customApdapter);

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }


//                int predictedIndex = 0;
//                float highestValue = 0.0f;
//                for (int i = 0; i < probability.length; i++) {
//                    if (probability[i] > highestValue) {
//                        highestValue = outputValues[i];
//                        predictedIndex = i;
//                    }
//                }


//                try {
//                    InputStream is = new URL(labelmapUrl).openStream();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        String[] parts = line.split(",");
//                        if (parts.length >= 2) {
//                            classes.add(parts[1]);
//                        }
//                    }
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


//                int finalPredictedIndex = predictedIndex;
//                new AsyncTask<Void, Void, BufferedReader>() {
//                    @Override
//                    protected BufferedReader doInBackground(Void... voids) {
//                        try {
//                            URL url = new URL(labelmapUrl);
//                            InputStream is = new URL(labelmapUrl).openStream();
//
//                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                            return br;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(BufferedReader br) {
//                        super.onPostExecute(br);
//                        // Tiếp tục xử lý ảnh và đoạn mã của bạn ở đây
//                        if (br != null) {
//                            try {
//                                String line;
//                                while ((line = br.readLine()) != null) {
//                                    String[] parts = line.split(",");
//                                    if (parts.length >= 2) {
//                                        classes.add(parts[1]);
//                                    }
//                                }
//                                br.close();
//                            }catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            String predictedClass = classes.get(finalPredictedIndex);
//
////                String predictedClass = labels.get(predictedIndex);
//                            Log.d("Prediction", predictedClass);
//                            result.setText(predictedClass);
//
//                        }
//                    }
//                }.execute();

// Get predicted class name

//                FloatBuffer probabilities = modelOutput.asFloatBuffer();
//                float[] floatArray = new float[probabilities.remaining()];
//
//                probabilities.get(floatArray);
////                try {
////                    BufferedReader reader = new BufferedReader(
////                            new InputStreamReader(getAssets().open("probability-labels.txt")));
////                    for (int i = 0; i < probabilities.capacity(); i++) {
////                        String label = reader.readLine();
////                        float probability = probabilities.get(i);
////                        Log.i(TAG, String.format("%s: %1.4f", label, probability));
////                    }
////                } catch (IOException e) {
////                    // File not found?
////                }
//                // Runs model inference and gets result.
////                    MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
////
////                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
////
//                System.out.println(getMax(floatArray));
                // Releases model resources if no longer used.


//
////                String predictedClass = labels.get(predictedIndex);
//                            Log.d("Prediction", predictedClass);
//                            result.setText(predictedClass);
                interpreter.close();

            }
        });

    }

    public MappedByteBuffer loadModelToByteBuffer(File modelFile) throws IOException {
        // Load the model file as a byte buffer
        FileInputStream inputStream = new FileInputStream(modelFile);
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = 0;
        long declaredLength = modelFile.length();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public static List<String> loadLabelsFromMetadata(MappedByteBuffer byteBufferModel, String labelFilename) throws IOException {
        // Load the metadata of the TFLite model
        MetadataExtractor metadataExtractor = new MetadataExtractor(byteBufferModel);

        List<String> labels = new ArrayList<>();
        int cnt = 0;
        String[] label = new String [2024];
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     metadataExtractor.getAssociatedFile(labelFilename)))) {
            String line = br.readLine();
            while (line!=null){

//                label[cnt]= line;
                labels.add(line);
                cnt++;
                line = br.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }



        return labels;
    }
    private int getMax(float[] v) {
        int max = 0 ;
        for (int i = 0 ; i<v.length;i++){
            if (v[i]>v[max]) max = i;
        }
        System.out.println("Max: "+max);
        return max;
    }

    private void getPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA},11);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==11){
            if (grantResults.length>0){
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if ( requestCode==11){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}