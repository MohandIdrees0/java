package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    boolean isSignIn=true;
    Button SignInUp;
    SharedPreferences sharedPreferences;
    Button Switch;
    EditText emailAdress;
    EditText Password;
    TextView Error;
    CheckBox checkBox;
    ImageView image;
    ArrayList<user> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInUp=findViewById(R.id.signUpIn);
        Switch=findViewById(R.id.Switch);
        emailAdress=findViewById(R.id.EmailAdress);
        image=findViewById(R.id.imageView3);
        Password=findViewById(R.id.Password);
        Error=findViewById(R.id.error);
        image.setImageResource(R.drawable.news_report);
        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        checkBox=findViewById(R.id.checkBox);
        users=new ArrayList<user>();

        if(sharedPreferences.getBoolean("signdIn",false)){
            startActivity(new Intent(this, MainpageActivity.class));
        }
        //
        LoadDataFromDB();
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setActivated(!checkBox.isActivated());
            }
        });
        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSignIn=!isSignIn;
                SignInUp.setText(isSignIn?"Sign In":"Sign Up");
                Switch.setText(isSignIn?"Register":"I have an account");
            }
        });
        SignInUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                String email= String.valueOf(emailAdress.getText());
                String password= String.valueOf(Password.getText());
                user x=new user(email,password);
                String error=cheackValidEmailAndPassword(email,password);
                Error.setText(error);
                if(error.isEmpty()){
                    Gson gson=new Gson();
                    users.add(x);
                    String jsonData=gson.toJson(users);
                    editor.putString("usersData",jsonData);
                    //add Remember Me
                    if(checkBox.isActivated()){
                        editor.putString("rememberMe",gson.toJson(x));
                    }
                    editor.putBoolean("signdIn",true);
                    Intent intent=new Intent(getApplicationContext(), MainpageActivity.class);
                    startActivity(intent);
                }
                else if(error.compareToIgnoreCase("loading")==0){
                    if(checkBox.isActivated()){
                        editor.putString("rememberMe",new Gson().toJson(x));
                    }
                    editor.putBoolean("signdIn",true);
                    Intent intent=new Intent(getApplicationContext(), MainpageActivity.class);
                    startActivity(intent);
                }
                editor.apply();
            }
        });
    }
    String cheackValidEmailAndPassword(String email,String password){
        if(isSignIn){
            for(int i=0;i<users.size();i++){
                if(users.get(i).email.compareToIgnoreCase(email)==0){
                    if(users.get(i).password.compareTo(password)==0){

                        //save in DB this user Registered
                        return "loading";
                    }
                    else{
                        return "Incorrect Password";
                    }

                }
            }
            return "email does not exist";
        }
        if(!email.contains("@gmail.com")){
            return  "email Should have @gmail.com extention";
        }
        for(int i=0;i<users.size();i++){
            if(users.get(i).email.compareToIgnoreCase(email)==0){
                return "this email is already exist";
            }
        }
        return password.length()<6?"password length should be 6 characters at least":"";
    }
    void LoadDataFromDB(){
        Gson  gson= new Gson();
        //load usersAcount
        String data=sharedPreferences.getString("usersData","");
        if(!data.isEmpty()){
            user[] users=gson.fromJson(data,user[].class);
            this.users.addAll(Arrays.asList(users));
        }
        //load remember me account
        String rememberme=sharedPreferences.getString("rememberMe","");
        if(!rememberme.isEmpty()){
            user x=gson.fromJson(rememberme,user.class);
            emailAdress.setText(x.email);
            Password.setText(x.password);
        }
    }
}