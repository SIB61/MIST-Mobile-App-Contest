package com.sib.healthcare.Medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sib.healthcare.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Or> {
    List<Order> list;
    Context c;
    OrderAdapter(Context c,List<Order>list)
    {
        this.c=c;
        this.list=list;
    }
    @NonNull
    @Override
    public Or onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prodducts, parent, false);
        return new Or(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Or holder, int i) {
        holder.date.setText(list.get(i).getDate());
        holder.phone.setText("Phone: "+list.get(i).getPhone());
        holder.location.setText("Delivery Address: "+list.get(i).getLocation());
        holder.pro.setText("Products: "+list.get(i).getPro());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Or extends RecyclerView.ViewHolder {
        TextView date,pro,phone,location;
        public Or(@NonNull View v) {
            super(v);
            date=v.findViewById(R.id.date);
            phone=v.findViewById(R.id.phone);
            location=v.findViewById(R.id.location);
            pro=v.findViewById(R.id.pro);
        }
    }
}
