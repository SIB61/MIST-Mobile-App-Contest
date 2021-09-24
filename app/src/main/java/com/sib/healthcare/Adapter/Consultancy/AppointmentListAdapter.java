package com.sib.healthcare.Adapter.Consultancy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.R;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter {
    private  List<AppointmentModel> appointmentModels;
    private Context ctx;
    public AppointmentListAdapter(Context ctx,List<AppointmentModel> appointmentModels) {
      this.appointmentModels=appointmentModels;
      this.ctx=ctx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.appointment_list_item,parent,false)){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.textView_AL);
        String text= formatText(appointmentModels.get(position));
        textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return appointmentModels.size();
    }
    public String formatText(AppointmentModel appointmentModel){
        String t;
        t="Name : " + appointmentModel.getpName()+"\nAge : "+appointmentModel.getAge()
                +"    gender : "+appointmentModel.getGender()+"\nheight : "+appointmentModel.getHeight()
                +"    weight : "+appointmentModel.getWeight()+ "\nDescription : "+appointmentModel.getDescription();

        return t;
    }

}
