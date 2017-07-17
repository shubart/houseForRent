package com.example.dell.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.dell.myapplication.Constants.FIRST_COLUMN;
import static com.example.dell.myapplication.Constants.FIRTH_COLUMN;
import static com.example.dell.myapplication.Constants.FOURTH_COLUMN;
import static com.example.dell.myapplication.Constants.SECOND_COLUMN;
import static com.example.dell.myapplication.Constants.THIRD_COLUMN;

public class ClickedHouse extends AppCompatActivity implements View.OnClickListener{

    //DECLARATIONS OF VARIABLES

    private NetworkImageView imageView;
    private ArrayList<String> images_array;
    public   static ArrayList<HashMap<String, String>> SelectedpictureList;

    private  int loop = 0;

    private Bitmap bitmap;
    private final int PICK_IMAGE_REQUEST = 1;

    private final String KEY_IMAGE = "image";
    private final String KEY_NAME = "name";
    private  String Maincall ;
    private  String Tenantcall ;
    private static int viewsNumber ;

    private String house_num;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri; // file url to store image/video
    private Uri filePath;

    public  int gallerySize = 0;


    /*===============================================================================================
  * On create method
  * ===============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_house);
        Maincall = "me";
        Tenantcall = "me";

        Maincall= MainActivity.getCall();
        Tenantcall = Tenant.getTCall();
        clockwise();


        TextView descriptiontxt = (TextView) findViewById(R.id.descriptiontxt);
        TextView hfor = (TextView) findViewById(R.id.housefortxt);
        TextView viewstxt = (TextView) findViewById(R.id.viewstxt);
        TextView Locationtxt = (TextView) findViewById(R.id.provincetxt);
        TextView areatxt = (TextView) findViewById(R.id.areatxt);
        TextView towntxt = (TextView) findViewById(R.id.TWNtxt);
        TextView pricetxt = (TextView) findViewById(R.id.pricetxt);
        TextView contactNametxt = (TextView) findViewById(R.id.cnametxt);
        TextView emailtxt = (TextView) findViewById(R.id.emailtxt);
        TextView phonetxt = (TextView) findViewById(R.id.cphonetxt);
        TextView postdatetxt = (TextView) findViewById(R.id.postdatetxt);




        if (Maincall.equalsIgnoreCase("Main")){
            house_num =  MainActivity.getHOUSE_NUMBER();

            String hforrrr = MainActivity.getHOSFOR();
            Toast.makeText(this,"\n"+ hforrrr +"\n", Toast.LENGTH_LONG).show();

            //number of views counter
            int vn = Integer.parseInt(MainActivity.getVIEWS()) ;
            viewsNumber = 1 + vn;

            descriptiontxt.setText(MainActivity.getDESCRIP());
            hfor.setText(MainActivity.getHOSFOR());
            viewstxt.setText(""+viewsNumber+ " views");
            areatxt.setText(MainActivity.getAREA());
            Locationtxt.setText(MainActivity.getPROV());
            towntxt.setText(MainActivity.getTOWN());
            pricetxt.setText("K "+MainActivity.getPRICE());
            contactNametxt.setText(MainActivity.getCONTACRT());
            emailtxt.setText(MainActivity.getEMAIL());
            phonetxt.setText(MainActivity.getNUMBER());
            postdatetxt.setText(house_num);

        }else if (Tenantcall.equalsIgnoreCase("Tenant")){


            house_num = Tenant.getHOUSE_NUMBER();

            //number of views counter
            int vn = Integer.parseInt(Tenant.getVIEWS()) ;
            viewsNumber = 1 + vn;


            descriptiontxt.setText(Tenant.getDESCRIP());
            hfor.setText(Tenant.getHOSFOR());
            viewstxt.setText(viewsNumber +" views");
            areatxt.setText(Tenant.getAREA());
            Locationtxt.setText(Tenant.getPROV());
            towntxt.setText(Tenant.getTOWN());
            pricetxt.setText("K "+Tenant.getPRICE());
            contactNametxt.setText(Tenant.getCONTACRT());
            emailtxt.setText(Tenant.getEMAIL());
            phonetxt.setText(Tenant.getNUMBER());
            postdatetxt.setText(house_num);





        }else{
            Toast.makeText(this, "he prisca lover", Toast.LENGTH_LONG).show();
        }





        images_array = new ArrayList<String>();
        //  SelectedpictureList= new  ArrayList<HashMap<String, String>>();


        new image_URL_Downloader_FOR_LIST().execute();

        //   images_array = new ArrayList<String>();
        imageView = (NetworkImageView) findViewById(R.id.clicked_imageView);

        //make a check for saved image urls for the first time-
        //  new image_URL_Downloader().execute();





    }// END ON CREATE=========================================================================





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

    //Receive control from the image_URL_Downloader
    private void initGAllaey(){
        // Note that Gallery view is deprecated in Android 4.1---
        //noinspection deprecation
        @SuppressWarnings("deprecation")
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
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

    @Override
    public void onClick(View v) {

    }


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
            NetworkImageView imageView = new   NetworkImageView(getApplicationContext());
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







//DOWNLOADING A STRING OF URLS of the images

    class image_URL_Downloader_FOR_LIST extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return getHousePictures_listView();
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
                    // images_array.clear();
                    //loop through the array
                    for (int i = 0 ; i< NEWbusDetail.length(); i++){
                        JSONObject jasonobject = NEWbusDetail.getJSONObject(i);
                        //only downloading pic_url from the json file
                        String pic_url = jasonobject.getString("pic_url");
                        //adding a list of picute urls into an array
                        //   images_array.add(pic_url);



                        String pic_urls = jasonobject.getString("pic_url");
                        String pic_name = jasonobject.getString("pic_name");


                        images_array.add(pic_urls);




                    }//end for loop

                    //MAKE CALL TO Galley - we want to see the pictures
                    initGAllaey();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }else{
                Toast.makeText(getApplicationContext(), "not conencted to internet", Toast.LENGTH_SHORT).show();
            }

        }//end post

        public String getHousePictures_listView(){
            InputStream is = null;
            String line = null;





            try {
                String _PICTURES_URL = "http://sopange.16mb.com/hon/selectedHouse.php";
                URL url = new URL(_PICTURES_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data =
                        URLEncoder.encode("house_num", "UTF-8") + "=" + URLEncoder.encode(house_num, "UTF-8")+ "&" +
                                //Upading the number of views
                                URLEncoder.encode("viewsNumber", "UTF-8") + "=" + URLEncoder.encode(""+viewsNumber, "UTF-8");

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
    }//END MY HOUSE   =========================================================================================











    public void onGreenTabClickHome(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void onGreenTabClickAbout(View v) {
        //startActivity(new Intent(MainActivity.this, About.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickContact(View v) {
        //startActivity(new Intent(MainActivity.this, Contact.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickMessages(View v) {
        //startActivity(new Intent(MainActivity.this, Messages.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }



    public void onClickHouse(View v) {
        switch (v.getId()) {
            case R.id.clickedHouseBackButton:
                startActivity(new Intent(ClickedHouse.this, MainActivity.class));
                break;

            case R.id.reservebutton:
                Toast.makeText(getBaseContext(), " Coming Soon" ,
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.mapbutton:
                Toast.makeText(getBaseContext(), " Coming Soon" ,
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.SelecctedH_Backtv:
              if (Maincall.equalsIgnoreCase("Main")){

                  startActivity(new Intent(ClickedHouse.this, MainActivity.class));

              }else  {
                  startActivity(new Intent(ClickedHouse.this, Tenant.class));
              }
                break;

        }
    }

    public void clockwise() {
        TextView txtView = (TextView)
                findViewById(R.id.ADverttv);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        txtView.startAnimation(animation1);

    }





}
