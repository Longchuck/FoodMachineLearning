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

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder> {


    Context context;
    List<Instructions> list;

    public InstructionsAdapter(Context context, List<Instructions> instructionsList) {
        this.context = context;
        this.list = instructionsList;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_instructions, parent, false);
        return new InstructionsViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.tvInstructionName.setText(list.get(position).getName());

        holder.recyclerInstruction.setHasFixedSize(true);
        holder.recyclerInstruction.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        InstructionsStepAdapter instructionsStepAdapter =new InstructionsStepAdapter(context,list.get(position).steps);
        holder.recyclerInstruction.setAdapter(instructionsStepAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionsViewHolder extends RecyclerView.ViewHolder{
    TextView tvInstructionName;
    RecyclerView recyclerInstruction;

    public InstructionsViewHolder(@NonNull View itemView){
        super(itemView );
        tvInstructionName = itemView.findViewById(R.id.tv_instruction_name);
        recyclerInstruction = itemView.findViewById(R.id.rv_instructions_step);

    }
}
