package hcmute.edu.vn.foodmachinelearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InformationFoodActivity extends AppCompatActivity {


    Button btnRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_food);

        btnRecipe = findViewById(R.id.recipe_button);
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InformationFoodActivity.this, RecipeFoodActivity.class));
            }
        });
    }
}
