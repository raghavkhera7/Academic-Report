package com.intel.gjust.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.intel.gjust.MainActivity;
import com.intel.gjust.R;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.nio.file.Path;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentFragment extends Fragment {
    private SharedPref sharedPref;
    private static final int READ_REQUEST_CODE = 42;
    @BindView(R.id.choose_file)
    Button chooseFile;
    @BindView(R.id.file_name)
    TextView fileName;
    @BindView(R.id.file_upload)
    Button uploadBtn;
    private Uri file;
    public AssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        sharedPref = new SharedPref(getActivity(),getActivity().getSharedPreferences(Constants.PerfName, Context.MODE_PRIVATE));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_assignment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick({R.id.file_upload,R.id.choose_file})
    public void setChooseFile(View view){
        if (view.getId() == R.id.choose_file){
            performFileSearch();
        }
        else if(view.getId() == R.id.file_upload){
            if (file  == null){
                Toast.makeText(getContext(), "Please Choose File", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadFile();
        }
    }
    public void performFileSearch() {
        new ChooserDialog().with(getActivity())
                .withStartFile("/sdcard/")
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                       file = Uri.fromFile(pathFile);
                       setHomeView(pathFile.getName());

                        Toast.makeText(getContext(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
       /* // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be
        intent.setType("application/pdf");

        startActivityForResult(intent, READ_REQUEST_CODE);*/
    }
    private void setHomeView(String file){
      fileName.setText(file);

    }
    private void uploadFile(){
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("pdf/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getContext(), "Falied to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                System.out.println(downloadUrl);
                Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                Log.i("assignment", "Uri: " + uri.toString());
                file= uri;
            }
        }
    }

}
