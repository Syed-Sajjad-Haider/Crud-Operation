package com.example.allcrudopperationperformeds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity
{

    Button add,select;
    EditText name,father,email,img;
    ImageView imageView;
    Uri file;
    Bitmap bitmap;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;


    public final int REQUEST_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        name = (EditText) findViewById(R.id.addname);
        father = (EditText) findViewById(R.id.addfather);
        email = (EditText) findViewById(R.id.addemail);
        img = (EditText) findViewById(R.id.addimg);
        imageView = (ImageView) findViewById(R.id.imageView);

        select = (Button) findViewById(R.id.select);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dexter.withActivity(AddData.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
                            {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please Select Image"),REQUEST_CODE);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });




        add = (Button) findViewById(R.id.addbtn);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference().child("Deatils").child(file.getLastPathSegment());
                storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> down = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String na,fa,em,i ;
                                na = name.getText().toString();
                                fa = father.getText().toString();
                                em = email.getText().toString();
                                i = img.getText().toString();

                                String t =task.getResult().toString();

                                Map<String,Object> map = new HashMap<>();
                                map.put("name",na);
                                map.put("fathername",fa);
                                map.put("email",em);
                                map.put("image",i);
                                map.put("systemimage",task.getResult().toString());



                                FirebaseDatabase.getInstance().getReference().child("Details").push().setValue(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddData.this,Data.class);

                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
//
                                        Toast.makeText(getApplicationContext(),"Data Not Inserted",Toast.LENGTH_SHORT).show();

//                        Log.w("msg", "Failed to read value.",err);
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Image Inserted",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });







            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            file=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(file);
                 bitmap= BitmapFactory.decodeStream(inputStream);
                 imageView.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}