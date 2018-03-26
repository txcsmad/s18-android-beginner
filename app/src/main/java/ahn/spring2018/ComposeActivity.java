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
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                pictureButton.setEnabled(true);
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
