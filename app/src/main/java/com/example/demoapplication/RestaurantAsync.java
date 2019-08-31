package com.example.demoapplication;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RestaurantAsync extends AsyncTask<URL, Void, String> {

    private Activity mActivity;
    private StringBuffer mBuffer = new StringBuffer();
    private static final String TAG = "RestaurantAsync";

    /**
     * コンストラクタ
     * @param activity
     */
    public RestaurantAsync(Activity activity) {
        mActivity = activity;
    }

    /**
     * 非同期処理の前処理
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 非同期処理
     * @param url
     * @return
     */
    @Override
    protected String doInBackground(URL... url) {
        HttpURLConnection con = null;
        URL urls = url[0];

        try {
            con = (HttpURLConnection) urls.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int resCd = con.getResponseCode();
            if (resCd != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP responseCode:" + resCd);
            }

            BufferedInputStream inputStream = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                mBuffer.append(line);
            }
            inputStream.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

//        Log.d(TAG, mBuffer.toString());
        return mBuffer.toString();
    }

    /**
     * 非同期処理の後処理
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            // JSONをパースして各飲食店の情報を取得する
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("shop");
            ArrayList<HotPepperGourmet> hotPepperGourmetArray = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                HotPepperGourmet hotPepperGourmet = new HotPepperGourmet();
                JSONObject json = jsonArray.getJSONObject(i);
                String id = json.getString("id"); // お店ID
                String name = json.getString("name"); // 店名
                String address = json.getString("address"); // 住所
                Double lat = json.getDouble("lat"); // 緯度
                Double lng = json.getDouble("lng"); // 経度
                String lunch = json.getString("lunch"); //ランチありなし
                String url = json.getJSONObject("urls").getString("pc"); // URL
                String gzUrl = json.getJSONObject("photo").getJSONObject("pc").getString("l");

//                Log.d(TAG, "お店ID:" + id);
//                Log.d(TAG, "店名:" + name);
//                Log.d(TAG, "住所:" + address);
//                Log.d(TAG, "緯度:" + lat.toString());
//                Log.d(TAG, "経度:" + lng.toString());
//                Log.d(TAG, "ランチありなし:" + lunch);
//                Log.d(TAG, "URL:" + url);
//                Log.d(TAG, "画像URL:" + gzUrl);

                hotPepperGourmet.setId(id);
                hotPepperGourmet.setName(name);
                hotPepperGourmet.setAddress(address);
                hotPepperGourmet.setLat(lat);
                hotPepperGourmet.setLng(lng);
                hotPepperGourmet.setLunch(lunch);
                hotPepperGourmet.setUrl(url);
                hotPepperGourmet.setGzUrl(gzUrl);

                hotPepperGourmetArray.add(hotPepperGourmet);
            }
            if ((mActivity instanceof MainActivity) && (mActivity instanceof ConfirmAsyncListener)) {
                ((MainActivity) mActivity).setHotPepperGourmetArray(hotPepperGourmetArray);
                ((ConfirmAsyncListener) mActivity).onRestaurantAsyncCallBack();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    interface ConfirmAsyncListener {
        void onRestaurantAsyncCallBack();
    }
}
