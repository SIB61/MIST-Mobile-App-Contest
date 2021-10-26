package com.sib.healthcare.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.Medicine.AddMedicine;
import com.sib.healthcare.Medicine.Edit_Medicine;
import com.sib.healthcare.Medicine.Med;
import com.sib.healthcare.Medicine.ShowProduct;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.consultancy.EditProfileActivity;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MED> {
    List<Med> list;
    Context c;
    String wo;
  public  MedAdapter(Context c, List<Med>list, String wo)
    {
        this.c=c;
        this.list=list;
        this.wo=wo;
    }

    @NonNull
    @Override
    public MED onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_med_adapter, parent, false);
        return new MED(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MED holder, @SuppressLint("RecyclerView") int i) {
       // Toast.makeText(c,list.size()+"",Toast.LENGTH_LONG).show();
       //  Toast.makeText(c, list.get(i).getURL(),Toast.LENGTH_LONG).show();
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getURL());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
           //  Toast.makeText(c, list.get(i).getURL(),Toast.LENGTH_LONG).show();

            try {
                Glide.with(c.getApplicationContext()).load(uri).into(holder.ph);
            }
            catch (Exception e){

            }

        });
        holder.name.setText(list.get(i).getMname());
        holder.price.setText(list.get(i).getPrice()+"Tk.");
        if(wo.equals("Ow")){
      holder.ed.setVisibility(View.VISIBLE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c, Edit_Medicine.class).putExtra("Ran",list.get(i).getRan()));
            }
        });
        SessionManager sh = new SessionManager(c, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String email1="";
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        String finalEmail = email1;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ProgressDialog progressDialog;
                 AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(c);

                builder = new AlertDialog.Builder(c);

                builder.setMessage("Are you sure you want to delete??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseDatabase.getInstance().getReference("MedicineOwners").child(finalEmail).child("Medicines").child(list.get(i).getDisease()).child(list.get(i).getRan()).removeValue();
                                FirebaseDatabase.getInstance().getReference("Medicines").child(list.get(i).getDisease()).child(list.get(i).getRan()).removeValue();
                                FirebaseDatabase.getInstance().getReference("AllMedicines").child(list.get(i).getRan()).removeValue();
                                FirebaseDatabase.getInstance().getReference("All").child(finalEmail).child(list.get(i).getRan()).removeValue();

                                list.remove(i);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete?");
                alert.show();
            }
        });
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!wo.equals("Ow")) {
                    c.startActivity(new Intent(c, ShowProduct.class).putExtra("Url", list.get(i).getURL()
                    ).putExtra("Name", list.get(i).getName()).putExtra("Mname", list.get(i).getMname()).
                            putExtra("Qua", list.get(i).getQua()).putExtra("Des", list.get(i).getDes()).
                            putExtra("Dis", list.get(i).getDisease()).putExtra("Ran", list.get(i).getRan()).putExtra("Price", list.get(i).getPrice()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MED extends RecyclerView.ViewHolder {
        CircleImageView ph;
        TextView name,price;
        CardView buy;
        LinearLayout ed;
        Button edit,delete;
        public MED(@NonNull View v) {
            super(v);
            ph=v.findViewById(R.id.ph);
            name=v.findViewById(R.id.name);
            price=v.findViewById(R.id.price);
            buy=v.findViewById(R.id.buy);
            ed=v.findViewById(R.id.ed);
            edit=v.findViewById(R.id.edit);
            delete=v.findViewById(R.id.delete);
        }
    }
}
