package hcmute.edu.vn.foodmachinelearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeFoodActivity extends AppCompatActivity {

    ImageView imageFood;
    TextView foodNameTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_food);

        imageFood = findViewById(R.id.recipe_image);
        foodNameTitle = findViewById(R.id.recipe_title);

        String foodName = getIntent().getStringExtra("foodName");

        foodNameTitle.setText(foodName);


//        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        imageFood.setImageBitmap(bitmap);
    }
}
