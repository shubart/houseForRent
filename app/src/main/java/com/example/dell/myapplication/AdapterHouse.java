package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import static  com.example.dell.myapplication.Constants.FIRST_COLUMN;
import static  com.example.dell.myapplication.Constants.SECOND_COLUMN;
import static  com.example.dell.myapplication.Constants.THIRD_COLUMN;
import static  com.example.dell.myapplication.Constants.FOURTH_COLUMN;
import static  com.example.dell.myapplication.Constants.FIRTH_COLUMN;
import static  com.example.dell.myapplication.Constants.SIX_COLUMN;
import static  com.example.dell.myapplication.Constants.SEVEN_COLUMN;
import static  com.example.dell.myapplication.Constants.EIGHT_COLUMN;
import static com.example.dell.myapplication.Constants.TWELVE_COLUMN;


/**
 * Created by Dell on 10/23/2016.
 */

    public class AdapterHouse extends ArrayAdapter<HashMap<String, String>> {
    private final Activity context;
    private ArrayList<HashMap<String, String>> houseList;
    private ArrayList<HashMap<String, String>> pic_list;

    public AdapterHouse(Activity context, ArrayList<HashMap<String, String>> houseList, ArrayList<HashMap<String, String>> pic_list) {
        super(context, R.layout.tenant_search_list, houseList);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.houseList=houseList;
        this.pic_list=pic_list;

    }


    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.tenant_search_list, null, true);


        NetworkImageView imageView = (NetworkImageView ) rowView.findViewById(R.id.ivHouse);
        TextView hfor = (TextView) rowView.findViewById(R.id.hosfortv);
        TextView location = (TextView) rowView.findViewById(R.id.locaTv);
        TextView price = (TextView) rowView.findViewById(R.id.pricetv);
        TextView descrip = (TextView) rowView.findViewById(R.id.textType);
        TextView date = (TextView) rowView.findViewById(R.id. Tsl_datetv);


        HashMap<String, String> map=houseList.get(position);

        hfor.setText(map.get(FIRST_COLUMN));
        location.setText(map.get(SECOND_COLUMN));
        price.setText("K "+ map.get(SIX_COLUMN));
        descrip.setText(map.get(FIRTH_COLUMN));
        date.setText(map.get(TWELVE_COLUMN));

        //SETTING AN IMAGE

        //get url
        HashMap<String, String> map1=pic_list.get(position);
        String url = map1.get(SECOND_COLUMN);
        ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                android.R.drawable.ic_menu_camera, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(url, imageLoader);



        return rowView;

    };

}
