package com.example.volmopc1.apitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent = new Intent(this, MyIntentService.class);
        this. startService(intent);*/
        getofferNews();
    }


    private void getofferNews() {
        String url5 = "http://hellohelps.com/HelloHelps/api_get.php";
        JsonObjectRequest jsObjRequest5 = new JsonObjectRequest(url5, null, new Response.Listener<JSONObject>() {
            JSONObject o;

            @Override
            public void onResponse(JSONObject response) {
                o = response;
                praseJsonNewsData9(o);
                // Toast.makeText(getActivity(), "hihihihi", Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.GONE);

               /* group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                snackbar.setText("looddingg   done");
                snackbar.dismiss();*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest5);
    }

    public void praseJsonNewsData9(JSONObject o) {
        JSONObject jj = o;
        JSONArray rr;
        JSONObject jjj;
        try {
            rr = jj.getJSONArray("articles");
            for (int i = 0; i < rr.length(); i++) {
                jjj = rr.getJSONObject(i);
                String pagelink = jjj.getString("image");
               // System.out.println(pagelink);
               // bean b=new bean();
               // b.setData(pagelink);
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("pagelink", pagelink);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
