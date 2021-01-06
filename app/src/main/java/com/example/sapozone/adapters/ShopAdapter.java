package com.example.sapozone.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sapozone.R;
import com.example.sapozone.data.shop.Shop;
import com.example.sapozone.viewHolders.ShopHolder;

import java.util.List;

public class ShopAdapter extends ArrayAdapter<Shop> {

    public ShopAdapter(Context context, List<Shop> shops
    ) {
        super(context, 0, shops);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_row,parent, false);
        }

        ShopHolder viewHolder = (ShopHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ShopHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.postalCode = (TextView) convertView.findViewById(R.id.postalCode);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Shop shop = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(shop.getName());
        viewHolder.postalCode.setText(shop.getPostalCode()+"");
        viewHolder.logo.setImageDrawable(new ColorDrawable(shop.getLogo()));

        return convertView;
    }

}
