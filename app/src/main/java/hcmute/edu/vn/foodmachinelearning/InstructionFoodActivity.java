//package hcmute.edu.vn.foodmachinelearning;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//import hcmute.edu.vn.foodmachinelearning.Interface.SpoonacularAPI;
//import hcmute.edu.vn.foodmachinelearning.model.Instructions;
//import hcmute.edu.vn.foodmachinelearning.model.Recipe;
//import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class InstructionFoodActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener{
////    Button btnRecipe;
////    TextView foodTextView;
////    ImageView imageFood;
//    TextView tvInstructionName;
//    RecyclerView recyclerInstruction;
//    private View loadingView;
//    private SpoonacularAPI spoonacularAPI;
//
//    LinearLayout linearListInstruction;
//
//    @Override
//    public void onItemClick(int RecipeId) {
//
//        Intent intent = new Intent(InstructionFoodActivity.this, RecipeFoodActivity.class);
//
//        intent.putExtra("id", RecipeId+"");
//        startActivity(intent);
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_instructions);
//
//
//        //get food name from activity_Main.layout when clicked.
//        String foodName = getIntent().getStringExtra("foodName");
//        int id = Integer.parseInt(getIntent().getStringExtra("id"));
//
//        linearListInstruction = findViewById(R.id.linearLayoutInstruction);
//
//        tvInstructionName = findViewById(R.id.tv_instruction_name);
//        tvInstructionName.setText(foodName);
//
//        recyclerInstruction = findViewById(R.id.rv_instructions_step);
//        loadingView = findViewById(R.id.layout_loading);
//
//        // Khởi tạo hàm gọi API
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.spoonacular.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        spoonacularAPI = retrofit.create(SpoonacularAPI.class);
//
//        showLoading();
//
//        callAPI(id);
//
//    }
//
//
////        btnRecipe = findViewById(R.id.recipe_button);
////        foodTextView = findViewById(R.id.food_name);
////        imageFood = findViewById(R.id.food_image);
//
//
//
//
////        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
////        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
//
//
////        foodTextView.setText(foodName);
////        imageFood.setImageBitmap(bitmap);
//
//
//
////        btnRecipe.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);
////
////
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////                byte[] byteArray = stream.toByteArray();
////                intent.putExtra("foodImage", byteArray);
////
////                startActivity(intent);
////            }
////        });
//
////    private void showMonAnList(List<Recipe> danhSachMonAn) {
////        foodList = new ArrayList<>();
////        foodList.addAll(danhSachMonAn);
////        System.out.println(foodList.size());
////    }
//
//    private void showLoading() {
//        linearListInstruction.setVisibility(View.GONE);
//        loadingView.setVisibility(View.VISIBLE);
//    }
//
//    private void hideLoading() {
//        linearListInstruction.setVisibility(View.VISIBLE);
//        loadingView.setVisibility(View.GONE);
//    }
////    public void callAPI(int  id){
////        String apiKey = "b4573860b7ab4fda8e3530bc4c807030";
////        Call<InstructionsResponse> call = spoonacularAPI.getInstructions(id,apiKey);
////        call.enqueue(new Callback<Instructions>() {
////            @Override
////            public void onResponse(Call<Instructions> call, Response<Instructions> response) {
////                if (response.isSuccessful()) {
////                    Instructions instructions = response.body();
////
////                    if (instructions != null ) {
////                        // Thực hiện các thao tác với danh sách recipe
////                        hideLoading();
////
////                        // ...
////                    } else {
////
////                        System.out.println("Không lấy được thông tin");
////                        // Xử lý trường hợp danh sách rỗng
////                    }
////
////                    recyclerInstruction.setHasFixedSize(true);
////                    recyclerInstruction.setLayoutManager(new LinearLayoutManager(InstructionFoodActivity.this,LinearLayoutManager.VERTICAL,false));
////                    InstructionsStepAdapter instructionsStepAdapter =new InstructionsStepAdapter(InstructionFoodActivity.this,instructions.steps);
////                    recyclerInstruction.setAdapter(instructionsStepAdapter);
////
//////                    Lấy thông tin món đầu tiên
//////                  Xử lý kết quả từ API tại đây
//////
////                }
////                else {
////                    // Xử lý lỗi từ API tại đây
////                    System.out.println(response.message());
////                }
////            }
////
////            @Override
////            public void onFailure(Call<Instructions> call, Throwable t) {
////                // Xử lý lỗi kết nối tại đây
////                System.out.println(t.getMessage());
////            }
////        });
////    }
//
//
//
//
//
//}
