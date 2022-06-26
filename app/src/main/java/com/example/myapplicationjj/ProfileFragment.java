package com.example.myapplicationjj;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;



public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView firstnameview, emailTxtView, weightview,phoneview ,roomnumview,heightview,ageview;
    private final String TAG = this.getClass().getName().toUpperCase();

    private static final String USERS = "patients";
    FirebaseAuth auth;
    FirebaseFirestore fstore ;
    Button btn,edit_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        edit_button= (Button)view.findViewById(R.id.edit_button);
        emailTxtView = view.findViewById(R.id.email);
        firstnameview = view.findViewById(R.id.firstname);

        phoneview = view.findViewById(R.id.phone);
        roomnumview = view.findViewById(R.id.room_number);
        weightview = view.findViewById(R.id.weight);
        heightview = view.findViewById(R.id.height);
        ageview = view.findViewById(R.id.age);
        // Read from the database
        String email = user.getEmail();
        fstore= FirebaseFirestore.getInstance();
        DocumentReference doc= fstore.collection("patients").document("SWNFKu2qwQEOZZ42WPER");
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    emailTxtView.setText(user.getEmail());
                    firstnameview.setText(documentSnapshot.get("prenom",String.class)+" "+documentSnapshot.get("nom",String.class));
                    phoneview.setText(documentSnapshot.get("tel",String.class));
                    roomnumview.setText("your room number "+documentSnapshot.get("numero_chambre",String.class));
                    weightview.setText("your height "+documentSnapshot.get("poids",String.class));
                    heightview.setText("your weight "+documentSnapshot.get("taille",String.class));
                    ageview.setText("your age "+documentSnapshot.get("age",String.class));




                }
            }
        });


        edit_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edit_button){
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, EditProfileFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
        }
    }

    private void finish() {
    }
}