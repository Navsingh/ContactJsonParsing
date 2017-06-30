package com.example.android.contactjsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private ListView lv1;
    private static String url = "http://api.androidhive.info/contacts/";
    ArrayList<HashMap<String, String>> clist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = (ListView)findViewById(R.id.lv_contacts);
        clist = new ArrayList<>();
        new  Get_contact().execute();

    }
    public class Get_contact extends AsyncTask<Void,Void,Void>
    {
       @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void  doInBackground(Void... args0)
        {
            URL builturl = GetJsonFromUrl.buildurl(url);
            String jsondata = null;
            //clist = new ArrayList<>();
            try
            {
                jsondata = GetJsonFromUrl.getData(builturl);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
                   if (jsondata != null) {
                       System.out.print(jsondata);
                try
                {
                    JSONObject obj = new JSONObject(jsondata);
                    JSONArray arr = obj.getJSONArray("contacts");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject c = arr.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String gender = c.getString("gender");
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        HashMap<String, String> cntct = new HashMap<>();
                        cntct.put("id", id);
                        cntct.put("name", name);
                        cntct.put("email", email);
                       // cntct.put("gender", gender);
                       // cntct.put("mobile", mobile);
                        clist.add(cntct);

                    }
                   // System.out.print("Size"+clist.size());

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
            else
                   {
                       System.out.print("nodata");
                   }
                 return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
           super.onPostExecute(result);
            System.out.print("Size"+clist.size());
              //  System.out.print("Size is "+ clist.size());

            ListAdapter la = new SimpleAdapter(MainActivity.this,
                    clist,R.layout.listview,
                    new String[]{"name","email","id"},
                    new int[]{R.id.tv1,R.id.email,R.id.id});
            lv1.setAdapter(la);
        }
    }

}
