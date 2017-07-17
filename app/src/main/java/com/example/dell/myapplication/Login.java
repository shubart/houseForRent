package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private static EditText ET_EMAIL,ET_PASS;
    private static String login_email, userName, userPhone  ;
    private String login_pass;
    private static final String LOGIN_USER_URL = "http://sopange.16mb.com/hon/LoginUser.php";


    private static String u_phone= "Guest";
    private static String u_email= "Guest";
    private static String u_name= "Guest";
    private static String  user_role = "empty";
    static  List<String> CustomerInfo = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_EMAIL = (EditText)findViewById(R.id.etUseremail);
        ET_PASS = (EditText)findViewById(R.id.etPassword);




        //make a toast
        Toast.makeText(this," \n First Time ?..Click Create Account! \n",Toast.LENGTH_LONG).show();

    }//end on create




    public void setName(String u_name){Login.this.u_name = u_name;}
    public void setEmail(String u_email){Login.this.u_email = u_email;}
    public void setPhone(String u_phone){Login.this.u_phone = u_phone;}
    public void setRole(String user_role){Login.this.user_role = user_role;}


    public static String getName(){return u_name;}
    public static String getEmail(){
        return u_email;
    }
    public static String getPhone(){
        return u_phone;
    }
    public static String getRole(){
        return user_role;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
                onLoginClick();
                break;

            case R.id.regButton:
                startActivity(new Intent(this,Register.class));
                break;

        }

    }

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


    /**
     * Login button click event
     * A Toast is set to alert when the Email and Password field is empty
     **/



    public void onLoginClick() {
        login_email = ET_EMAIL.getText().toString().toLowerCase();
        login_pass = ET_PASS.getText().toString().toLowerCase();

        if (  ( !ET_EMAIL.getText().toString().equals("")) && ( !ET_PASS.getText().toString().equals("")) )
        {
            //STRING METHOD TO SEND DATA TO THE BACKGROUND TASK

            LoginUser();

            ET_PASS.setText("");
            ET_EMAIL.setText("");

        } else if ((!ET_EMAIL.getText().toString().equals("")) )
        {
            Toast.makeText(getApplicationContext(),
                    "Password field empty", Toast.LENGTH_SHORT).show();
        }
        else if ( ( !ET_PASS.getText().toString().equals("")) )
        {
            Toast.makeText(getApplicationContext(),
                    "Email field empty", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(getApplicationContext(),
                    "Please Fill in The Details empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoginUser() {

        class RegisterBus extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            AlertDialog alertDialog;

            protected void onPreExecute (){
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Please Wait",null,true,true);
                alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Login Information.....");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                if (s!= null){









                    loading.dismiss();
                    //   Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();



                    // JSON OBJECT DATA
                    try {


                        CustomerInfo.clear();

                        //PUTING THE DATA IN JASON OBJECT
                        JSONObject parentObject = new JSONObject(s);
                        JSONArray jdata = parentObject.getJSONArray("result");

                        for(int i = 0; i < jdata.length(); i++){
                            JSONObject c = jdata.getJSONObject(i);

                            String email =c.getString("user_email");
                            String uname =c.getString("user_name");
                            String uphone=c.getString("user_phone");
                            String role =c.getString("role");

                            CustomerInfo.add(email);
                            CustomerInfo.add(uname);
                            CustomerInfo.add(uphone);
                            CustomerInfo.add(role);



                            login_email =CustomerInfo.get(0);
                            userName=  CustomerInfo.get(1);
                            userPhone=   CustomerInfo.get(2);
                            user_role =   CustomerInfo.get(3);

                            setEmail(CustomerInfo.get(0));
                            setName(CustomerInfo.get(1));
                            setPhone(CustomerInfo.get(2));
                            setRole(CustomerInfo.get(3));



                        }//end forloop

                        //deciding what page to display


                        if (user_role.equalsIgnoreCase("empty")){

                            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Login.this);

                            viewDetail.setIcon(android.R.drawable.ic_menu_help);
                            viewDetail.setTitle(" Login Fail");
                            viewDetail.setMessage("   oops  sorry !  Wrong email or Password ...please try again");
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



                        }else{


                            if (user_role.equalsIgnoreCase("landlord")){
                                startActivity(new Intent(Login.this, Landlord.class));
                                Toast.makeText(Login.this,"Welcome  " + userName,Toast.LENGTH_LONG).show();

                            }else if(user_role.equalsIgnoreCase("tenant")){
                                startActivity(new Intent(Login.this, Tenant.class));
                                Toast.makeText(Login.this,"Welcome  " + userName,Toast.LENGTH_LONG).show();

                            }else if (user_role.equalsIgnoreCase("admin")){
                                startActivity(new Intent(Login.this, MainActivity.class));
                                Toast.makeText(Login.this,"Welcome  " + userName,Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(Login.this,"oops! login fail ...please try again",Toast.LENGTH_LONG).show();
                            }



                        }






                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }//end try


                }else{
                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Login.this);

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
                    loading.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                return sendStaff();
            }

            public String sendStaff(){

                InputStream is;
                String line;


                try {
                    URL url = new URL(LOGIN_USER_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    httpURLConnection.setInstanceFollowRedirects(false);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("login_email", "UTF-8") + "=" + URLEncoder.encode(  login_email, "UTF-8") + "&" +
                            URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode( login_pass, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    // HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    is = new BufferedInputStream( httpURLConnection.getInputStream());

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    StringBuffer sb = new StringBuffer();

                    //making sure buffered reader is not null

                    while ( (line = br.readLine()) != null){
                        sb.append( line  +"\n");
                    }
                    return sb.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }


        }//end innerclass

        RegisterBus rb = new RegisterBus();
        rb.execute();
    }


}
