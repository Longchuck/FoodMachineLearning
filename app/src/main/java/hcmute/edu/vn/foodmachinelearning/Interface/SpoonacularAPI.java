package hcmute.edu.vn.foodmachinelearning.Interface;

import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpoonacularAPI {
    @GET("recipes/complexSearch")
    Call<complexSearch> getRecipeInformation(
//        @Path("id") int recipeId,
        @Query("apiKey") String apiKey,
        @Query("query") String query
    );
}
