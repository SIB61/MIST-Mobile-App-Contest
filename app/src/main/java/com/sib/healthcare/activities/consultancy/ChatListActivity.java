package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityChatListBinding;
import com.sib.healthcare.models.ChatModel;

import java.text.SimpleDateFormat;

public class ChatListActivity extends AppCompatActivity {
private ActivityChatListBinding binding ;
private String uId;
private Query query;
private FirestoreRecyclerOptions<ChatModel> options;
private FirestoreRecyclerAdapter<ChatModel,ChatListViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.materialToolbar4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        query= FirebaseFirestore.getInstance().collection("Users/"+ FirebaseAuth.getInstance().getUid()+"/ChatList");
        options= new FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query,ChatModel.class).build();
        adapter=new FirestoreRecyclerAdapter<ChatModel, ChatListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatListViewHolder holder, int position, @NonNull ChatModel model) {
                StorageReference st= FirebaseStorage.getInstance().getReference("ProfilePics/"+model.getfUid()+".jpg");
                st.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(ChatListActivity.this).load(uri).into(holder.i));
                holder.n.setText(model.getfName());
                @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat( "d MMM yyyy, h:mm a" ).format ( model.getTimestamp().toDate() );
                holder.t.setText(time);
                holder.m.setText(model.getMsg());
                holder.itemView.setOnClickListener(v -> { startActivity(new Intent(ChatListActivity.this,ChatActivity.class).putExtra("uId",model.getfUid()));});
            }

            @NonNull
            @Override
            public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ChatListViewHolder(LayoutInflater.from(ChatListActivity.this).inflate(R.layout.chat_list_item,parent,false));
            }
        };
        binding.chatList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.chatList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder{
        ImageView i ;
        TextView m,n,t;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            i=itemView.findViewById(R.id.image_cli);
            m=itemView.findViewById(R.id.lastMsg_cli);
            n=itemView.findViewById(R.id.name_cli);
            t=itemView.findViewById(R.id.time_cli);
        }
    }
}