package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

public class Register extends AppCompatActivity implements  View.OnClickListener{

    private String Email,Pass,FName,LName,Address,Phone,
            male_CKB,female_CKB,tenant_CKB,landlord_CKB,
            user_sex,user_role
            ;
    private EditText email,pass,fname,lname,address,phone;
    private CheckBox CKB_male,CKB_female,CKB_tenant,CKB_landlord;

    private Button registerButton;
    private static final String REGISTER_USER_URL = "http://sopange.16mb.com/hon/RegisterUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.newemail);
        pass = (EditText) findViewById(R.id.new_user_Pass);
        fname= (EditText) findViewById(R.id.new_user_fname);
        lname = (EditText) findViewById(R.id.new_user_lname);
        address = (EditText) findViewById(R.id.new_user_town);
        phone = (EditText) findViewById(R.id.new_user_phone);

        CKB_female =(CheckBox) findViewById(R.id.femaleCkB);
        CKB_male =(CheckBox) findViewById(R.id.maleCkB);
        CKB_tenant =(CheckBox) findViewById(R.id.tenantCkB);
        CKB_landlord =(CheckBox) findViewById(R.id.landlordCkB);


        registerButton = (Button) findViewById(R.id.registerUserButton);
        registerButton.setOnClickListener(this);

        CKB_female.setOnClickListener(this);
        CKB_male.setOnClickListener(this);
        CKB_tenant.setOnClickListener(this);
        CKB_landlord.setOnClickListener(this);

        female_CKB ="no";
        male_CKB ="no";
        tenant_CKB ="no";
        landlord_CKB ="no";

        user_role = "no";
        user_sex ="no";



    }

    @Override
    public void onClick(View v) {
        if (v == registerButton) {
            registerBus();
        }

        if (v ==  CKB_landlord) {
            CKB_tenant.setChecked(false);
            user_role = "landlord";
        }
        if (v ==  CKB_tenant) {
            CKB_landlord.setChecked(false);
            user_role ="tenant";
        }
        if (v ==  CKB_male) {
            CKB_female.setChecked(false);
            user_sex = "male";
        }
        if (v ==  CKB_female) {
            CKB_male.setChecked(false);
            user_sex= "female";
        }


    }

    public void onGreenTabClickrCLogin(View v) {
        //startActivity(new Intent(MainActivity.this, Contact.class));
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void onGreenTabClickrHome(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void onGreenTabClicrkAbout(View v) {
        //startActivity(new Intent(MainActivity.this, About.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickrContact(View v) {
        //startActivity(new Intent(MainActivity.this, Contact.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }
    public void onGreenTabClickrMessages(View v) {
        //startActivity(new Intent(MainActivity.this, Messages.class));
        Toast.makeText(getApplicationContext(),"Not Yet Worked On", Toast.LENGTH_SHORT).show();
    }
    private void registerBus() {
        if (  ( !  email.getText().toString().equals("")) && ( !  pass.getText().toString().equals("")) && ( !  fname.getText().toString().equals(""))
                && ( !  user_sex.equals("no"))  && ( !  user_role.equals("no"))
                && ( !   lname.getText().toString().equals(""))&& ( ! address.getText().toString().equals(""))&& ( ! phone.getText().toString().equals("")))
        {
            // DO THIS
            Email = email.getText().toString().toLowerCase();
            Pass =  pass.getText().toString().toLowerCase();
            FName= fname.getText().toString().toLowerCase();
            LName =  lname.getText().toString().toLowerCase();
            Address =  address.getText().toString().toLowerCase();
            Phone = phone.getText().toString().toLowerCase();

            male_CKB = CKB_male.getText().toString().toLowerCase();
            female_CKB = CKB_female.getText().toString().toLowerCase();
            tenant_CKB = CKB_tenant.getText().toString().toLowerCase();
            landlord_CKB = CKB_landlord.getText().toString().toLowerCase();

            //  Male = male.getText().toString();
            // Female =female.getText().toString();

//call register buses method going to the database
            registerUser();

            email.setText("");
            pass.setText("");
            phone.setText("");
            fname.setText("");
            lname.setText("");
            address.setText("");
        }

        else
        {
            Toast.makeText(getApplicationContext(),
                    "Please Fill in All The Details", Toast.LENGTH_SHORT).show();
        }
    }


    private void registerUser() {

        class RegisterBus extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            protected void onPreExecute (){
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s!= null){

                    loading.dismiss();

                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Register.this);

                    viewDetail.setIcon(android.R.drawable.ic_menu_help);
                    viewDetail.setTitle(" Registration Successful");
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
                                    startActivity(new Intent(Register.this, Login.class));
                                }
                            });
                    viewDetail.show();

                   // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


                }else{
                    final AlertDialog.Builder viewDetail = new AlertDialog.Builder(Register.this);

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
                                    //make call to login now
                                    startActivity(new Intent(Register.this, Login.class));
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
                    URL url = new URL(REGISTER_USER_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    httpURLConnection.setInstanceFollowRedirects(false);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode( Email, "UTF-8") + "&" +
                            URLEncoder.encode("Pass", "UTF-8") + "=" + URLEncoder.encode(Pass, "UTF-8") + "&" +
                            URLEncoder.encode("FName", "UTF-8") + "=" + URLEncoder.encode(FName, "UTF-8") + "&" +
                            URLEncoder.encode("LName", "UTF-8") + "=" + URLEncoder.encode(LName, "UTF-8") + "&" +
                            URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8") + "&" +

                            URLEncoder.encode("role", "UTF-8") + "=" + URLEncoder.encode(user_role, "UTF-8") + "&" +
                            URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(user_sex, "UTF-8") + "&" +


                            URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");

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
