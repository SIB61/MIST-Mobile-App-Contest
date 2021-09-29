package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityChatBinding;
import com.sib.healthcare.models.ChatModel;
import com.sib.healthcare.models.UserDataModel;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
private ActivityChatBinding binding;
private FirestoreRecyclerOptions<ChatModel> options;
private Query query;
private String frUid,myUid,fName;
private UserDataModel userDataModel,myDataModel;
private CollectionReference cm,cf;
private DocumentReference df,dm;
private FirestoreRecyclerAdapter<ChatModel,RecyclerView.ViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        frUid=getIntent().getStringExtra("uId");
        myUid=FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().document("Users/"+frUid).get().addOnSuccessListener(documentSnapshot -> {
                   if(documentSnapshot.exists())
                   {
                      userDataModel = documentSnapshot.toObject(UserDataModel.class);
                       FirebaseStorage.getInstance().getReference(Objects.requireNonNull(userDataModel).getImage()).getDownloadUrl().addOnSuccessListener(uri -> {
                           Glide.with(this).load(uri).into(binding.imageCA);
                       });
                       binding.nameCA.setText(userDataModel.getName());
                       binding.typeCA.setText(userDataModel.getType());
                   }
                });
        FirebaseFirestore.getInstance().document("Users/"+myUid).get().addOnSuccessListener(documentSnapshot ->myDataModel=documentSnapshot.toObject(UserDataModel.class) );
        dm=FirebaseFirestore.getInstance().document("Users/"+myUid+"/ChatList/"+frUid);
        cm=dm.collection("Chats");
        df=FirebaseFirestore.getInstance().document("Users/"+frUid+"/ChatList/"+myUid);
        cf=df.collection("Chats");
        query=cm.orderBy("timestamp", Query.Direction.DESCENDING);
        options=new FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query,ChatModel.class).build();
        adapter=new FirestoreRecyclerAdapter<ChatModel, RecyclerView.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ChatModel model) {
                if(model.getType()==ChatModel.MY_MSG)
                   {
                       MyViewHolder holder1= (MyViewHolder) holder;
                       holder1.m.setText(model.getMsg());
                       @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat( "d MMM yyyy, h:mm a" ).format ( model.getTimestamp().toDate() );
                       holder1.t.setText(time);
                   }
                   else
                   {
                       FriendViewHolder holder1 = (FriendViewHolder) holder;
                       holder1.m.setText(model.getMsg());
                       @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat( "d MMM yyyy, h:mm a" ).format ( model.getTimestamp().toDate() );
                       holder1.t.setText(time);
                   }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return viewType==1? new MyViewHolder(LayoutInflater.from(ChatActivity.this).inflate(R.layout.my_chat,parent,false)) : new FriendViewHolder(LayoutInflater.from(ChatActivity.this).inflate(R.layout.frnd_chat,parent,false));
            }

            @Override
            public int getItemViewType(int position) {
                return getItem(position).getType();
            }
        };
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
        adapter.startListening();
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

    public void back(View view) {
        finish();
    }

    public void send(View view) {
        String msg = binding.chatBox.getText().toString().trim();
        if(!msg.isEmpty()){
            Timestamp timestamp = Timestamp.now();
            ChatModel chatModel = new ChatModel(msg,ChatModel.MY_MSG,timestamp,frUid);
            chatModel.setfName(userDataModel.getName());
            ChatModel chatModel1 = new ChatModel(msg,ChatModel.FRIEND_MSG,timestamp,myUid);
            chatModel1.setfName(myDataModel.getName());
            cm.add(chatModel).addOnSuccessListener( documentReference -> cf.add(chatModel1));
            dm.set(chatModel).addOnSuccessListener(unused -> df.set(chatModel1));
            binding.chatBox.setText("");
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView m,t;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            m=itemView.findViewById(R.id.my_msg);
            t=itemView.findViewById(R.id.my_time);
        }
    }
    class FriendViewHolder extends RecyclerView.ViewHolder{
        TextView m,t;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            m=itemView.findViewById(R.id.frnd_msg);
            t=itemView.findViewById(R.id.frnd_time);
        }
    }


}