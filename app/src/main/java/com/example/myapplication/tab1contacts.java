package com.example.myapplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.widget.AdapterView;
import android.database.Cursor;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import static android.os.Build.ID;
import static com.example.myapplication.R.id.list;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by q on 2016-12-28.
 */



public class tab1contacts extends Fragment{
    ArrayList<HotelData> datas= new ArrayList<HotelData>();
    ListView listview;

    /*public class Contact{
        String PhotoId;
        String name;
        String PhoneNum;

        public Contact(){}

        public void setPhotoId(String PhotoId){
            this.PhotoId=PhotoId;
        }
    }

    public class Android_Contact{
        public String android_contact_Name = "";
        public String android_contact_TelefonNr="";
        public int android_contact_ID=0 ;
    }

    public ArrayList fp_get_Android_Contacts() {
        ArrayList<Android_Contact> arrayList_Android_Contacts = new ArrayList<Android_Contact>();

        Cursor cursor_Android_Contacts = null;
        Cursor phoneCursor = null;
        ContentResolver contentResolver = getContext().getContentResolver();
        try {
            cursor_Android_Contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            phoneCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"
                    , new String[]{contact_id}
                    , null);
        } catch (Exception ex) {
            Log.e("Error on contact", ex.getMessage());
        }
        if (cursor_Android_Contacts.getCount() > 0 && phoneCursor.getCount()>0 ) {
            while (cursor_Android_Contacts.moveToNext()) {
                String contact_id = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
                String contact_display_name = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                android_contact.android_contact_Name = contact_display_name;

                int hasPhoneNumber = Integer.parseInt(cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contracts.HAS_PHONE_NUMBER)));

                arrayList_Android_Contacts.add(android_contact);
            }
        }
        return arrayList_Android_Contacts;
    }*/



    // JSONObject hotel = new JSONObject(json);


    public class AddrBean{
        String name;
        String number;
        public void setName(String name){
            this.name = name;
        }
        public void setNumber(String number){
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }
    }


    public ArrayList getAddr(){
        Cursor cursor = null;
        ArrayList list = null;
        AddrBean bean = null;
        try {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String phoneName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
            String [] ad = new String[] {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            cursor = getContext().getContentResolver().query(uri, ad, null, null, phoneName);
            cursor.moveToFirst();
            while(cursor.moveToNext())
            {if(cursor.getString(1) !=null)
            {
                if(list.size()==0)
                {
                    bean = new AddrBean();
                    bean.setName(cursor.getString(0));
                    bean.setNumber(cursor.getString(1));
                    list.add(bean);
                }
                else{
                    if( list.size() != cursor.getCount())
                    {
                        bean = new AddrBean();
                        bean.setName(cursor.getString(0));
                        bean.setNumber(cursor.getString(1));
                        list.add(bean);
                    }
                }
            }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }
    return list;
    }

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

        listview = (ListView) rootView.findViewById(list);

//        ArrayList<String> arGeneral = new ArrayList<String>();
//        arGeneral.add("abc");
//        arGeneral.add("ced");
//
//        ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arGeneral);
//
//
//        ListView list = (ListView) rootView.findViewById(R.id.list);
//        list.setAdapter(Adapter);
        HotelDataAdapter adapter = new HotelDataAdapter( getLayoutInflater(null) , datas );
        /*PhoneNumberAdapter adapter = new PhoneNumberAdapter (getLayoutInflater(null), getAddr());*/

        listview.setAdapter(adapter);
        return rootView;
    }



}
