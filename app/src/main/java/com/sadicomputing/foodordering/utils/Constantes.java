package com.sadicomputing.foodordering.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.sadicomputing.foodordering.R;
import com.squareup.picasso.Picasso;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by modykane on 14/12/2017.
 */

public class Constantes {

    private static final String ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SERVER_BASE_URL = "http://10.0.2.2/FoodOrdering/images/uploads/";
    public static final String URL_REGISTER_DEVICE = "http://172.20.10.2/FoodOrdering/RegisterDevice.php";
    public static final String URL_SEND_SINGLE_PUSH = "http://172.20.10.2/FoodOrdering/sendSinglePush.php";
    public static final String URL_SEND_MULTIPLE_PUSH = "http://172.20.10.2/FoodOrdering/sendMultiplePush.php";
    public static final String URL_FETCH_DEVICES = "http://172.20.10.2/FoodOrdering/GetRegisteredDevices.php";

    private static SecureRandom rnd = new SecureRandom();

    public static String randomString(){
        int length = 10;
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++)
            sb.append(NUMERIC.charAt(rnd.nextInt(NUMERIC.length())));
        return sb.toString();
    }

    public static String formatDate(String strDate){
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // EEE (E) d MMM yyyy HH:mm:ss => Monday (Mon) 01 Jan 2017 00:00:00
        Timestamp stamp = new Timestamp(Long.parseLong(strDate));
        return sf.format(new Date(stamp.getTime()));
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView){
        if (imageUrl.contains(SERVER_BASE_URL))
            Picasso.with(context).load(imageUrl).into(imageView);
        else
            Picasso.with(context).load(SERVER_BASE_URL+imageUrl).placeholder(R.drawable.icons8_restaurant_menu).into(imageView);
    }

}
