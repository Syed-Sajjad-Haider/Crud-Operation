package com.example.allcrudopperationperformeds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewHolder> {

String data[];
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options,Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, final int position, @NonNull model model)
    {
//        final model model1 = da

//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,ClickedData.class);
//                intent.putExtra("name",getItem(position).getName());
////                Toast.makeText(myadapter.class,"Position "+getItem(position)+" Clicked",Toast.LENGTH_SHORT).show();
//                context.startActivity(intent);
//            }
//        });

        holder.name.setText(model.getName());
        holder.fathername.setText(model.getFathername());
        holder.email.setText(model.getEmail());
//        holder.image.setext(model.getName());
//        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);

        String imageUrl=null;
        imageUrl=model.getSystemimage();
        Picasso.get().load(imageUrl).into(holder.image);






        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogPlus dialog = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent)).setExpanded(true,500).create();
                View myview = dialog.getHolderView();
                EditText nam = myview.findViewById(R.id.newname);
                EditText fnam = myview.findViewById(R.id.newfather);
                EditText ema = myview.findViewById(R.id.newemail);
                EditText im = myview.findViewById(R.id.newimage);

                Button sub = myview.findViewById(R.id.upbutton);

                im.setText(model.getImage());
                nam.setText(model.getName());
                fnam.setText(model.getFathername());
                ema.setText(model.getEmail());
                dialog.show();


                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("name",nam.getText().toString());
                        hashMap.put("fathername",fnam.getText().toString());
                        hashMap.put("email",ema.getText().toString());
                        hashMap.put("image",im.getText().toString());
//                        System.out.println(hashMap);
//                        Log.d("values ","uploaded"+hashMap);


                        FirebaseDatabase.getInstance().getReference().child("Details").child(getRef(position).getKey())
                                .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(Data.clas,"Update",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.show();
                            }
                        });
                    }
                });






            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Box");
                builder.setMessage("Do You Want To Delete");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        FirebaseDatabase.getInstance().getReference().child("Details").child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        builder.show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewHolder(view);
    }


    class myviewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView image;
        TextView name,fathername,email;
        Button edit,delete;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            fathername = (TextView) itemView.findViewById(R.id.fathername);
            email = (TextView) itemView.findViewById(R.id.email);

            edit = (Button)itemView.findViewById(R.id.edit);
            delete = (Button)itemView.findViewById(R.id.delete);
            FloatingActionButton fb = (FloatingActionButton) itemView.findViewById(R.id.add);


        }
    }
}
