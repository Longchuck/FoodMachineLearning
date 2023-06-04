package hcmute.edu.vn.foodmachinelearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.Instructions;
import hcmute.edu.vn.foodmachinelearning.model.Step;

public class InstructionsStepAdapter extends RecyclerView.Adapter<InstructionsStepViewHolder> {


    Context context;
    List<Step> list;

    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public InstructionsStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_instructions_step, parent, false);
        return new InstructionsStepViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsStepViewHolder holder, int position) {
        holder.tvNumber.setText(String.valueOf(list.get(position).number));
        holder.tvStepName.setText(list.get(position).step);

        holder.rvInstructionsIngredient.setHasFixedSize(true);
        holder.rvInstructionsIngredient.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsIngredientAdapter instructionsIngredientAdapter =new InstructionsIngredientAdapter(context,list.get(position).ingredients);
        holder.rvInstructionsIngredient.setAdapter(instructionsIngredientAdapter);

        holder.rvInstructionsEquipment.setHasFixedSize(true);
        holder.rvInstructionsEquipment.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter =new InstructionsEquipmentAdapter(context,list.get(position).equipment);
        holder.rvInstructionsEquipment.setAdapter(instructionsEquipmentAdapter);


    }




    @Override
    public int getItemCount() { 
        return list.size();
    }
}
class InstructionsStepViewHolder extends RecyclerView.ViewHolder{
    TextView tvStepName,tvEquipment,tvIngredient,tvNumber;

    RecyclerView rvInstructionsEquipment,rvInstructionsIngredient;

    public InstructionsStepViewHolder(@NonNull View itemView){
        super(itemView );
        tvStepName = itemView.findViewById(R.id.tv_step_name);
        tvEquipment=itemView.findViewById(R.id.tv_equipment);
        tvIngredient=itemView.findViewById(R.id.tv_ingredient);
        tvNumber= itemView.findViewById(R.id.tv_number);

        rvInstructionsEquipment = itemView.findViewById(R.id.rv_instructions_equipment);
        rvInstructionsIngredient = itemView.findViewById(R.id.rv_instructions_ingredient);
    }
}
