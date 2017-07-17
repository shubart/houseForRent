package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String address ="http://sopange.16mb.com/hon/listHouses.php";
    private static String call;




    ListView list;
    public   static ArrayList<HashMap<String, String>> ALLpictureList;
    private ArrayList<HashMap<String, String>> ALLHouseList;

    private  static String HOUSE_NUMBER,CONTACRT,PROV,PHONE,PRICE,TOWN,AREA,EMAIL,VIEWS,DESCRIP ,NUMBER,HOSFOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //call the rotate image anmation on the picture
        Zoom();

        list=(ListView)findViewById(R.id.list);

        ALLHouseList = new ArrayList<HashMap<String,String>>();
        ALLpictureList = new ArrayList<HashMap<String,String>>();////////


        new GenralSearchInitialDownload().execute();
        new image_URL_Downloader_FOR_LIST().execute();

        HOUSE_NUMBER = "guest"; PROV= "guest"; TOWN= "guest"; AREA = "guest"; PRICE = "guest";NUMBER = "No number";
        VIEWS = "guest";DESCRIP = "guest";EMAIL = "guest";PHONE = "guest"; ;HOSFOR = "guest";
        call = "quest";


    }


    public void setHOUSE_NUMBER(String HOUSE_NUMBER){
        this.HOUSE_NUMBER=HOUSE_NUMBER;
    }

    public static String getHOUSE_NUMBER(){
        return HOUSE_NUMBER;
    }

    public void setCONTACRT(String CONTACRT){
        this.CONTACRT=CONTACRT;
    }

    public static String getCONTACRT(){
        return CONTACRT;
    }
    public void setPROV(String PROV){
        this.PROV=PROV;
    }

    public static String getPROV(){
        return PROV;
    }
    public void setTOWN(String TOWN){
        this.TOWN=TOWN;
    }

    public static String getTOWN(){
        return TOWN;
    }
    public void setAREA(String AREA){
        this.AREA=AREA;
    }

    public static String getAREA(){
        return AREA;
    }

    public void setEMAIL(String EMAIL){
        this.EMAIL=EMAIL;
    }

    public static String getEMAIL(){
        return EMAIL;
    }
    public void setPHONE(String PHONE){
        this.PHONE=PHONE;
    }

    public static String PHONE(){
        return PHONE;
    }
    public void setPRICE(String PRICE){
        this.PRICE=PRICE;
    }

    public static String getPRICE(){
        return PRICE;
    }  public void setVIEWS(String VIEWS){
        this.VIEWS=VIEWS;
    }

    public static String getVIEWS(){
        return VIEWS;
    }  public void setDESCRIP(String DESCRIP){
        this.DESCRIP=DESCRIP;
    }

    public static String getDESCRIP(){
        return DESCRIP;
    }
    public void setHOSFOR(String HOSFOR){
        this.HOSFOR=HOSFOR;
    }

    public static String getHOSFOR(){
        return HOSFOR;
    }
    public void setNUMBER(String NUMBER){
        this.NUMBER=NUMBER;
    }

    public static String getNUMBER(){
        return NUMBER;
    }



    public void setCall(String call){
        this.call=call;
    }

    public static String getCall(){
        return call;
    }



/*=================================================================================================================
*                   ANIMATIONS Methodds
*   ===============================================================================================================*/


    public  void Zoom(){
        ImageView image = (ImageView)
                findViewById(R.id.imageView3);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
        image.startAnimation(animation1);
    }


    /*=================================================================================================================
/

/*=================================================================================================================
*                       DOWNLOADING THE IMAGE URL'S FROM THE DATABASE FOR THE LIST
*   ===============================================================================================================*/




//DOWNLOADING A STRING OF URLS of the images


    class image_URL_Downloader_FOR_LIST extends AsyncTask<String,Void,String> {
        // Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // checkInternetConenction();
        }

        /*
        //check Internet conenction.
        private void checkInternetConenction(){
            ConnectivityManager check = (ConnectivityManager) getApplicationContext(). getSystemService(Context.CONNECTIVITY_SERVICE);
            if (check != null) { NetworkInfo[] info = check.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i <info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            Toast.makeText(getApplicationContext(), "Internet is connected", Toast.LENGTH_SHORT).show();
                        } }
            else{
                Toast.makeText(getApplicationContext(), "not conencted to internet", Toast.LENGTH_SHORT).show(); }
        }

        */

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

                    ALLpictureList.clear();
                    // images_array.clear();
                    //loop through the array
                    for (int i = 0 ; i< NEWbusDetail.length(); i++){
                        JSONObject jasonobject = NEWbusDetail.getJSONObject(i);
                        //only downloading pic_url from the json file
                        String pic_url = jasonobject.getString("pic_url");
                        //adding a list of picute urls into an array
                        //   images_array.add(pic_url);


                        String pic_id = jasonobject.getString("pic_id");
                        String pic_urls = jasonobject.getString("pic_url");
                        String pic_name = jasonobject.getString("pic_name");
                        String pic_owner = jasonobject.getString("pic_owner");
                        String pic_house_num = jasonobject.getString("pic_house_num");




                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put(FIRST_COLUMN, pic_id);
                        temp.put(SECOND_COLUMN, pic_urls);
                        temp.put(THIRD_COLUMN, pic_name);
                        temp.put(FOURTH_COLUMN, pic_owner);
                        temp.put(FIRTH_COLUMN, pic_house_num);

                        //add hashmap to arraylist
                        ALLpictureList.add(temp);


                    }//end for loop

                    //MAKE CALL TO Galley - we want to see the pictures
                    // initGAllaey();



                    CustomListAdapter adapter=new CustomListAdapter(MainActivity.this, ALLHouseList, ALLpictureList);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            // TODO Auto-generated method stub

                            String housnum =  ALLHouseList.get(position).get(TWELVE_COLUMN).toString();

                            String contact =  ALLHouseList.get(position).get(EIGHT_COLUMN).toString();
                            String housefor  = ALLHouseList.get(position).get(FIRST_COLUMN).toString();
                            String area =  ALLHouseList.get(position).get(FOURTH_COLUMN).toString();
                            String town = ALLHouseList.get(position).get(THIRD_COLUMN).toString();
                            String price =  ALLHouseList.get(position).get(SIX_COLUMN).toString();
                            String description  =  ALLHouseList.get(position).get(FIRTH_COLUMN).toString();
                            String status  =  ALLHouseList.get(position).get(TWELVE_COLUMN).toString();
                            String views =  ALLHouseList.get(position).get(SEVEN_COLUMN).toString();
                            String province =  ALLHouseList.get(position).get(SECOND_COLUMN).toString();
                            String email =  ALLHouseList.get(position).get(NINE_COLUMN).toString();
                            String phone=  ALLHouseList.get(position).get(TEN_COLUMN).toString();




                            setCONTACRT(contact);
                            setAREA(area);
                            setDESCRIP(description);
                            setTOWN(town);
                            setPHONE(price);
                            setVIEWS(views);
                            setPROV(province);
                            setPRICE(price);
                            setEMAIL(email);
                            setNUMBER(phone);
                            setHOSFOR(housefor);

                            setHOUSE_NUMBER(housnum);


                            setCall("Main");


                            startActivity(new Intent(MainActivity.this, ClickedHouse.class));
                        }
                    });


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }else{
                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(MainActivity.this);

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
                                //call the rotate image anmation on the picture
                                Zoom();
                            }
                        });
                viewDetail.show();
                list.setBackgroundResource(R.drawable.home);
            }

        }//end post

        public String getHousePictures_listView(){
            InputStream is = null;
            String line = null;


            String email = "any";


            try {
                String _PICTURES_URL = "http://sopange.16mb.com/hon/myPictures_for_listView.php";
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
                        URLEncoder.encode("myemail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

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










    //BEGIN INNER CLASS GENERAL SEARCH

    private class GenralSearchInitialDownload  extends AsyncTask< Void, Integer,String> {

        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected String doInBackground(Void... params) {
            String data=getJSONUrl();
            return data;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // checkInternetConenction();
            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

       /*
       //check Internet conenction.
       private void checkInternetConenction(){
           ConnectivityManager check = (ConnectivityManager) getApplicationContext(). getSystemService(Context.CONNECTIVITY_SERVICE);
           if (check != null) { NetworkInfo[] info = check.getAllNetworkInfo();
               if (info != null)
                   for (int i = 0; i <info.length; i++)
                       if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                           Toast.makeText(getApplicationContext(), "Internet is connected", Toast.LENGTH_SHORT).show();
                       } }
           else{ Toast.makeText(getApplicationContext(), "not conencted to internet", Toast.LENGTH_SHORT).show(); }
       }

       */

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //this method will be running on UI thread
            pdLoading.dismiss();

            if (s!= null){

                try {
                    //PUTING THE DATA IN JASON OBJECT
                    JSONObject parentObject = new JSONObject(s);
                    JSONArray NEWbusDetail = parentObject.getJSONArray("result");

                    ALLHouseList.clear();
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
                        String date = jasonobject.getString("house_number");
                        String picture1 = jasonobject.getString("picture1");

                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put(FIRST_COLUMN, house_for);
                        temp.put(SECOND_COLUMN, house_province);
                        temp.put(THIRD_COLUMN, house_town);
                        temp.put(FOURTH_COLUMN, house_area);
                        temp.put(FIRTH_COLUMN, description);
                        temp.put(SIX_COLUMN, price);
                        temp.put(SEVEN_COLUMN, views);
                        temp.put(EIGHT_COLUMN, owner);
                        temp.put(NINE_COLUMN, email);
                        temp.put(TEN_COLUMN, phone);
                        temp.put(ELEVEN_COLUMN, picture1);
                        temp.put(TWELVE_COLUMN, date);

                        //add hashmap to arraylist
                        ALLHouseList.add(temp);


                    }//end for




                } catch (JSONException e) {
                    //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else{
                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(MainActivity.this);

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
        }//end on post


        //downloading the data
        public String getJSONUrl() {
            StringBuilder str = new StringBuilder();
            InputStream is =null;
            String url = address;
            try {
                URL myurl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                httpURLConnection.setInstanceFollowRedirects(false);

                // HttpURLConnection con = (HttpURLConnection)url.openConnection();
                is = new BufferedInputStream( httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str.toString();
        }//end getJasonurl

    }// END GenralSearchInitialDownload
























    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.landlordbtn:
                startActivity(new Intent(MainActivity.this, Landlord.class));
                break;
            case R.id. tenantbtn:
                startActivity(new Intent(MainActivity.this, Tenant.class));
                break;
            case R.id.  loginbtn:
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
            case R.id.     creatacountbtn:
                startActivity(new Intent(MainActivity.this, Register.class));
                break;
        }

    }

    public void onGreenTabClickHome(View v) {
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
    public void onGreenTabClickAbout(View v) {
        //startActivity(new Intent(MainActivity.this, About.class));
        Toast.makeText(getApplicationContext(),"coming soon", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickContact(View v) {
        //startActivity(new Intent(MainActivity.this, Contact.class));
        Toast.makeText(getApplicationContext(),"coming soon", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickMessages(View v) {
        //startActivity(new Intent(MainActivity.this, Messages.class));
        Toast.makeText(getApplicationContext(),"coming soon", Toast.LENGTH_SHORT).show();
    }


}
