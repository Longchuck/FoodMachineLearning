package hcmute.edu.vn.foodmachinelearning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import hcmute.edu.vn.foodmachinelearning.Interface.SpoonacularAPI;
import hcmute.edu.vn.foodmachinelearning.model.Instructions;
import hcmute.edu.vn.foodmachinelearning.model.InstructionsResponse;
import hcmute.edu.vn.foodmachinelearning.model.RecipeInformation;
import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeFoodActivity extends AppCompatActivity {

    ImageView imageFood;
    TextView foodNameTitle,foodSummary;
    RecyclerView ingredientRecylerView, instructionRecyclerView;

    Button btnInstructions;

    LinearLayout recipeDetailLayout;

    IngredientsAdapter ingredientsAdapter;

    private View loadingView;

    private SpoonacularAPI spoonacularAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_food);

        instructionRecyclerView = findViewById(R.id.food_recycler_instruction);
        ingredientRecylerView = findViewById(R.id.food_recycler_ingredients);
        recipeDetailLayout = findViewById(R.id.recipe_detail_Layout);
        imageFood = findViewById(R.id.recipe_image);
        foodNameTitle = findViewById(R.id.food_recipe_name);
        loadingView = findViewById(R.id.layout_loading);
        foodSummary = findViewById(R.id.recipe_summary);
//        btnInstructions = findViewById(R.id.btn_instructions);



        int RecipeId = Integer.parseInt(getIntent().getStringExtra("id"));

//        foodNameTitle.setText(foodName);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spoonacularAPI = retrofit.create(SpoonacularAPI.class);

        showLoading();
        callAPIs(RecipeId);




//        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        imageFood.setImageBitmap(bitmap);
    }


    private void showLoading() {
        recipeDetailLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        recipeDetailLayout.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }


//    public void callAPI(int id){
//        String apiKey = "b4573860b7ab4fda8e3530bc4c807030";
//        Call<RecipeInformation> call = spoonacularAPI.getRecipeInformation(id,apiKey);
//        call.enqueue(new Callback<RecipeInformation>() {
//            @Override
//            public void onResponse(Call<RecipeInformation> call, Response<RecipeInformation> response) {
//                if (response.isSuccessful()) {
//                    RecipeInformation recipe = response.body();
//
//                    if (recipe != null ) {
//                        // Thực hiện các thao tác với  recipe detail
//
//                        hideLoading();
//
//                        // ...
//                    } else {
//
//                        System.out.println("Không lấy được thông tin");
//                        // Xử lý trường hợp danh sách rỗng
//                    }
//
//                    Picasso.get().load(recipe.getImage()).into(imageFood);
//                    foodNameTitle.setText(recipe.getTitle());
//
//
//                    String summary = recipe.getSummary();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        foodSummary.setText(Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY));
//                    } else
//                        foodSummary.setText(Html.fromHtml(summary));
//
//                    ingredientRecylerView.setHasFixedSize(true);
//                    ingredientRecylerView.setLayoutManager(new LinearLayoutManager(RecipeFoodActivity.this,LinearLayoutManager.VERTICAL,false));
//
//                    ingredientsAdapter = new IngredientsAdapter(RecipeFoodActivity.this,  recipe.getExtendedIngredients());
//                    ingredientRecylerView.setAdapter(ingredientsAdapter);
//
//
//                    btnInstructions.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(RecipeFoodActivity.this, InstructionFoodActivity.class);
//
//                            intent.putExtra("id",id+"" );
//                            intent.putExtra("foodName", recipe.getTitle() );
//
//                            startActivity(intent);
//                        }
//                    });
////                    Lấy thông tin món đầu tiên
////                  Xử lý kết quả từ API tại đây
////
//                }
//                else {
//                    // Xử lý lỗi từ API tại đây
//                    System.out.println(response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecipeInformation> call, Throwable t) {
//
//            }
//
//
//        });
//    }

    private void callAPIs(int id) {
        String apiKey = "b4573860b7ab4fda8e3530bc4c807030";

        CompletableFuture<List<Instructions>> call1 = CompletableFuture.supplyAsync(() -> {
            Call<List<Instructions>> call = spoonacularAPI.getInstructions(id, apiKey);
            try {
                Response<List<Instructions>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    // Xử lý lỗi từ API
                    return null;
                }
            } catch (IOException e) {
                // Xử lý lỗi kết nối
                return null;
            }
        });

        CompletableFuture<RecipeInformation> call2 = CompletableFuture.supplyAsync(() -> {
            // Gọi API thứ hai tương tự như call1
            // Return kết quả
            Call<RecipeInformation> call = spoonacularAPI.getRecipeInformation(id, apiKey);
            try {
                Response<RecipeInformation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    // Xử lý lỗi từ API
                    return null;
                }
            } catch (IOException e) {
                // Xử lý lỗi kết nối
                return null;
            }
        });

        CompletableFuture.allOf(call1, call2).join();

        List<Instructions> instructions = call1.join();
        RecipeInformation recipeInformation = call2.join();

        if (instructions != null && recipeInformation != null) {
            hideLoading();

            //Xử lý dữ liệu từ callAPI2
            Picasso.get().load(recipeInformation.getImage()).into(imageFood);
            foodNameTitle.setText(recipeInformation.getTitle());


            String summary = recipeInformation.getSummary();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                foodSummary.setText(Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY));
            } else
                foodSummary.setText(Html.fromHtml(summary));

            ingredientRecylerView.setHasFixedSize(true);
            ingredientRecylerView.setLayoutManager(new LinearLayoutManager(RecipeFoodActivity.this,LinearLayoutManager.VERTICAL,false));

            ingredientsAdapter = new IngredientsAdapter(RecipeFoodActivity.this,  recipeInformation.getExtendedIngredients());
            ingredientRecylerView.setAdapter(ingredientsAdapter);

            //Xử lý dữ liệu từ callAPI1
            instructionRecyclerView.setHasFixedSize(true);
            instructionRecyclerView.setLayoutManager(new LinearLayoutManager(RecipeFoodActivity.this,LinearLayoutManager.HORIZONTAL,false));
            InstructionsAdapter instructionsStepAdapter =new InstructionsAdapter(RecipeFoodActivity.this,instructions);
            instructionRecyclerView.setAdapter(instructionsStepAdapter);
            // Xử lý và cập nhật giao diện với dữ liệu từ cả hai cuộc gọi API
            // ...
        } else {
            System.out.println("Không lấy được dữ liệu");
        }
    }
}
