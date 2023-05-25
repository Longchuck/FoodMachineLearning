package hcmute.edu.vn.foodmachinelearning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class InformationFoodActivity extends AppCompatActivity {


    Button btnRecipe;
    TextView foodTextView;

    ImageView imageFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_food);

        btnRecipe = findViewById(R.id.recipe_button);
        foodTextView = findViewById(R.id.food_name);
        imageFood = findViewById(R.id.food_image);


        String foodName = getIntent().getStringExtra("foodName");

        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);



        foodTextView.setText(foodName);
        imageFood.setImageBitmap(bitmap);



        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("foodImage", byteArray);

                startActivity(intent);
            }
        });
    }
}
