package com.example.dell.myapplication;

import android.app.Activity;
import android.net.Network;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.dell.myapplication.Constants.FIRST_COLUMN;
import static com.example.dell.myapplication.Constants.FIRTH_COLUMN;
import static com.example.dell.myapplication.Constants.SECOND_COLUMN;
import static com.example.dell.myapplication.Constants.SEVEN_COLUMN;
import static com.example.dell.myapplication.Constants.SIX_COLUMN;
import static com.example.dell.myapplication.Constants.TWELVE_COLUMN;

/**
 * Created by Dell on 10/20/2016.
 */

    public class CustomListAdapter extends ArrayAdapter<HashMap<String, String>> {
        private final Activity context;
        private ArrayList<HashMap<String, String>> houseList;
    private ArrayList<HashMap<String, String>> ALLpictureList;




        public CustomListAdapter(Activity context, ArrayList<HashMap<String, String>> houseList, ArrayList<HashMap<String, String>> ALLpictureList) {
            super(context, R.layout.mylist, houseList);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.houseList=houseList;
            this.ALLpictureList=ALLpictureList;


        }








    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        TextView Locationtxt = (TextView) rowView.findViewById(R.id.textView2);
        TextView pricetxt = (TextView) rowView.findViewById(R.id.priceMtv);
        TextView datetxt = (TextView) rowView.findViewById(R.id.dateMtv);



        HashMap<String, String> map=houseList.get(position);

        txtTitle.setText(map.get(FIRST_COLUMN));
        Locationtxt.setText(map.get(SECOND_COLUMN));
        extratxt.setText(map.get(SEVEN_COLUMN));
        pricetxt.setText("K "+map.get(SIX_COLUMN));
        datetxt.setText(map.get(TWELVE_COLUMN));




        //SETTING AN IMAGE
        HashMap<String, String> map1=ALLpictureList.get(position);


        //get url
        String url = map1.get(SECOND_COLUMN);
        ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                android.R.drawable.ic_menu_camera, R.drawable
                        .h3));
        imageView.setImageUrl(url, imageLoader);


        return rowView;

    };

}
