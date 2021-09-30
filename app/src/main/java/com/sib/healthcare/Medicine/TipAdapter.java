package com.sib.healthcare.Medicine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sib.healthcare.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.Tips> {
    Context c;
    int img[];
    String na[];
    public TipAdapter(Context c,int img[],String na[])
    {
        this.c=c;
        this.img=img;
        this.na=na;
    }

    @NonNull
    @Override
    public Tips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tip, parent, false);
        return new Tips(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tips holder, @SuppressLint("RecyclerView") int i) {
        holder.ph.setImageResource(img[i]);
        holder.he.setText(na[i]);
        holder.tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c, TrShow.class).putExtra("Re", na[i]));

            }
        });





    }

    @Override
    public int getItemCount() {
        return na == null ? 0 : na.length-1;
    }

    public class Tips extends RecyclerView.ViewHolder {
        CircleImageView ph;
        TextView he;
        CardView tr;
        public Tips(@NonNull View v) {
            super(v);
            ph=v.findViewById(R.id.ph);
            he=v.findViewById(R.id.he);
            tr=v.findViewById(R.id.tr);
        }
    }
}
