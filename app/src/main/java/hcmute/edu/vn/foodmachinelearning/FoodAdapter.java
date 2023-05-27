package hcmute.edu.vn.foodmachinelearning;

// FoodAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<String> foodList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String foodName);
    }

    public FoodAdapter(List<String> foodList, OnItemClickListener itemClickListener) {
        this.foodList = foodList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_food_list_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        String foodName = foodList.get(position);
        holder.textFoodName.setText(foodName);
        holder.score.setText(foodName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(foodName);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView textFoodName;
        public TextView score;

        public FoodViewHolder(View itemView) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.text_view_name);
            score = itemView.findViewById(R.id.text_view_score);
        }
    }
}
