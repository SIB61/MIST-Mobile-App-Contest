package com.sib.healthcare.Medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.sib.healthcare.R;

import java.util.ArrayList;
import java.util.HashMap;

public class medicine_main_activity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String, ArrayList<String>> listChild = new HashMap<>();
    MainAdapter adapter;

    View medicine_details_cancle;
    TextView name_medicine;
    TextView details_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);


        expandableListView = findViewById(R.id.exp_list_view);

        AddMedicineInfo();


        adapter = new MainAdapter(listGroup, listChild);
        expandableListView.setAdapter(adapter);

        adapter.setOnAdapterInteractionListener(new MainAdapter.onAdapterInteractionListener() {
            @Override
            public void onItemClick(int position, String s) {



                final Dialog dialog = new Dialog(medicine_main_activity.this);
                dialog.setContentView(R.layout.medicine_info_details);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);


                name_medicine = dialog.findViewById(R.id.medicine_info_medicine_name);
                details_info = dialog.findViewById(R.id.medicine_info_medicine_datails);

                name_medicine.setText(listGroup.get(position).toString());
                details_info.setText(s);

                medicine_details_cancle = dialog.findViewById(R.id.medicine_info_cancleBtn);

                medicine_details_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });



    }

    private void AddMedicineInfo() {

       listGroup.add("A2(Piperazines)");
       ArrayList<String> arrayList = new ArrayList<>();

       arrayList.add("How it looks, tastes and smells");
       arrayList.add("How do people take it?");
       arrayList.add("How it feels");
       arrayList.add("Duration");
       arrayList.add("The risks");
       arrayList.add("Mixing");
       arrayList.add("Addiction");
       arrayList.add("The law");
       listChild.put(listGroup.get(0), arrayList);


       listGroup.add("A2(Piperazines)");
       listChild.put(listGroup.get(1), arrayList);


       listGroup.add("Aerosols(Glues, gases and aerosols)");
       listChild.put(listGroup.get(2), arrayList);


       listGroup.add("Agaric(Magic mushrooms)");
       listChild.put(listGroup.get(3), arrayList);


       listGroup.add("Alcohol(Booze / Bevvy)");
       listChild.put(listGroup.get(4), arrayList);

       listGroup.add("Alpha-Methyltryptamine(AMT)");
       listChild.put(listGroup.get(5), arrayList);

       listGroup.add("Alprazolam(Benzodiazepines)");
       listChild.put(listGroup.get(6), arrayList);

       listGroup.add("Amani(Magic mushrooms)");
       listChild.put(listGroup.get(7), arrayList);

       listGroup.add("Amphetamine(Speed)");
       listChild.put(listGroup.get(8), arrayList);


       listGroup.add("Amphetamine Sulphate(Speed)");
       listChild.put(listGroup.get(9), arrayList);

       listGroup.add("Amsterdam Gold(Synthetic cannabinoids)");
       listChild.put(listGroup.get(10), arrayList);

       listGroup.add("AMT(Alpha-Methyltryptamine / Amt freebase / Indopan)");
       listChild.put(listGroup.get(11), arrayList);

       listGroup.add("Amt And 2-(1H-Indol-3-Yl)-1-Methyl-Ethylamine(AMT)");
       listChild.put(listGroup.get(12), arrayList);

       listGroup.add("Amt freebase(AMT)");
       listChild.put(listGroup.get(13), arrayList);


       listGroup.add("Amyls(Poppers)");
       listChild.put(listGroup.get(14), arrayList);

       listGroup.add("Anabolic steroids(Roids / Juice / Melanotan / Sildenafil +3 more)");
       listChild.put(listGroup.get(15), arrayList);

       listGroup.add(" Angel dust(PCP)");
       listChild.put(listGroup.get(16), arrayList);

       listGroup.add("Annihilation(Synthetic cannabinoids)");
       listChild.put(listGroup.get(17), arrayList);

       listGroup.add("Ayahuasca(DMT)");
       listChild.put(listGroup.get(18), arrayList);




    }
}