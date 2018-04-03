package ahn.spring2018;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComposeActivity extends AppCompatActivity {
    private static final String USER_NAME="Ali";

    private ImageView imageView;
    private EditText editText;
    private Button uploadButton;
    private Button pictureButton;

    private Uri file;
    private String fileName;
    private String caption;
    private String timeStamp;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data");

    private View.OnClickListener takePicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = FileProvider.getUriForFile(ComposeActivity.this,
                    ComposeActivity.this.getApplicationContext().getPackageName() + ".fileprovider",
                    getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, 100);
        }
    };
    
    private View.OnClickListener uploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            caption = editText.getText().toString();
            
            if (!caption.equals("")) {
                timeStamp = new SimpleDateFormat("MM\\dd\\yy hh:mm:ss:ss a").format(new Date());

                Uri picture = file;

                StorageReference stgRef = storageReference.child(USER_NAME + "/images/" + fileName);
                stgRef.putFile(picture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getDownloadUrl().toString();

                        DatabaseReference dataRef = databaseReference.child(timeStamp);
                        dataRef.child("caption").setValue(caption);
                        dataRef.child("url").setValue(url);
                        dataRef.child("user").setValue(USER_NAME);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ComposeActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
                Toast.makeText(ComposeActivity.this, "Enter a caption", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        imageView = findViewById(R.id.upload_pic);
        editText = findViewById(R.id.caption_edit_text);
        uploadButton = findViewById(R.id.upload_button);
        pictureButton = findViewById(R.id.take_pic_button);

        uploadButton.setEnabled(false);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pictureButton.setEnabled(false);

            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        } else {
            pictureButton.setOnClickListener(takePicListener);
            uploadButton.setOnClickListener(uploadListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                pictureButton.setEnabled(true);
                pictureButton.setOnClickListener(takePicListener);
                uploadButton.setOnClickListener(uploadListener);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Picasso.with(this).load(file).rotate(90).into(imageView);
            uploadButton.setEnabled(true);
        }
    }

    private File getOutputMediaFile() {
        File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                return null;
            }
        }

        String picStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileName = "IMG_" + picStamp + ".jpg";
        return new File(storageDir.getPath() + File.separator + fileName);
    }
}
