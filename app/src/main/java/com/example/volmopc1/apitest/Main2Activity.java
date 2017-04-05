package com.example.volmopc1.apitest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
public static  String pagelink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
         pagelink = bundle.getString("pagelink");
       // System.out.println(pagelink);

     /*   bean b=new bean();
        b.setData(pagelink);
        System.out.println( "dscs"+b.getData());*/
        //MyIntentService j=new MyIntentService();
       // j.MyIntentService1(b);
        Intent intent = new Intent(this, MyIntentService.class);
        // intent.putExtra("pagelink",pagelink);
        this. startService(intent);
        a();
    }


    public void a(){
        System.out.println("dddd"+pagelink);
    }




}
