package com.sib.healthcare.Medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridAdapter extends BaseAdapter {
    Context c;
    List<Med> list;
    LayoutInflater inflater;
    GridAdapter(Context c,List<Med>list)
    {
        this.c=c;
        this.list=list;
    }

    @Override
    public int getCount() {
      return  list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        if(inflater==null)
            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(v==null)
            v=inflater.inflate(R.layout.activity_med_adapter,null);
        CircleImageView ph;
        TextView name,price;
        CardView buy;
        ph=v.findViewById(R.id.ph);
        name=v.findViewById(R.id.name);
        price=v.findViewById(R.id.price);
        buy=v.findViewById(R.id.buy);
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getURL());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
         //   Toast.makeText(c, url,Toast.LENGTH_LONG).show();
            try{
                Glide.with(c).load(uri).into(ph);
            }
            catch (Exception e){

            }

        });

        name.setText(list.get(i).getMname());
        price.setText(list.get(i).getPrice()+"Tk.");
        return v;
    }
}
