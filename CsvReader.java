package com.example.csvmove;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    List<ListData> objects = new ArrayList<ListData>();
    String fileName = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;


    public void reader(Context context, String selectedText) {

        AssetManager assetManager = context.getResources().getAssets();
        try {
            if (selectedText.equals("福地家宏")){
                fileName = "data_fukuchi.csv";
            } else if (selectedText.equals("石神国子")){
                fileName = "data_ishigami.csv";
            } else if (selectedText.equals("松井貴博")){
                fileName = "data_matsui.csv";
            } else if (selectedText.equals("表もこみち")){
                fileName = "data_omote.csv";
            } else if (selectedText.equals("椎名裕美子")){
                fileName = "data_shiina.csv";
            } else if (selectedText.equals("杉本高文")){
                fileName = "data_sugimoto.csv";
            } else if (selectedText.equals("菅生大将")){
                fileName = "data_sugou.csv";
            } else if (selectedText.equals("駿河学")){
                fileName = "data_suruga.csv";
            } else if (selectedText.equals("竹村桐子")){
                fileName = "data_takemura.csv";
            } else if (selectedText.equals("寺西修")){
                fileName = "data_teranishi.csv";
            } else if (selectedText.equals("アップロード")){
                this.fileUploader();
            } else if (selectedText.equals("ダウンロード")){
                this.fileDownloader();
            }
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open(String.valueOf(fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {

                //カンマ区切りで１つづつ配列に入れる
                ListData data = new ListData();
                String[] RowData = line.split(",");

                //CSVの左([0]番目)から順番にセット
                data.setDate(RowData[0]);
                data.setName(RowData[1]);
                data.setStarttime(RowData[2]);
                data.setClosingtime(RowData[3]);
                data.setGetpoint(RowData[4]);

                objects.add(data);
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileUploader () {
        //アップロード処理
// Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("data_fukuchi.csv");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("assets/data_fukuchi.csv");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        Uri file = Uri.fromFile(new File("path/to/images/data_fukuchi.csv"));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public void fileDownloader() {
        //Toast.makeText(this, "ダウンロード開始", Toast.LENGTH_LONG).show();
    }

}