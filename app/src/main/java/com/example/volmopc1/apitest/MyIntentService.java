package com.example.volmopc1.apitest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;

public class MyIntentService extends IntentService {
    String pagelink;
    ArrayList al;
    bean b;


    public MyIntentService() {
        super("MyIntentService");


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //getofferNews();
        // System.out.println(pagelink);
        // Bundle b=getIntent().getExtras();
        System.out.println(Main2Activity.pagelink);
        try {
            ttt(Main2Activity.pagelink);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                pagelink = jjj.getString("image");
                displayLink(pagelink);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void displayLink(String pagelink) {
        //System.out.println(this.pagelink);
        bean b = new bean();
        b.setData(pagelink);
//        al.add(pagelink);
    }


    public static void ttt(String url) throws IOException {

        Document document = Jsoup.connect(url).get();
        Elements links = document.select("body");
        int i = 1;
        HashMap<Integer, String> hs = new HashMap();
        Map<String, Integer> result = new TreeMap<String, Integer>();
        HashSet<String> set2 = new HashSet<>();
        HashSet<String> set3 = new HashSet<>();

        //ADDING TOTAL ATTRIBUTE IN HASHMAP
        for (Element e : links) {
            Elements at = e.getAllElements();
            for (Element e1 : at) {
                Attributes at1 = e1.attributes();
                String ff = at1.toString();
                if (ff != null && !ff.trim().isEmpty() && !ff.equals("")) {
                    hs.put(i, ff);
                }
                i++;
            }
        }

        //GETTING HOWMANY SAME ATTRIBUTES IN THE DOCUMENT
        for (Map.Entry m : hs.entrySet()) {
            String value = (String) m.getValue();
            Integer count = result.get(value);
            //  System.out.println(value+count);
            if (count == null)
                result.put(value, new Integer(1));
            else
                result.put(value, new Integer(count + 1));

        }


        result.values().removeAll(Collections.singleton(1));
        result.values().removeAll(Collections.singleton(2));
        result.values().removeAll(Collections.singleton(3));
        result.values().removeAll(Collections.singleton(4));
        result.values().removeAll(Collections.singleton(5));


        for (Map.Entry m1 : result.entrySet()) {
            // int occurance = (int) m1.getValue();
            String attribute = (String) m1.getKey();
            Elements ell1 = document.getElementsByAttributeValue(attributename(attribute), attributevalue(attribute));//.....PASSING ATTRIBUTE AND ATTRIBUTE VALUE  TO GET ALL THE ELEMENTS
            for (Element so : ell1) {
                if (isSiblingsAttributeValueSame(so) == true && hasChild(so) == true && hasAHref(so) == true && hasImgSrc(so) == true && so.hasText() == true) {
                    set2.add(attribute);
                }
            }
        }
        Map obj=new HashMap();
        String jsonText = null;
        System.out.println(set2);
        System.out.println(set3);
        for (String ss : set2) {
            String attname1 = attributename(ss);
            String attvalue1 = attributevalue(ss);
            Elements ell1 = document.getElementsByAttributeValue(attname1, attvalue1);
            for (Element el1 : ell1) {

              //  Element content = el1.getElementById("content");
                Elements links1 = el1.getElementsByTag("a");
                for (Element link : links1) {
                    String linkHref = link.attr("abs:href");
                    System.out.println(linkHref);
                    String linkText = link.text();
                    System.out.println(linkText);

                    obj.put("link",linkHref);
                    obj.put("text",linkText);
                    jsonText = JSONValue.toJSONString(obj);
                    System.out.print(jsonText);
                }
            }
        }



    }


   /* public void volleeyy_z_set_news_ent(final String linkHref1, final String sourcename, final String title, final String imagelink, final String body, String uploadurl) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PAGELINK, linkHref1);
                params.put(KEY_SOURCENAME, sourcename);
                params.put(KEY_TITLE, title);
                params.put(KEY_IMAGELINK, imagelink);
                params.put(KEY_BODY, body);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

*/
    public static boolean hasChild(Element e) throws IOException {
        boolean b;
        ArrayList al = new ArrayList();

        Elements children = e.children();
        for (Element child : children) {
            al.add(child);
            // System.out.println(child);
            // System.out.println("+++++++");


        }


        if (al.isEmpty() == true) {
            b = false;
            // System.out.println("empty");
        } else {
            b = true;
            // System.out.println("not empty");
        }

        //System.out.println(b);
        return b;


    }

    public static String attributename(String s) {
        String subString1 = null;
        String subString = "noo";
        int iend = s.indexOf("=");
        if (iend > 0) {
            subString = s.substring(0, iend);
        }
        if (!subString.equals("") && !subString.trim().isEmpty() && !subString.equals(null)) {
            subString1 = subString;
        } else {
            subString1 = "noo";
        }
        // System.out.println(subString1.length());
        return subString1;
    }

    public static String attributevalue(String s) {
        String attvalue = "noAttributeValue";
        String attvalue1 = "noAttributeValue";
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(s);
        if (m.find()) {
            attvalue = m.group(1);
        }
        if (!attvalue.equals("") && !attvalue.trim().isEmpty() && !attvalue.equals(null)) {
            attvalue1 = attvalue;
        }
        return attvalue1;
    }

    public static boolean hasAHref(Element e) {
        ArrayList l = new ArrayList();
        Boolean b;
        Elements links1 = e.getElementsByTag("a");
        for (Element link : links1) {
            String linkHref = link.attr("abs:href");
            //System.out.println(linkHref);
            l.add(linkHref);

        }
        if (l.isEmpty() == true) {
            b = false;
        } else {
            b = true;
        }
        return b;
    }

    public static boolean hasImgSrc(Element e) {
        ArrayList l = new ArrayList();
        Boolean b;
        Elements links1 = e.getElementsByTag("img");
        for (Element link : links1) {
            String linkHref = link.attr("src");
            //System.out.println(linkHref);
            l.add(linkHref);

        }
        if (l.isEmpty() == true) {
            b = false;
        } else {
            b = true;
        }
        return b;
    }

    public static int noOfSiblingElements(Element e) {
        int i;
        ArrayList al = new ArrayList();
        Elements all = e.siblingElements();
        for (Element e1 : all) {
            al.add(e1);
        }
        i = al.size();
        return i;
    }

    public static boolean isSiblingsAttributeValueSame(Element e) {
        boolean b = false;
        String k2 = "noo";
        Attributes attributes = e.attributes();//...GETTING THE ATTRIBUTES OF ELEMENT
        String ff = attributes.toString();

        Elements siblingElements = e.siblingElements();
        for (Element e5 : siblingElements) {
            Attributes at2 = e5.attributes();//getting the attributes of sibling
            String ff1 = at2.toString();
            if (ff.equals(k2)) {
                // System.out.println("yes");
                b = true;

                break;
            } else {
                // System.out.println("no");
                b = false;
            }
            k2 = ff1;

        }

        return b;
    }

}

   /* public void vvv() {
        Iterator itr = al.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }*/



