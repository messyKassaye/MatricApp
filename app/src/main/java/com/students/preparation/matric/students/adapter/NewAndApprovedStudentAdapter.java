package com.students.preparation.matric.students.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.StudentsModel;
import java.util.ArrayList;

public class NewAndApprovedStudentAdapter extends RecyclerView.Adapter<NewAndApprovedStudentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StudentsModel> student;
    private String type;
    public NewAndApprovedStudentAdapter(Context context,
                                        ArrayList<StudentsModel> tutorialsArrayList,String type) {
        this.context = context;
        this.student = tutorialsArrayList;
        this.type =  type;
    }

    @NonNull
    @Override
    public NewAndApprovedStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_student_and_approved_student_layout,
                viewGroup, false);
        return new NewAndApprovedStudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewAndApprovedStudentAdapter.ViewHolder viewHolder, int i) {
        final StudentsModel studentsModel = student.get(i);
        viewHolder.studentAvatar.setText(String.valueOf(studentsModel.get_fullName().charAt(0)));
        viewHolder.fullName.setText(studentsModel.get_fullName());
        viewHolder.schoolName.setText(studentsModel.get_school());
        viewHolder.stream.setText(studentsModel.get_stream());
        viewHolder.payedVia.setText(studentsModel.get_bank());
        viewHolder.phoneNo.setText(studentsModel.get_mobileNumber());
        viewHolder.txNumber.setText(studentsModel.get_txRefNum());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to Approve " + studentsModel.get_fullName() + "?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Approve Student
                       boolean result= registerStudentToDatabase(studentsModel);
                       if(result){
                           dialog.dismiss();
                           viewHolder.cancelLayout.setVisibility(View.VISIBLE);
                           viewHolder.approvalLayout.setVisibility(View.GONE);
                       }
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // removeRegStudent(uploadsModel);
                dialog.cancel();

            }
        });

        final AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(context);
        cancelBuilder.setCancelable(true);
        cancelBuilder.setTitle("Are You Sure?");
        cancelBuilder.setMessage("Do you want to remove " + studentsModel.get_fullName() + "?");
        cancelBuilder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Approve Student
                        removeRegStudent(studentsModel);
                        dialog.dismiss();
                        viewHolder.isApproved.setText("This student is removed!");
                        viewHolder.isApproved.setTextColor(Color.RED);
                        viewHolder.cancelLayout.setVisibility(View.VISIBLE);
                        viewHolder.approvalLayout.setVisibility(View.GONE);
                    }
                });

        cancelBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // removeRegStudent(uploadsModel);
                dialog.cancel();

            }
        });

        final AlertDialog dialog = builder.create();

        final AlertDialog canceDialog = cancelBuilder.create();

        viewHolder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceDialog.show();
            }
        });

        if(!type.equals("new")){
            viewHolder.approvalLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return student.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView studentAvatar;
        private final TextView fullName;
        private final TextView schoolName;
        private final TextView stream;
        private final TextView payedVia;
        private final TextView phoneNo;
        private final TextView txNumber;
        private final Button approve,remove;
        private RelativeLayout approvalLayout;
        private final LinearLayout cancelLayout;
        private TextView isApproved;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            studentAvatar = itemView.findViewById(R.id.studentAvatar);
            fullName = itemView.findViewById(R.id.studentName);
            schoolName = itemView.findViewById(R.id.schoolName);
            stream = itemView.findViewById(R.id.studentStream);
            payedVia = itemView.findViewById(R.id.payedVia);
            phoneNo = itemView.findViewById(R.id.studentPhone);
            txNumber = itemView.findViewById(R.id.txNumber);
            approve =itemView.findViewById(R.id.approved);
            cancelLayout = itemView.findViewById(R.id.cancelLayout);
            approvalLayout = itemView.findViewById(R.id.approvalLayout);
            remove = itemView.findViewById(R.id.removeStudent);
            isApproved = itemView.findViewById(R.id.isApproved);

        }
    }

    private boolean registerStudentToDatabase(final StudentsModel registrationModel) {

        DatabaseReference mDatabaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);
        mDatabaseReference.child(registrationModel.get_studentId()).setValue(registrationModel)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Approval Failed Please Try Again Latter", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeRegStudent(registrationModel);
                    }
                });
        return true;

    }

    private void removeRegStudent(StudentsModel uploadsModel) {
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS)
                .child(uploadsModel.get_studentId());

        mPostReference.getRef().removeValue();

    }
}

