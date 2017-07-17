package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import static com.example.dell.myapplication.Constants.EIGHT_COLUMN;
import static com.example.dell.myapplication.Constants.ELEVEN_COLUMN;
import static com.example.dell.myapplication.Constants.FIRST_COLUMN;
import static com.example.dell.myapplication.Constants.FIRTH_COLUMN;
import static com.example.dell.myapplication.Constants.FOURTH_COLUMN;
import static com.example.dell.myapplication.Constants.NINE_COLUMN;
import static com.example.dell.myapplication.Constants.SECOND_COLUMN;
import static com.example.dell.myapplication.Constants.SEVEN_COLUMN;
import static com.example.dell.myapplication.Constants.SIX_COLUMN;
import static com.example.dell.myapplication.Constants.TEN_COLUMN;
import static com.example.dell.myapplication.Constants.THIRD_COLUMN;
import static com.example.dell.myapplication.Constants.TWELVE_COLUMN;

public class Landlord extends AppCompatActivity  implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {


    //====================================================================================
    //DECLARATIONS OF VARIABLES

    // File url to download


    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    private  int img_counter;



    public EditText ET_hfor, ET_price, ET_province, ET_town, ET_area, ET_description, ET_phone, ET_email, ET_cname, ET_houseNum;
    public String hfor, hprice, province, town, area, cphone, cemail, description, cname, housNum  ,role     ,myPhon,myNam,myEmai;
    public ListPopupWindow H_F_LPW, PROVINCE_LPW,MyH_LPW;
    public TextView userPAGETV, L_LogoutTV, L_loginTV
            , et_ImageName;

    //we are storing information about a house in an array list
    private ArrayList<HashMap<String, String>> houseList;
    private ArrayList<HashMap<String, String>>  MYHOUSEimages_array;
    private ArrayList<String> images_array;

    private String KEY_OWNER = "email";
    private String KEY_HOUSENUMBER = "houseNum";
    private final String KEY_IMAGE = "image";
    private final String KEY_NAME = "name";
    private final int PICK_IMAGE_REQUEST = 1;
    private String image_email= "guest@hfr.com";
    private static String   timeStamp  = "";

    private String UPLOADHOUSE_URL = "http://sopange.16mb.com/hon/postHouse.php";
    private String MYHOUSE_URL = "http://sopange.16mb.com/hon/myHouse.php";
    private String UPLOAD_URL ="http://sopange.16mb.com/hon/uploadPhoto.php";
    private String  MYHOUSE_PICTURES_URL ="http://sopange.16mb.com/hon/myPictures.php";
    private static String file_url = "http://sopange.16mb.com/hon/PhotoUploadWithText/24.png";
    private String  MY_SAVED_HOUSE_PICTURES_URL ="http://sopange.16mb.com/hon/minePics.php";
    // private String UPLOAD_URL ="http://sopange.16mb.com/uploadPhoto.php";


    private NetworkImageView imageView;
    private Bitmap bitmap;



    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri; // file url to store image/video
    private    Uri filePath;




    /*========================================================================================================
         LOGIC FOR THINGS THAT HAPPENS ON UPON CREATION OF THE ACTIVITY
      ========================================================================================================*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord);

        ET_hfor = (EditText) findViewById(R.id.hfortv);
        ET_price = (EditText) findViewById(R.id.pricetv);
        ET_province = (EditText) findViewById(R.id.provincetv);
        ET_town = (EditText) findViewById(R.id.towntv);
        ET_area = (EditText) findViewById(R.id.areatv);
        ET_description = (EditText) findViewById(R.id.descriptiontv);
        ET_phone = (EditText) findViewById(R.id.cphonetv);
        ET_email = (EditText) findViewById(R.id.emailtv);
        ET_cname = (EditText) findViewById(R.id.cnametv);
        ET_houseNum = (EditText) findViewById(R.id.houseNumbertv);


        et_ImageName = (EditText) findViewById(R.id.editTextImageName);
        imageView = (NetworkImageView) findViewById(R.id.imageView);

        images_array = new ArrayList<String>();   //holds the list of urls downloaded from MyPICTURES.php
        MYHOUSEimages_array = new  ArrayList<HashMap<String, String>>();
        houseList=new ArrayList<HashMap<String,String>>();   //this holds the list of houses downloaded from My HOUSES.php

        //Galler_images_array = new ArrayList<String>();

        timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss",
                Locale.getDefault()).format(new Date());
        img_counter = 0;


        //getting details about the user
        myPhon= Login.getPhone();
        myNam =Login.getName();
        myEmai =Login.getEmail();
        role = Login.getRole();

        //setting welcome name
        userPAGETV  = (TextView) findViewById(R.id. userPageNametv);
        userPAGETV.setText(myNam);

        //what kind of a user is this
        if (myNam.equalsIgnoreCase("Guest"))
        {
            L_LogoutTV  = (TextView) findViewById(R.id. L_logouttv);
            L_LogoutTV.setText("");
            L_LogoutTV.setEnabled(false);
            L_loginTV  = (TextView) findViewById(R.id. L_logintv);
            L_loginTV.setText("Login");
        }else{


            if (role.equalsIgnoreCase("tenant") )
            {
                L_LogoutTV  = (TextView) findViewById(R.id. L_logouttv);
                L_LogoutTV.setText("");
                L_LogoutTV.setEnabled(false);
                L_loginTV  = (TextView) findViewById(R.id. L_logintv);
                L_loginTV.setText("Login");
                userPAGETV.setText("Guest");
            }else{
                new MyHouse().execute();

                L_LogoutTV  = (TextView) findViewById(R.id. L_logouttv);
                L_LogoutTV.setText("Logout");
                L_loginTV  = (TextView) findViewById(R.id. L_logintv);
                L_loginTV.setText("");
                L_loginTV.setEnabled(false);

                ET_phone.setText(myPhon);
                ET_email.setText( myEmai);
                ET_cname .setText(myNam);
                image_email = myEmai;
            }
        }


        ET_hfor.setOnTouchListener(this);
        ET_province.setOnTouchListener(this);

        Constants.addArrays();

        //the list
        H_F_LPW = new ListPopupWindow(getApplicationContext());
        H_F_LPW .setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.hforlist));



        //the list
        PROVINCE_LPW= new ListPopupWindow(getApplicationContext());
        PROVINCE_LPW.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.provincelist));

        H_F_LPW.setAnchorView(ET_hfor);
        H_F_LPW.setModal(true);

        PROVINCE_LPW.setAnchorView(ET_province);
        PROVINCE_LPW.setModal(true);

        H_F_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toitem = parent.getItemAtPosition(position).toString();
                ET_hfor.setText(Toitem);
                H_F_LPW.dismiss();
            }
        });
        PROVINCE_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Fromitem = parent.getItemAtPosition(position).toString();
                ET_province.setText(Fromitem);
                PROVINCE_LPW.dismiss();
            }
        });










        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }



    }//END ON CREATE=================================================================




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }//==========================================================












    /*===============================================================================================
    * METHOD FOR LOADING  AN IMAGE FOR THE USER TO SEE
    * ===============================================================================================*/

    private void loadImage(String pic_url){

        //   Toast.makeText(this, "Ple "  + images_array.size(), Toast.LENGTH_LONG).show();

        String url = pic_url;
        if(url.equals("")){
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_LONG).show();
            return;
        }

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                android.R.drawable.ic_menu_camera, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(url, imageLoader);
    }//=============================================================================================




/*========================================================================================================
                   LOGIC FOR THE IMAGE GALLERY
 ========================================================================================================*/

    //IMAGE GALLERY ADAPTER  - called by the initialize gallery method
    @SuppressWarnings("deprecation")
    class ImageAdapter extends BaseAdapter {
        private final Context context;
        private final int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return  images_array.size();
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            NetworkImageView imageView = new NetworkImageView(getApplicationContext());
            //method to set a picture on the IMAGE View - even before we make a click
            if (position == 0){
                loadImage(images_array.get(0));
            }else{}
            //
            String url=images_array.get(position);

            imageView.setLayoutParams(new Gallery.LayoutParams(120, 120));
            imageView.setBackgroundResource(itemBackground);


            ImageLoader imageLoader = CustomVolleyRequest.getInstance(getApplicationContext())
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageView,
                    android.R.drawable.ic_menu_camera, android.R.drawable
                            .ic_dialog_alert));
            imageView.setImageUrl(url, imageLoader);


            return imageView;
        }

    }//end  image Adapter innerclass


    //Receive control from the image_URL_Downloader
    private void initGAllaey(){
        // Note that Gallery view is deprecated in Android 4.1---
        //noinspection deprecation
        @SuppressWarnings("deprecation")
        Gallery gallery = (Gallery) findViewById(R.id.landlord_image_gallery);
        //make an instance of the Image Adapter class
        gallery.setAdapter(new ImageAdapter(getApplication()));
        //when a tab has been clicked on the image gallery- what happens
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();

                //method call to load an image to the IMAGE VIEW
                loadImage(images_array.get(position));

            }
        });

    }//end Galley================================================================================





/*=================================================================================================================
*                       DOWNLOADING THE IMAGE URL'S FROM THE DATABASE
*   ===============================================================================================================*/




//DOWNLOADING A STRING OF URLS of the images

    class image_URL_Downloader extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return getHousePictures();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s!= null){

                try {
                    //putting the data in a jason array
                    JSONObject parentObject = new JSONObject(s);
                    JSONArray NEWbusDetail = parentObject.getJSONArray("result");

                    images_array.clear();
                    //loop through the array
                    for (int i = 0 ; i< NEWbusDetail.length(); i++){
                        JSONObject jasonobject = NEWbusDetail.getJSONObject(i);
                        //only downloading pic_url from the json file
                        String pic_url = jasonobject.getString("pic_url");
                        //adding a list of picute urls into an array
                        images_array.add(pic_url);
                    }//end for loop

                    //MAKE CALL TO Galley - we want to see the pictures
                    initGAllaey();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }else{
                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Landlord.this);

                viewDetail.setIcon(android.R.drawable.ic_menu_help);
                viewDetail.setTitle(" Network Fail");
                viewDetail.setMessage("   oops!  No internet connection");
                viewDetail.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                //  Intent intent = new Intent(Landlord.this, RouteSelect.class);
                                // Landlord.this.startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                viewDetail.show();

            }

        }//end post

        public String getHousePictures(){
            InputStream is = null;
            String line = null;

            String HOUSE_NUMMBER  = timeStamp +"_by " +myNam;
            String email = myEmai;


            try {
                // String MYHOUSE_PICTURES_URL = "http://192.168.56.1/HON/myPictures.php";
                URL url = new URL(MYHOUSE_PICTURES_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data =  URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode( email, "UTF-8") + "&" +
                        URLEncoder.encode("housNum", "UTF-8") + "=" + URLEncoder.encode(HOUSE_NUMMBER, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                is = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuffer sb = new StringBuffer();

                //making sure buffered reader is not null

                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } else {
                    return null;
                }
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }
    }//END MY HOUSE

    // }//END UP_PIC()=========================================================================================


























    /*===================================================================================================
                             CHOOSING AN IMAGE
     ====================================================================================================
     */
    //(1)  - Control given after button click - later given to onActivityResult to open file chooser activity
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }//END==========

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);

                //(2)  - we get that image that has been selected by the user , then upload it to database
                uploadImage();
                et_ImageName .setText("");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // if the result is capturing Image
        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }//===============


    // this method allows to convert the selected image to base64- for easy upload
    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }//END==================================================================================================





/*===================================================================================================
                        UPLOADING AN IMAGE
 ====================================================================================================*/

    //uploading the selected image
    private void uploadImage(){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Landlord.this, s , Toast.LENGTH_LONG).show();

                        //(3)  - then call this method - to download the url of the image
                        new image_URL_Downloader().execute();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Landlord.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                //Getting Image Name
                String image_name = et_ImageName.getText().toString();
                //Getting Image House Number
                String HOUSE_NUMMBER   = timeStamp +"_by " +myNam;
                img_counter = img_counter + 1;
                String count = String.valueOf(img_counter);



                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, image_name);
                params.put("userEmail", image_email);
                params.put("houseNumber",    HOUSE_NUMMBER );
                params.put("count",    count );

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


    } //======================================================================================================================







































//DOWNLOADING A STRING OF URLS of the images

    class MyHouseimage_URL_Downloader extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return getMYHOUSEPictures();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s!= null){

                try {
                    //putting the data in a jason array
                    JSONObject parentObject = new JSONObject(s);
                    JSONArray NEWbusDetail = parentObject.getJSONArray("result");

                    MYHOUSEimages_array.clear();
                    //loop through the array
                    for (int i = 0 ; i< NEWbusDetail.length(); i++){
                        JSONObject jasonobject = NEWbusDetail.getJSONObject(i);
                        //only downloading pic_url from the json file
                        String pic_url = jasonobject.getString("pic_url");
                        //adding a list of picute urls into an array

                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put(FIRST_COLUMN, pic_url);
                        temp.put(SECOND_COLUMN, pic_url);


                        //add hashmap to arraylist
                        MYHOUSEimages_array.add(temp);

                    }//end for loop

                    //making a call to download information about the landlords saved houses
                    new MyHouse().execute();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }else{
                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Landlord.this);

                viewDetail.setIcon(android.R.drawable.ic_menu_help);
                viewDetail.setTitle(" Network Fail");
                viewDetail.setMessage("   oops!  No internet connection");
                viewDetail.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                //  Intent intent = new Intent(Landlord.this, RouteSelect.class);
                                // Landlord.this.startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                viewDetail.show();

            }

        }//end post

        public String getMYHOUSEPictures(){

            InputStream is = null;
            String line = null;


            try {
                // String MYHOUSE_PICTURES_URL = "http://192.168.56.1/HON/myPictures.php";
                URL url = new URL(MY_SAVED_HOUSE_PICTURES_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String data =
                        URLEncoder.encode("myemail", "UTF-8") + "=" + URLEncoder.encode(myEmai, "UTF-8") + "&" +
                        URLEncoder.encode("myphone", "UTF-8") + "=" + URLEncoder.encode(myPhon, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                is = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuffer sb = new StringBuffer();

                //making sure buffered reader is not null

                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } else {
                    return null;
                }
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }
    }//END MY HOUSE













    /*========================================================================================================
            LOGIC FOR GETTING A LIST OF MY HOUSES FROM THE DATABASE
         ========================================================================================================*/
    public  class MyHouse extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            String data=getHouse();
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s!= null){

                try {
                    //PUTING THE DATA IN JASON OBJECT
                    JSONObject parentObject = new JSONObject(s);
                    JSONArray NEWbusDetail = parentObject.getJSONArray("result");


                    houseList.clear();
                    //loop through the array
                    for (int i = 0 ; i< NEWbusDetail.length(); i++){
                        JSONObject jasonobject = NEWbusDetail.getJSONObject(i);

                        String house_for = jasonobject.getString("house_for");
                        String house_province = jasonobject.getString("house_province");
                        String house_town = jasonobject.getString("house_town");
                        String house_area = jasonobject.getString("house_area");
                        String description = jasonobject.getString("description");
                        String views = jasonobject.getString("views");
                        String owner = jasonobject.getString("owner");
                        String price = jasonobject.getString("price");
                        String email = jasonobject.getString("email");
                        String phone = jasonobject.getString("phone");
                        String status = jasonobject.getString("house_status");
                        String picture1 = jasonobject.getString("picture1");
                        String houseNum = jasonobject.getString("house_number");

                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put(FIRST_COLUMN, house_for);
                        temp.put(SECOND_COLUMN, house_province);
                        temp.put(THIRD_COLUMN, house_town);
                        temp.put(FOURTH_COLUMN, house_area);
                        temp.put(FIRTH_COLUMN, description);
                        temp.put(SIX_COLUMN, "K "+price);
                        temp.put(SEVEN_COLUMN, views);
                        temp.put(EIGHT_COLUMN, owner);
                        temp.put(NINE_COLUMN, email);
                        temp.put(TEN_COLUMN,phone );
                        temp.put(TWELVE_COLUMN,status );
                        temp.put(ELEVEN_COLUMN, houseNum);

                        //add hashmap to arraylist
                        houseList.add(temp);

                    }//end for

                    //THEN FORMAT FOT VIEWING
                    final Spinner spin = (Spinner)findViewById(R.id.spinner1);

                      /*
                    AdapterHouse adapter=new AdapterHouse(Landlord.this, houseList, MYHOUSEimages_array);
                    spin.setAdapter(adapter);
                    */

                    SimpleAdapter sAdap;
                    sAdap = new SimpleAdapter(Landlord.this, houseList, R.layout.tenant_search_list,
                            new String[] {FIRST_COLUMN, FOURTH_COLUMN,TWELVE_COLUMN , SIX_COLUMN, ELEVEN_COLUMN}, new int[] {R.id.hosfortv, R.id.locaTv, R.id.textType, R.id.pricetv, R.id.Tsl_datetv});


                    spin.setAdapter(sAdap);



                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Landlord.this);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> arg0, View selectedItemView,  // what happens when an item has been selected
                                                   int position, long id) {

                            String contact =  houseList.get(position).get(TEN_COLUMN).toString();
                            String housefor  =  houseList.get(position).get(FIRST_COLUMN).toString();
                            String area =  houseList.get(position).get(FOURTH_COLUMN).toString();
                            String town =  houseList.get(position).get(THIRD_COLUMN).toString();
                            String price =  houseList.get(position).get(SIX_COLUMN).toString();
                            String description  =  houseList.get(position).get(FIRTH_COLUMN).toString();
                            String status  =  houseList.get(position).get(TWELVE_COLUMN).toString();
                            String views =  houseList.get(position).get(SEVEN_COLUMN).toString();
                            String province =  houseList.get(position).get(SECOND_COLUMN).toString();


                            viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                            viewDetail.setTitle("House Details");
                            viewDetail.setMessage("House For    :   " + housefor + "\n"
                                    + "Price              :   " + price + "\n"
                                    + "Status           :   " + status + "\n"
                                    + "Province       :   " + province + "\n"
                                    + "Town             :   " + town + "\n"
                                    + "Area              :   " + area + "\n"
                                    + "Seen By        :   " + views + " people" + "\n"
                                    + "Contact        :   " + contact + "\n"
                                    + "Description  : " + description);
                            viewDetail.setPositiveButton("Active/Deactivate",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(getApplicationContext(),"Still under development",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                            viewDetail.setNegativeButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub

                                            dialog.dismiss();
                                        }
                                    });
                            viewDetail.setNeutralButton("Edit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(getApplicationContext(),"Still under development",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                            viewDetail.show();
                        }
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                            Toast.makeText(Landlord.this,
                                    "Your Selected : Nothing",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }else{
                Toast.makeText(getApplicationContext(), "not connected to internet", Toast.LENGTH_SHORT).show();
            }

        }//end post

        public String getHouse(){
            InputStream is = null;
            String line = null;

            try {
                URL url = new URL(MYHOUSE_URL );

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data =  URLEncoder.encode("myname", "UTF-8") + "=" + URLEncoder.encode(myNam, "UTF-8") + "&" +
                        URLEncoder.encode("myemail", "UTF-8") + "=" + URLEncoder.encode(myEmai, "UTF-8") + "&" +
                        URLEncoder.encode("myphone", "UTF-8") + "=" + URLEncoder.encode(myPhon, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                is = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuffer sb = new StringBuffer();

                //making sure buffered reader is not null

                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } else {
                    return null;
                }
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }
    }//END MY HOUSE
























    /*========================================================================================================
                 LOGIC FOR SAVING A HOUSE TO THE DATABASE
     ========================================================================================================*/
    private void postAhouse() {
        if ((!ET_phone.getText().toString().equals("")) && (!ET_hfor.getText().toString().equals("")) && (!ET_province.getText().toString().equals("")) && (!ET_town.getText().toString().equals(""))
                && (!ET_area.getText().toString().equals("")) && (!ET_price.getText().toString().equals("")) && (!ET_description.getText().toString().equals(""))
                && (!ET_email.getText().toString().equals("")) && (!ET_cname.getText().toString().equals(""))) {
            hfor = ET_hfor.getText().toString().toLowerCase();
            province = ET_province.getText().toString().toLowerCase();
            town = ET_town.getText().toString().toLowerCase();
            area = ET_area.getText().toString().toLowerCase();
            description = ET_description.getText().toString().toLowerCase();
            hprice = ET_price.getText().toString().toLowerCase();
            cemail = ET_email.getText().toString().toLowerCase();
            cphone = ET_phone.getText().toString().toLowerCase();
            cname = ET_cname.getText().toString().toLowerCase();
            housNum = ET_houseNum.getText().toString().toLowerCase();


            //make a call to the inner class to start execution
            startSaveHouse();
            images_array.clear();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please Fill in All The Details", Toast.LENGTH_SHORT).show();
        }
    }

    public void startSaveHouse() {

        //BEGIN INNER CLASS
        class SaveHouse extends AsyncTask<String, Void, String> {
            ProgressDialog loading  =  new ProgressDialog(Landlord.this);


            @Override
            protected String doInBackground(String... params) {
                String data = postHouse();
                return data;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Landlord.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s!= null){

                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "\n"+s+"\n", Toast.LENGTH_LONG).show();
                    new MyHouse().execute();
                    img_counter = 0;
                    et_ImageName.setText("Front Image");
                    images_array.clear();


                }else{
                    Toast.makeText(getApplicationContext(), "not connected  to internet", Toast.LENGTH_SHORT).show();
                }

            }

            //downnloading the data
            public String postHouse() {

                InputStream is = null;
                String line = null;
                StringBuffer sb = new StringBuffer();
                String HOUSE_NUMMBER  = timeStamp +"_by " +myNam;

                try {
                    URL url = new URL(UPLOADHOUSE_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    httpURLConnection.setInstanceFollowRedirects(false);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("hfor", "UTF-8") + "=" + URLEncoder.encode(hfor, "UTF-8") + "&" +
                            URLEncoder.encode("province", "UTF-8") + "=" + URLEncoder.encode(province, "UTF-8") + "&" +
                            URLEncoder.encode("town", "UTF-8") + "=" + URLEncoder.encode(town, "UTF-8") + "&" +
                            URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode(area, "UTF-8") + "&" +
                            URLEncoder.encode("houseNumber", "UTF-8") + "=" + URLEncoder.encode(HOUSE_NUMMBER , "UTF-8") + "&" +
                            URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(hprice, "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&" +
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(cemail, "UTF-8") + "&" +
                            URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(cphone, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    // HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    is = new BufferedInputStream(httpURLConnection.getInputStream());

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));



                    //making sure buffered reader is not null

                    if (br != null) {
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } else {
                        return null;
                    }
                    return sb.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }//end postHouse

        }//end inner class
        SaveHouse sh = new SaveHouse();
        sh.execute();
    }//end startSaveHouse






    /*========================================================================================================
                 LOGIC FOR THE POP UP WINDOWS
  ========================================================================================================*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {

            case R.id.hfortv:  //FROM

                int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        H_F_LPW.show();
                        return true;
                    }
                }

                break;

            case R.id.provincetv:

                int DRAWABLE_LEFT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        PROVINCE_LPW.show();
                        return true;
                    }
                }
                break;
        }

        return false;

    }





















/*=============================================================================================================================
*                 CAMERA CAPTURE
* ============================================================================================================================*/
    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }//==================

    /**
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }//==========================

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }//==================
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }//=============

    /**
     * Receiving activity result method will be called after closing the camera
     * */
    //   @Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    //   }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {

            imageView.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            //(2)  - we get that image that has been selected by the user , then upload it to database
            uploadImage();
            imageView.setImageBitmap(bitmap);
            et_ImageName .setText("");


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }








    /*========================================================================================================
        LOGIC FOR THE BUTTON CLICKS
     ========================================================================================================*/
    public void onLandClick(View v) {
        switch (v.getId()) {
            case R.id.clicked_imageView:
                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Landlord.this);
                viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                viewDetail.setTitle("Delete Picture");
                viewDetail.setMessage("Are you sure ?");
                viewDetail.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                //  Intent intent = new Intent(Landlord.this, RouteSelect.class);
                                // Landlord.this.startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                viewDetail.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                //   Intent intent = new Intent(Landlord.this, Request.class);
                                //  Landlord.this.startActivity(intent);
                                dialog.dismiss();
                            }
                        });


                viewDetail.show();
                break;

            case R.id.postHousebtn:

                //stop animation
                Button image = (Button)
                        findViewById(R.id.postHousebtn);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                image.clearAnimation();

                //check so as to only allow a login user to post a house
                if ( userPAGETV.getText().toString().equalsIgnoreCase("Guest") )
                {
                    Toast.makeText(Landlord.this,"Please LOGIN to continue",Toast.LENGTH_LONG).show();
                }else{

                    if ( img_counter == 0 ){
                        Toast.makeText(Landlord.this,"Please Add Pictures",Toast.LENGTH_LONG).show();
                    }else {
                        postAhouse();

                        ET_hfor.setText("");
                        ET_town.setText("");
                        ET_houseNum.setText("");
                        ET_price.setText("");
                        ET_province.setText("");
                        ET_area.setText("");
                        ET_description.setText("");

                        images_array.clear();
                        timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss",
                                Locale.getDefault()).format(new Date());
                        initGAllaey();
                    }

                }


                break;
            case R.id.L_logintv:
                Intent intent1 = new Intent(Landlord.this, Login.class);
                Landlord.this.startActivity(intent1);
                break;
            case R.id.L_logouttv:
                Intent intent2 = new Intent(Landlord.this,Login.class);
                Landlord.this.startActivity(intent2);
                break;

            case R.id.L_Hometv:
                Intent intent22 = new Intent(Landlord.this,MainActivity.class);
                Landlord.this.startActivity(intent22);
                break;

            case R.id.uploadbtn:

                blink();

                //check so as to only allow a login user to post a house
                if (userPAGETV.getText().toString().equalsIgnoreCase("Guest"))
                {
                    Toast.makeText(Landlord.this,"Please LOGIN to continue",Toast.LENGTH_LONG).show();
                }else{



                    if ((!ET_phone.getText().toString().equals("")) && (!ET_hfor.getText().toString().equals("")) && (!ET_province.getText().toString().equals("")) && (!ET_town.getText().toString().equals(""))
                            && (!ET_area.getText().toString().equals("")) && (!ET_price.getText().toString().equals("")) && (!ET_description.getText().toString().equals(""))
                            && (!ET_email.getText().toString().equals("")) && (!ET_cname.getText().toString().equals(""))  && (!et_ImageName.getText().toString().equals(""))    )  {
                        hfor = ET_hfor.getText().toString().toLowerCase();
                        province = ET_province.getText().toString().toLowerCase();
                        town = ET_town.getText().toString().toLowerCase();
                        area = ET_area.getText().toString().toLowerCase();
                        description = ET_description.getText().toString().toLowerCase();
                        hprice = ET_price.getText().toString().toLowerCase();
                        cemail = ET_email.getText().toString().toLowerCase();
                        cphone = ET_phone.getText().toString().toLowerCase();
                        cname = ET_cname.getText().toString().toLowerCase();
                        housNum = ET_houseNum.getText().toString().toLowerCase();


                     /*  3 things basically follow up
                * 1- allow user to select an image- file chooser to open
                * 2- after file chooser has executed, call uploadImage() to database
                * 3- call downloadImage() to download url of that image from the database- because we are displaying an image based on the url*/

                        showFileChooser();



                    } else if ( (et_ImageName.getText().toString().equals(""))  ){
                        Toast.makeText(getApplicationContext(),
                                "Image Name is Empty", Toast.LENGTH_SHORT).show();
                    }


                    else {
                        Toast.makeText(getApplicationContext(),
                                "Fill in All House Details First", Toast.LENGTH_SHORT).show();
                    }




                }// end if login check


                break;
            case R.id.camerabtn:
                blink();

                //check so as to only allow a login user to post a house
                if (userPAGETV.getText().toString().equalsIgnoreCase("Guest"))
                {
                    Toast.makeText(Landlord.this,"Please LOGIN to continue",Toast.LENGTH_LONG).show();

                }else{



                    if ((!ET_phone.getText().toString().equals("")) && (!ET_hfor.getText().toString().equals("")) && (!ET_province.getText().toString().equals("")) && (!ET_town.getText().toString().equals(""))
                            && (!ET_area.getText().toString().equals("")) && (!ET_price.getText().toString().equals("")) && (!ET_description.getText().toString().equals(""))
                            && (!ET_email.getText().toString().equals("")) && (!ET_cname.getText().toString().equals(""))  && (!et_ImageName.getText().toString().equals(""))    )  {
                        hfor = ET_hfor.getText().toString().toLowerCase();
                        province = ET_province.getText().toString().toLowerCase();
                        town = ET_town.getText().toString().toLowerCase();
                        area = ET_area.getText().toString().toLowerCase();
                        description = ET_description.getText().toString().toLowerCase();
                        hprice = ET_price.getText().toString().toLowerCase();
                        cemail = ET_email.getText().toString().toLowerCase();
                        cphone = ET_phone.getText().toString().toLowerCase();
                        cname = ET_cname.getText().toString().toLowerCase();
                        housNum = ET_houseNum.getText().toString().toLowerCase();


                    /*
                when camera is clicked */
                        captureImage();

                    } else if ( (et_ImageName.getText().toString().equals(""))  ){
                        Toast.makeText(getApplicationContext(),
                                "Image Name is Empty", Toast.LENGTH_SHORT).show();
                    }


                    else {
                        Toast.makeText(getApplicationContext(),
                                "Fill in All House Details First", Toast.LENGTH_SHORT).show();
                    }




                }// end if login check
                break;
            case R.id.mapbtn:

                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
                break;


            case R.id.landlordHometv:

                startActivity(new Intent(this, MainActivity.class));
                break;


        }//end switch


    }//END ON LANDLORD BUTTON CLICKS



    @Override
    public void onClick(View v) {

    }
    //animation
    public  void blink(){
        Button image = (Button)
                findViewById(R.id.postHousebtn);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move1);
        image.startAnimation(animation1);
    }







}//END MAIN CLASS //========================================================================================================



