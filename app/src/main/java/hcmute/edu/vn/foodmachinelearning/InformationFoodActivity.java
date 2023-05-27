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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InformationFoodActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener{
//    Button btnRecipe;
//    TextView foodTextView;
//    ImageView imageFood;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    List<String> foodList;

    @Override
    public void onItemClick(String foodNameTemp) {

        Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);

        intent.putExtra("foodName", foodNameTemp);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_food);

        //get food name from activity_Main.layout when clicked.
        String foodName = getIntent().getStringExtra("foodName");

        recyclerView = findViewById(R.id.food_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //vi du tao mot danh sach thuc an de dua len recycle view
        foodList = new ArrayList<>();
        foodList.add("Pizza");
        foodList.add("Burger");
        foodList.add("Sushi");
        foodList.add("Pasta");
        foodList.add("Ice Cream");
        foodList.add("Steak");
        foodList.add("Tacos");
        foodList.add("Curry");
        foodList.add("Salad");
        foodAdapter = new FoodAdapter(foodList, this);
        recyclerView.setAdapter(foodAdapter);



//        btnRecipe = findViewById(R.id.recipe_button);
//        foodTextView = findViewById(R.id.food_name);
//        imageFood = findViewById(R.id.food_image);




//        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);



//        foodTextView.setText(foodName);
//        imageFood.setImageBitmap(bitmap);



//        btnRecipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);
//
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("foodImage", byteArray);
//
//                startActivity(intent);
//            }
//        });
    }


}
