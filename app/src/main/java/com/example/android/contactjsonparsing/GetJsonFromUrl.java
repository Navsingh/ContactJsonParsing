package com.example.android.contactjsonparsing;

import android.net.Uri;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Waheguru on 6/29/2017.
 */

public final class GetJsonFromUrl
{

    public static  URL buildurl(String url1)
    {
       // Uri url12 = Uri.parse(url1).buildUpon()
          //      .build();
        URL url = null;
        try
        {
            url = new URL(url1.toString());

        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static String getData(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = null;
        try
        {
            InputStream in = urlConnection.getInputStream();
           /* Scanner sc = new Scanner(in);
            sc.useDelimiter("\\n");
            boolean hasinput = sc.hasNext();
            if (hasinput) {
                return sc.next();
            } else {
                return null;
            }*/
           response = convertStreamToString(in);

        } finally {
            urlConnection.disconnect();
        }
        return  response;
    }
    private static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
try
{
while ((line = reader.readLine()) != null)
  {
sb.append(line).append('\n');
}
}
catch (IOException e)
{
e.printStackTrace();
}
    finally
    {
try
    {
is.close();
}
    catch (IOException e)
    {
e.printStackTrace();
}
}
return sb.toString();
}
}
