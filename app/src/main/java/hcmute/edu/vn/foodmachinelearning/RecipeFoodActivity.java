package hcmute.edu.vn.foodmachinelearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import hcmute.edu.vn.foodmachinelearning.Interface.SpoonacularAPI;
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
    RecyclerView ingredientRecylerView;

    LinearLayout recipeDetailLayout;

    IngredientsAdapter ingredientsAdapter;

    private View loadingView;

    private SpoonacularAPI spoonacularAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_food);


        ingredientRecylerView = findViewById(R.id.food_recycler_ingredients);
        recipeDetailLayout = findViewById(R.id.recipe_detail_Layout);
        imageFood = findViewById(R.id.recipe_image);
        foodNameTitle = findViewById(R.id.food_recipe_name);
        loadingView = findViewById(R.id.layout_loading);
        foodSummary = findViewById(R.id.recipe_summary);
        int RecipeId = Integer.parseInt(getIntent().getStringExtra("id"));

//        foodNameTitle.setText(foodName);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spoonacularAPI = retrofit.create(SpoonacularAPI.class);

        showLoading();
        callAPI(RecipeId);




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


    public void callAPI(int id){
        String apiKey = "b4573860b7ab4fda8e3530bc4c807030";
        Call<RecipeInformation> call = spoonacularAPI.getRecipeInformation(id,apiKey);
        call.enqueue(new Callback<RecipeInformation>() {
            @Override
            public void onResponse(Call<RecipeInformation> call, Response<RecipeInformation> response) {
                if (response.isSuccessful()) {
                    RecipeInformation recipe = response.body();

                    if (recipe != null ) {
                        // Thực hiện các thao tác với  recipe detail

                        hideLoading();

                        // ...
                    } else {

                        System.out.println("Không lấy được thông tin");
                        // Xử lý trường hợp danh sách rỗng
                    }

                    Picasso.get().load(recipe.getImage()).into(imageFood);
                    foodNameTitle.setText(recipe.getTitle());


                    String summary = recipe.getSummary();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        foodSummary.setText(Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY));
                    } else
                        foodSummary.setText(Html.fromHtml(summary));

                    ingredientRecylerView.setHasFixedSize(true);
                    ingredientRecylerView.setLayoutManager(new LinearLayoutManager(RecipeFoodActivity.this,LinearLayoutManager.VERTICAL,false));

                    ingredientsAdapter = new IngredientsAdapter(RecipeFoodActivity.this,  recipe.getExtendedIngredients());
                    ingredientRecylerView.setAdapter(ingredientsAdapter);
//                    Lấy thông tin món đầu tiên
//                  Xử lý kết quả từ API tại đây
//
                }
                else {
                    // Xử lý lỗi từ API tại đây
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipeInformation> call, Throwable t) {

            }


        });

    }
}
