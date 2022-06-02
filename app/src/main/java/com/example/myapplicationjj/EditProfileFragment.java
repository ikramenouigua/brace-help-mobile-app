package com.example.myapplicationjj;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout firstnameview, emailTxtView, weightview, phoneview, roomnumview, heightview, ageview;
    String email,phone,height,weight,age;
    private final String TAG = this.getClass().getName().toUpperCase();

    private static final String USERS = "patients";
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    Button btn, edit_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        emailTxtView = view.findViewById(R.id.email_edittext);
        phoneview = view.findViewById(R.id.phone_edittext);
        weightview = view.findViewById(R.id.weight_edittext);
        heightview = view.findViewById(R.id.height_edittext);
        ageview = view.findViewById(R.id.age_edittext);
        Button edit = view.findViewById(R.id.edits);
        // Read from the database
        String email = user.getEmail();
        fstore = FirebaseFirestore.getInstance();
        DocumentReference doc = fstore.collection("patients").document("SWNFKu2qwQEOZZ42WPER");
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    emailTxtView.getEditText().setText(documentSnapshot.get("email", String.class));

                    phoneview.getEditText().setText(documentSnapshot.get("tel", String.class));

                    heightview.getEditText().setText( documentSnapshot.get("poids", String.class));
                    weightview.getEditText().setText( documentSnapshot.get("taille", String.class));
                    ageview.getEditText().setText( documentSnapshot.get("age", String.class));


                }
            }
        });
        edit.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edits){
            email = emailTxtView.getEditText().getText().toString();
            phone = phoneview.getEditText().getText().toString();
            height = heightview.getEditText().getText().toString();
            weight = weightview.getEditText().getText().toString();
            age = ageview.getEditText().getText().toString();

            final DocumentReference docref = FirebaseFirestore.getInstance().collection("patients").document("SWNFKu2qwQEOZZ42WPER");
            Map<String,Object> map = new HashMap<>();
            map.put("email",email);
            map.put("tel",phone);
            map.put("poids",weight);
            map.put("taille",height);
            map.put("age",age);
            docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onsuccessLyay ,updated the doc");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG,"onfailure ",e);
                        }
                    });
        }
    }
}