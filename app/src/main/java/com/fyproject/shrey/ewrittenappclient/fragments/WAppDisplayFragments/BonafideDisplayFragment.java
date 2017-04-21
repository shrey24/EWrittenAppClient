package com.fyproject.shrey.ewrittenappclient.fragments.WAppDisplayFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyproject.shrey.ewrittenappclient.R;
import com.fyproject.shrey.ewrittenappclient.activity.ViewApplicaion;
import com.fyproject.shrey.ewrittenappclient.model.WAppBonafide;
import com.fyproject.shrey.ewrittenappclient.model.WAppLeave;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class BonafideDisplayFragment extends Fragment {


    private TextView tvToName;
    private TextView tvFromName;
    private TextView tvFromInfo;
    private TextView tvMessage;
    private TextView tvStatus;
    private Button btnFile;
    private Button btnAccept; //Faculty
    private Button btnReject; //Faculty

    private DatabaseReference fbRoot;

    private WAppBonafide bonafideApp;

    public String STUDENT;
    public String FACULTY;
    private String CurrentUserID;
    final String TAG="TAG";

    private void initialization(View v) {
        tvToName= (TextView) v.findViewById(R.id.tvToName);
        tvFromName= (TextView) v.findViewById(R.id.tvFromName);
        tvFromInfo= (TextView) v.findViewById(R.id.tvFromInfo);
        tvMessage= (TextView) v.findViewById(R.id.tvMessage);
        tvStatus= (TextView) v.findViewById(R.id.tvStatus);
        btnFile= (Button) v.findViewById(R.id.btnFile);
        btnAccept= (Button) v.findViewById(R.id.btnAccept);
        btnReject= (Button) v.findViewById(R.id.btnReject);
        STUDENT = getString(R.string.student);
        FACULTY = getString(R.string.faculty);
        bonafideApp = (WAppBonafide) ViewApplicaion.info;

        FirebaseDatabase.getInstance().getReference();

        //check user type and set UI accordingly
        if( ViewApplicaion.USERTYPE.equals(STUDENT) ){
            setUpStudentGUI(v);
        } else if( ViewApplicaion.USERTYPE.equals(FACULTY) ){
            setUpFacultyGUI();
        }


    }

    private void setUpStudentGUI(View v){
        btnAccept.setVisibility(View.GONE);
        btnReject.setVisibility(View.GONE);

    }

    private void setUpFacultyGUI(){
        tvToName.setVisibility(View.GONE);
    }

    public BonafideDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display_leave, container, false);
        initialization(view);


        if( ViewApplicaion.USERTYPE.equals(STUDENT)) {
            //STUDENT Display wApp code

            tvToName.append(bonafideApp.toName);
            tvFromName.setText(bonafideApp.fromName);
            tvFromInfo.setText(bonafideApp.classInfo);
            tvMessage.setText(bonafideApp.message);
            tvStatus.setText(bonafideApp.status.toUpperCase());


        }else if( ViewApplicaion.USERTYPE.equals(FACULTY) ) {
            //FACULTY Display wApp code



            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateStatus("accepted");
                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateStatus("rejected");
                }
            });

        }

        return view;
    }

    public void UpdateStatus(String status){
        String wAppPath1="/applicationsNode/"+bonafideApp.toUid+"/"+bonafideApp.getwAppId();
        String wAppPath2="/applicationsNode/"+bonafideApp.fromUid+"/"+bonafideApp.getwAppId();

        Map<String, Object> updateStatus = new HashMap<String, Object>();

        updateStatus.put(wAppPath1+"/status/",status);
        updateStatus.put(wAppPath2+"/status/",status);

        fbRoot.updateChildren(updateStatus, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference databaseReference) {
                if (error == null){ //Success
                    Toast.makeText(getContext(), "application sent!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    return;
                }
                Log.d(TAG, "updateStatus: ERRoR: " + error); //write failure
                Toast.makeText(getContext(), "failed to update status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "BonafideDisplayFragment onStop: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "BonafideDisplayFragment onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, " BonafideDisplayFragment onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BonafideDisplayFragment onDestroy: ");
    }}
