package com.kaihongtan.kongsi;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaihongtan.kongsi.ui.home.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewListing extends AppCompatActivity {

    public static final String UPLOAD_URL = "http://kaihongtan.com/images/upload.php";
    public static final String UPLOAD_KEY = "uploadimage";

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private EditText name;
    private EditText location;
    private Button done;

    private ImageView imageView;
    private Bitmap bitmap;
    private Uri filePath;
    String namepost, locationpost,imagepath ;
    public static final String HttpUrl = "http://kaihongtan.com/insert_record.php";
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing);
        init();
    }
    void init(){
        buttonChoose = findViewById(R.id.btnChoose);
        buttonUpload = findViewById(R.id.btnUpload);
        done = findViewById(R.id.button3);
        name = findViewById(R.id.itemname);
        location = findViewById(R.id.location);


        imageView = findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == buttonChoose) {
                    showFileChooser();
                }
                if(v == buttonUpload){
                    if(filePath!=null) {
                        uploadImage();
                    } else {
                        Toast.makeText(NewListing.this,"Select Image",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == buttonChoose) {
                    showFileChooser();
                }
                if(v == buttonUpload){
                    if(filePath!=null) {
                        uploadImage();
                    } else {
                        Toast.makeText(NewListing.this,"Select Image",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        requestQueue = Volley.newRequestQueue(NewListing.this);

        progressDialog = new ProgressDialog(NewListing.this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(NewListing.this,"Fill in data!",Toast.LENGTH_LONG).show();
                }
                else if(location.getText().toString().isEmpty()){
                    Toast.makeText(NewListing.this,"Fill in data!",Toast.LENGTH_LONG).show();
                }
                else if(imageView.getDrawable()== null){
                    Toast.makeText(NewListing.this,"Upload Image!",Toast.LENGTH_LONG).show();
                }
                else{
                    // Showing progress dialog at user registration time.
                    progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                    progressDialog.show();

                    // Calling method to get value from EditText.
                    GetValueFromEditText();

                    // Creating string request with post method.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();

                                    // Showing response message coming from server.
                                    Toast.makeText(NewListing.this, ServerResponse, Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                    volleyError.printStackTrace();

                                    // Showing error message if something goes wrong.
                                    Toast.makeText(NewListing.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("name", namepost);
                            params.put("Location", locationpost);
                            params.put("image", "http://kaihongtan.com/images/upload/"+imagepath);

                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(NewListing.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                    finish();
                }
            }
        });
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewListing.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                imagepath = getFileName(filePath);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put(uploadImage,getFileName(filePath));

                String result = rh.postRequest(UPLOAD_URL,data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }


    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    public void GetValueFromEditText(){

        namepost = name.getText().toString().trim();
        locationpost = location.getText().toString().trim();
    }


}
