package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by q on 2016-12-28.
 */



public class tab1contacts extends Fragment{
    ArrayList<HotelData> datas= new ArrayList<HotelData>();
    ListView listview;


    // JSONObject hotel = new JSONObject(json);


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("hotel.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    void doJSONParser()  {

   /*    / String st2 =
                "[{ 'name' : 'hotel' , 'number' : '123123' },"+
                        "{'name' : 'hotel', 'number' : '123123' }]";*/
        try {
            JSONArray obj = new JSONArray( loadJSONFromAsset() );
           /* String str = obj.toString();*/

           // JSONObject obj = new JSONObject(loadJSONFromAsset());
           // JSONArray jarray = new JSONArray(obj);   /////////// 여기를 고쳐야 함
            //jarray = loadJSONFromAsset();

            for (int i = 0; i < obj.length(); i++) {

               JSONObject jObject = obj.getJSONObject(i);
                String name = jObject.getString("name");
                String number = jObject.getString("number");
                datas.add(new HotelData(name,number));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayAdapter<String> Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1contacts, null);


        // data procesing ////
       doJSONParser();

        listview = (ListView) rootView.findViewById(R.id.list);

//        ArrayList<String> arGeneral = new ArrayList<String>();
//        arGeneral.add("abc");
//        arGeneral.add("ced");
//
//        ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arGeneral);
//
//
//        ListView list = (ListView) rootView.findViewById(R.id.list);
//        list.setAdapter(Adapter);
        HotelDataAdapter adapter = new HotelDataAdapter( getLayoutInflater(null) , datas);
        listview.setAdapter(adapter);
        return rootView;
    }



}
