package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static  com.example.dell.myapplication.Constants.FIRST_COLUMN;
import static  com.example.dell.myapplication.Constants.SECOND_COLUMN;
import static  com.example.dell.myapplication.Constants.THIRD_COLUMN;
import static  com.example.dell.myapplication.Constants.FOURTH_COLUMN;
import static  com.example.dell.myapplication.Constants.FIRTH_COLUMN;
import static  com.example.dell.myapplication.Constants.SIX_COLUMN;
import static  com.example.dell.myapplication.Constants.SEVEN_COLUMN;
import static  com.example.dell.myapplication.Constants.EIGHT_COLUMN;
import static  com.example.dell.myapplication.Constants.NINE_COLUMN;
import static  com.example.dell.myapplication.Constants.TEN_COLUMN;
import static  com.example.dell.myapplication.Constants.ELEVEN_COLUMN;
import static com.example.dell.myapplication.Constants.TWELVE_COLUMN;


public class Tenant extends AppCompatActivity  implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener{

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private  static String HOUSE_NUMBER,CONTACRT,PROV,PHONE,PRICE,TOWN,AREA,EMAIL,VIEWS,DESCRIP ,NUMBER,HOSFOR ;

    //we are storing information about a house in an array list
    private ArrayList<HashMap<String, String>> houseList;
    private ArrayList<HashMap<String, String>> pic_list;
    private  List<String> arealist = new ArrayList<String>();
    private List<String> townlist = new ArrayList<String>();

    private ListView lv;
    private ListPopupWindow HF_LPW, PROV_LPW,TOWN_LPW,PRICE_LPW,AREA_LPW,RH_LPW,RP_LPW;

    private String UPLOADHOUSE_URL = "http://sopange.16mb.com/hon/postHouse.php";
    private String address ="http://sopange.16mb.com/hon/listHouses.php";
    private String  FILTERHOUSE_URL = "http://sopange.16mb.com/hon/MultiSearch.php";


private String  HOUSE_NUMMBER = "priscamylove";


    public EditText ET_Thfor,ET_Tprice,ET_Tprovince,ET_Ttown,ET_Tarea,ET_Rhfor,ET_Rprovince
            , ET_Rcontact ,ET_Rdescription,ET_Rprice,ET_Rtown;

    public String myPhon;
    public String myNam;
    public String myEmai;
    public String Rhfor;
    public String Rprovince;
    public String Rtown;
    public String Rcphone;
    public String Rdescription;
    public String Rhprice;
    public String Rcemail;
    public String Rarea;
    public String Rcname;
    public String RhousNum;
    public String Thfor;
    public String Tprovince;
    public String Ttown;
    public String Thprice;
    public String Tharea;
    public String tarea;
    public static String Tcall, role;

    public  TextView tenantNAME ,  T_LogoutTV,  T_LoginTV;


    private String image_email= "guest@hfr.com";
    private static String   timeStamp  = "";
    private  int img_counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);

        clockwise();

        Constants.addArrays();
        //initialising our storage guy
        houseList=new ArrayList<HashMap<String,String>>();
        pic_list = MainActivity.ALLpictureList;



        timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss",
                Locale.getDefault()).format(new Date());
        img_counter = 0;

        ET_Thfor = (EditText) findViewById(R.id.thousefortv);
        ET_Tarea = (EditText) findViewById(R.id.tareatv);
        ET_Tprice = (EditText) findViewById(R.id.tpricetv);
        ET_Tprovince = (EditText) findViewById(R.id.tprovincetv);
        ET_Ttown = (EditText) findViewById(R.id.ttowntv);

        ET_Rprovince = (EditText) findViewById(R.id.requestProvincetv);
        ET_Rhfor = (EditText) findViewById(R.id.requestHfor);

        ET_Rdescription =(EditText) findViewById(R.id.requestDescriptiontv);
        ET_Rprice =(EditText) findViewById(R.id.requestPricetv);
        ET_Rtown =(EditText) findViewById(R.id.requestTowntv);
        ET_Rcontact =(EditText) findViewById(R.id.requestContacttv);

        ET_Thfor.setOnTouchListener(this);//
        ET_Tarea.setOnTouchListener(this);//
        ET_Tprice.setOnTouchListener(this);//
        ET_Tprovince.setOnTouchListener(this);//
        ET_Ttown.setOnTouchListener(this);//

        ET_Rprovince.setOnTouchListener(this);//
        ET_Rhfor.setOnTouchListener(this);//

        Tcall = "tenan";


        //getting details about the user
        myPhon= Login.getPhone();
        myNam =Login.getName();
        myEmai =Login.getEmail();
        role = Login.getRole();

        //what kind of a user is this
        if (myNam.equalsIgnoreCase("Guest")  )
        {
            T_LogoutTV  = (TextView) findViewById(R.id.Tlogouttv);
            T_LogoutTV.setText("");
            T_LogoutTV.setEnabled(false);
            T_LoginTV  = (TextView) findViewById(R.id. Tlogintv);
            T_LoginTV.setText("Login");
        }else{



            if (role.equalsIgnoreCase("landlord")  )
            {
                T_LogoutTV  = (TextView) findViewById(R.id.Tlogouttv);
                T_LogoutTV.setText("");
                T_LogoutTV.setEnabled(false);
                T_LoginTV  = (TextView) findViewById(R.id. Tlogintv);
                T_LoginTV.setText("Login");
            }else{
                T_LogoutTV  = (TextView) findViewById(R.id. Tlogouttv);
                T_LogoutTV.setText("Logout");
                T_LoginTV  = (TextView) findViewById(R.id.Tlogintv);
                T_LoginTV.setText("");
                T_LoginTV.setEnabled(false);
                ET_Rcontact.setText(myPhon);

            }

        }

        //setting welcome name
        tenantNAME  = (TextView) findViewById(R.id. tenantNameTV);
        tenantNAME.setText(myNam);



        HOUSE_NUMBER = "guest"; PROV= "guest"; TOWN= "guest"; AREA = "guest"; PRICE = "guest";NUMBER = "sdfds";
        VIEWS = "guest";DESCRIP = "guest";EMAIL = "guest";PHONE = "guest"; ;HOSFOR = "guest";


        //for the list popup windows


        //the list
        HF_LPW = new ListPopupWindow(getApplicationContext());
        HF_LPW .setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.hforlist));
        //the list
        RH_LPW = new ListPopupWindow(getApplicationContext());
        RH_LPW .setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.hforlist));

        //the list
        PRICE_LPW= new ListPopupWindow(getApplicationContext());
        PRICE_LPW.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.pricelist));

        //the list
        PROV_LPW = new ListPopupWindow(getApplicationContext());
        PROV_LPW.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.provincelist));
        //the list
        RP_LPW = new ListPopupWindow(getApplicationContext());
        RP_LPW.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.provincelist));

        //the list
        TOWN_LPW = new ListPopupWindow(getApplicationContext());
        TOWN_LPW .setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, townlist));

        //the list
        AREA_LPW = new ListPopupWindow(getApplicationContext());
        AREA_LPW.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arealist));


        HF_LPW.setAnchorView(ET_Thfor);
        HF_LPW.setModal(true);
        RH_LPW.setAnchorView(ET_Rhfor);
        RH_LPW.setModal(true);

        PRICE_LPW.setAnchorView(ET_Tprice);
        PRICE_LPW.setModal(true);

        PROV_LPW.setAnchorView(ET_Tprovince);
        PROV_LPW.setModal(true);
        RP_LPW.setAnchorView(ET_Rprovince);
        RP_LPW.setModal(true);

        TOWN_LPW .setAnchorView(ET_Ttown);
        TOWN_LPW .setModal(true);

        AREA_LPW.setAnchorView(ET_Tarea);
        AREA_LPW.setModal(true);


        HF_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toitem = parent.getItemAtPosition(position).toString();
                ET_Thfor.setText(Toitem);
                HF_LPW.dismiss();
            }
        });

        PRICE_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Fromitem = parent.getItemAtPosition(position).toString();
                ET_Tprice.setText(Fromitem);
                Toast.makeText(Tenant.this, "Click Search button Above", Toast.LENGTH_LONG).show();
                PRICE_LPW.dismiss();
            }
        });


        AREA_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toitem =  parent.getItemAtPosition(position).toString();
                ET_Tarea.setText(Toitem);
                AREA_LPW.dismiss();
            }
        });

        TOWN_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toitem = parent.getItemAtPosition(position).toString();
                ET_Ttown.setText(Toitem);
                TOWN_LPW.dismiss();
            }
        });

        PROV_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Fromitem =  parent.getItemAtPosition(position).toString();
                ET_Tprovince.setText(Fromitem);
                PROV_LPW.dismiss();
            }
        });

        RH_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toitem = parent.getItemAtPosition(position).toString();
                ET_Rhfor.setText(Toitem);
                RH_LPW.dismiss();
            }
        });

        RP_LPW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Fromitem =  parent.getItemAtPosition(position).toString();
                ET_Rprovince.setText(Fromitem);
                RP_LPW.dismiss();
            }
        });




    }//END on create




    public void setTCall(String call){
        this.Tcall=call;
    }

    public static String getTCall(){
        return Tcall;
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



    @Override
    public void onClick(View v) {

    }//END onClick

    public void clockwise() {
        TextView txtView = (TextView)
                findViewById(R.id.textView_zoom);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        txtView.startAnimation(animation1);

    }

    public void tenantButtons(View view){
        switch (view.getId()){
            case R.id.searchButton:
                //for a filtered search

                setContentView(R.layout.search_results);
                //Make call to AsyncTask for general search
                new GenralSearchInitialDownload().execute();

                //    filteredSearch();
                break;

            case R.id.requestBtn:
                //for a request to post a house
                Button image1 = (Button)
                        findViewById(R.id.requestBtn);
                image1.clearAnimation();

                //check so as to only allow a login user to post a house
                if (  tenantNAME .getText().toString().equalsIgnoreCase("Guest"))
                {
                    Toast.makeText(Tenant.this,"Hi, You need to Login First",Toast.LENGTH_LONG).show();
                }else{
                    postArequestHouse();
                }

                break;

            case R.id.Tlogintv:
                Intent intent1 = new Intent( Tenant.this, Login.class);
                Tenant.this.startActivity(intent1);
                break;


            case R.id.Tlogouttv:
                Login.CustomerInfo.clear();
                Intent intent2 = new Intent( Tenant.this,Login.class);
                Tenant.this.startActivity(intent2);
                break;
            case R.id.  landlordHometv:
                Intent intent11 = new Intent( Tenant.this, MainActivity.class);
                Tenant.this.startActivity(intent11);
                break;

            case R.id.  requestDescriptiontv:

                Button image = (Button)
                        findViewById(R.id.requestBtn);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move1);
                image.startAnimation(animation1);
                break;


        }
    }//END tenantButtons Actions


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
    public void onGreenTabClickSBACK(View v) {
        setContentView(R.layout.activity_tenant);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }//END onItemClick









    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.requestHfor:  //FROM

                int DRAWABLE_RIGHTt=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_RIGHTt].getBounds().width())){
                        RH_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.requestProvincetv:

                int DRAWABLE_LEFTt=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_LEFTt].getBounds().width())){
                        RP_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.thousefortv:  //FROM

                int DRAWABLE_RIGHT=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        HF_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.tpricetv:

                int DRAWABLE_LEFT=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())){
                        PRICE_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.tprovincetv:

                int DRAWABLE_TOP=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_TOP].getBounds().width())){
                        PROV_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.ttowntv:

                int DRAWABLE_BOTTOM=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_BOTTOM].getBounds().width())){
                        TOWN_LPW.show();
                        return true;
                    }
                }
                break;
            case R.id.tareatv:

                int DRAWABLE_TO=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getX()>= (v.getWidth() - ((EditText)v)
                            .getCompoundDrawables()[DRAWABLE_TO].getBounds().width())){
                        AREA_LPW.show();
                        return true;
                    }
                }
                break;




        }
        return false;
    }//END onTouch




    private void  postArequestHouse() {

        if ((!ET_Rhfor.getText().toString().equals("")) && (!ET_Rcontact.getText().toString().equals(""))
                && (!ET_Rprovince.getText().toString().equals("")) && (!ET_Rdescription.getText().toString().equals(""))
                && (!ET_Rtown.getText().toString().equals("")) && (!ET_Rprice.getText().toString().equals(""))) {

            Rhfor = ET_Rhfor.getText().toString().toLowerCase();
            Rprovince = ET_Rprovince.getText().toString().toLowerCase();
            Rtown = ET_Rtown.getText().toString().toLowerCase();
            Rcphone = ET_Rcontact.getText().toString().toLowerCase();
            Rdescription = ET_Rdescription.getText().toString().toLowerCase();
            Rhprice = ET_Rprice.getText().toString().toLowerCase();

            Rcemail =Login.getEmail();
            Rcname = Login.getName();
            RhousNum = Login.getName();

            Rarea = "Any";

            //make a call to the inner class to start execution
            HOUSE_NUMMBER  = timeStamp +"_" +myEmai;
            startRequestHouse();

            //clear fields
            ET_Rhfor.setText("");
            ET_Rprice.setText("");
            ET_Rprovince.setText("");
            ET_Rtown.setText("");
            ET_Rdescription.setText("");
            // ET_Rhfor.setText("");




        } else {
            Toast.makeText(getApplicationContext(),
                    "Please Fill in All The Details", Toast.LENGTH_SHORT).show();
        }



    }// end postArequestHouse call to  startRequestHouse();









    private void  filteredSearch() {
        if ((!ET_Rhfor.getText().toString().equals("")) && (!ET_Rcontact.getText().toString().equals(""))
                && (!ET_Rprovince.getText().toString().equals("")) && (!ET_Rdescription.getText().toString().equals(""))
                && (!ET_Rtown.getText().toString().equals("")) && (!ET_Rprice.getText().toString().equals(""))) {

            Thfor = ET_Thfor.getText().toString().toLowerCase();
            Tprovince = ET_Tprovince.getText().toString().toLowerCase();
            Ttown = ET_Ttown.getText().toString().toLowerCase();
            Thprice = ET_Tprice.getText().toString().toLowerCase();
            Tharea = ET_Tarea.getText().toString().toLowerCase();

            //make a call to the inner class to start execution
            //   startFilteredSearch();
        } else {
            //  Toast.makeText(getApplicationContext(),
            //  "Please Fill in All The Details", Toast.LENGTH_SHORT).show();
        }
    }// END   filteredSearch call to  startRequestHouse();






















    //(FILTERED SEARCH)
    public void  startRequestHouse() {

        //BEGIN INNER CLASS SAVE REQUESTED HOUSE
        class RequestHousePOST extends AsyncTask<String, Void, String> {
            ProgressDialog loading;



            @Override
            protected String doInBackground(String... params) {
                String data = postHouse();
                return data;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Tenant.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s!= null){


                    loading.dismiss();

                    uploadImage();
                    img_counter = 0;

                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Tenant.this);

                    viewDetail.setIcon(android.R.drawable.ic_menu_more);
                    viewDetail.setTitle(" POST Successful");
                    viewDetail.setMessage(s);
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    //  Intent intent = new Intent(Landlord.this, RouteSelect.class);
                                    // Landlord.this.startActivity(intent);

                                    dialog.dismiss();
                                    //make call to login now

                                }
                            });

                    viewDetail.setPositiveButton("View",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    //  Intent intent = new Intent(Landlord.this, RouteSelect.class);
                                    // Landlord.this.startActivity(intent);

                                    dialog.dismiss();

                                    //make call to the general search in tenant
                                    setContentView(R.layout.search_results);
                                    //Make call to AsyncTask for general search
                                    new GenralSearchInitialDownload().execute();

                                }
                            });
                    viewDetail.show();

                    // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();





                }else{
                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Tenant.this);

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

            }


            //downloading the data
            public String postHouse() {

                InputStream is = null;
                String line = null;

                String requestType = "I Want a House For "+ Rhfor;
                String requestDesc = "> "+ Rdescription;
                String requestProv = "> in "+ Rtown ;

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
                    String data = URLEncoder.encode("hfor", "UTF-8") + "=" + URLEncoder.encode(requestType, "UTF-8") + "&" +
                            URLEncoder.encode("province", "UTF-8") + "=" + URLEncoder.encode(requestProv , "UTF-8") + "&" +
                            URLEncoder.encode("town", "UTF-8") + "=" + URLEncoder.encode(Rtown, "UTF-8") + "&" +
                            URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode(Rarea, "UTF-8") + "&" +
                            URLEncoder.encode("houseNumber", "UTF-8") + "=" + URLEncoder.encode( HOUSE_NUMMBER, "UTF-8") + "&" +
                            URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(Rhprice, "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(requestDesc, "UTF-8") + "&" +
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Rcname, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(myEmai, "UTF-8") + "&" +
                            URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(Rcphone, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    // HttpURLConnection con = (HttpURLConnection)url.openConnection();
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
                return null;
            }//end postHouse

        }//end inner class
        new RequestHousePOST().execute();
    //    new GenralSearchInitialDownload().execute();


    }//END SAVE REQUESTED HOUSE (TENANT POST)



























    //BEGIN INNER CLASS GENERAL SEARCH
    public class GenralSearchInitialDownload  extends AsyncTask< Void, Integer,String> {
        ProgressDialog pdLoading = new ProgressDialog(Tenant.this);

        @Override
        protected String doInBackground(Void... params) {
            String data=getJSONUrl();
            return data;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if (s!= null){



                //this method will be running on UI thread
                pdLoading.dismiss();
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

                        houseList.add(temp);
                        townlist.add(house_town);
                        arealist.add(house_area);



                    }//end for


                    // Setup and Handover data to list viewF
                    AdapterHouse adapter=new AdapterHouse(Tenant.this, houseList, pic_list);
                    lv=(ListView)findViewById(R.id.listViewTen);
                     lv.setAdapter(adapter);



                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            HashMap<String, String> bus_detail = houseList.get(position);
                            String Slecteditem =bus_detail.get(SIX_COLUMN);

                            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

                            String contact =  houseList.get(position).get(EIGHT_COLUMN).toString();
                            String housefor  =houseList.get(position).get(FIRST_COLUMN).toString();
                            String area =  houseList.get(position).get(FOURTH_COLUMN).toString();
                            String town = houseList.get(position).get(THIRD_COLUMN).toString();
                            String price =  houseList.get(position).get(SIX_COLUMN).toString();
                            String description  = houseList.get(position).get(FIRTH_COLUMN).toString();
                            String views =  houseList.get(position).get(SEVEN_COLUMN).toString();
                            String province =  houseList.get(position).get(SECOND_COLUMN).toString();
                            String email =  houseList.get(position).get(NINE_COLUMN).toString();
                            String phone=  houseList.get(position).get(TEN_COLUMN).toString();

                            String housnum =  houseList.get(position).get(TWELVE_COLUMN).toString();

                            Toast.makeText(getApplicationContext(), houseList.get(position).get(TWELVE_COLUMN).toString(), Toast.LENGTH_SHORT).show();

                            setCONTACRT(contact);
                            setAREA(area);
                            setDESCRIP(description);
                            setTOWN(town);
                            setPHONE(price);
                            setVIEWS(views);
                            setPROV(province);
                            setPRICE(price);
                            setEMAIL(email);
                            setPHONE(phone);
                            setHOSFOR(housefor);
                            setNUMBER(phone);

                            setHOUSE_NUMBER(housnum);

                            setTCall("Tenant");

                            startActivity(new Intent(Tenant.this, ClickedHouse.class));

                        }
                    });

                } catch (JSONException e) {
                    // Toast.makeText(Tenant.this, e.toString(), Toast.LENGTH_LONG).show();

                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Tenant.this);

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


            }else{

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











/*===================================================================================================
                        UPLOADING AN IMAGE
 ====================================================================================================*/




    // this method allows to convert the selected image to base64- for easy upload
    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    //uploading the selected image
    private void uploadImage(){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        String UPLOAD_URL = "http://sopange.16mb.com/hon/uploadPhoto.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Tenant.this, s , Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Tenant.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
             Bitmap   bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.home);

                String image = getStringImage(bitmap);
                //Getting Image Name
                String image_name = "dummyImage";
                //Getting Image House Number
                img_counter = img_counter + 1;
                String count = String.valueOf(img_counter);



                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("image", image);
                params.put("name", image_name);
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


























/*


//INNER CLASS FOR FILTER SEARCH DATA DOWNLOAD

    //(TENANT POST)
    public void startRequestHouse() {

        //BEGIN INNER CLASS SAVE REQUESTED HOUSE
        class SaveREQUESTHouse extends AsyncTask<String, Void, String> {
            ProgressDialog pdLoading1 = new ProgressDialog(Tenant.this);


            @Override
            protected String doInBackground(String... params) {
                String data = searchAFilteredHouse();
                return data;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading1.setMessage("\tLoading...");
                pdLoading1.setCancelable(false);
                pdLoading1.show();
            }
            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                pdLoading1.dismiss();
                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();

                try{
                //PUTING THE DATA IN JASON OBJECT
                JSONObject parentObject = new JSONObject(s1);
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
                    String date = jasonobject.getString("house_number");
                    String picture1 = jasonobject.getString("picture1");

                    HashMap<String,String> temp1=new HashMap<String, String>();
                    temp1.put(FIRST_COLUMN, house_for);
                    temp1.put(SECOND_COLUMN, house_province);
                    temp1.put(THIRD_COLUMN, house_town);
                    temp1.put(FOURTH_COLUMN, house_area);
                    temp1.put(FIRTH_COLUMN, description);
                    temp1.put(SIX_COLUMN, price);
                    temp1.put(SEVEN_COLUMN, views);
                    temp1.put(EIGHT_COLUMN, owner);
                    temp1.put(NINE_COLUMN, email);
                    temp1.put(TEN_COLUMN, phone);
                    temp1.put(ELEVEN_COLUMN, picture1);
                    temp1.put(TWELVE_COLUMN, date);

                    //add hashmap to arraylist
                    houseList.add(temp1);

                }//end for

                // Setup and Handover data to list view
                AdapterHouse adapter=new AdapterHouse(Tenant.this, houseList);
                lv=(ListView)findViewById(R.id.listViewTen);
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        HashMap<String, String> bus_detail = houseList.get(position);
                        String Slecteditem =bus_detail.get(SIX_COLUMN);

                        Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Tenant.this, ClickedHouse.class));

                    }
                });

            } catch (JSONException e) {
                Toast.makeText(Tenant.this, e.toString(), Toast.LENGTH_LONG).show();
            }
            }//end on post

            //downloading the data
            public String  searchAFilteredHouse() {

                InputStream is = null;
                String line = null;

                try {
                    URL url = new URL(FILTERHOUSE_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    httpURLConnection.setInstanceFollowRedirects(false);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("Thfor", "UTF-8") + "=" + URLEncoder.encode(Thfor, "UTF-8") + "&" +
                            URLEncoder.encode("Tprovince", "UTF-8") + "=" + URLEncoder.encode(Tprovince , "UTF-8") + "&" +
                            URLEncoder.encode("Ttown", "UTF-8") + "=" + URLEncoder.encode(Ttown, "UTF-8") + "&" +
                            URLEncoder.encode("Tharea", "UTF-8") + "=" + URLEncoder.encode(Tharea, "UTF-8") + "&" +
                            URLEncoder.encode("Thprice", "UTF-8") + "=" + URLEncoder.encode(Thprice, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    // HttpURLConnection con = (HttpURLConnection)url.openConnection();
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
                return null;
            }//end postHouse

        }//end inner class
        SaveREQUESTHouse srh = new SaveREQUESTHouse();
        srh.execute();

        //making a call to updated the general search
        new GenralSearchInitialDownload().execute();

    }//END SAVE FILTERED SEARCH



*/






















}//END MAIN CLASS (Tenant Class)
