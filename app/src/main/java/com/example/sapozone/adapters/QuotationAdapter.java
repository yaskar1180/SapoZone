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
import com.example.sapozone.data.shop.Quote;
import com.example.sapozone.data.shop.Shop;
import com.example.sapozone.viewHolders.QuotationHolder;
import com.example.sapozone.viewHolders.ShopHolder;

import java.util.List;

public class QuotationAdapter extends ArrayAdapter<Quote> {

    public QuotationAdapter(Context context, List<Quote> quotes
    ) {
        super(context, 0, quotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quote_row,parent, false);
        }

        QuotationHolder viewHolder = (QuotationHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new QuotationHolder();
            viewHolder.detail = (TextView) convertView.findViewById(R.id.detail);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.lead = (TextView) convertView.findViewById(R.id.lead);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Quote quote = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.detail.setText(quote.getDetail());
        viewHolder.price.setText(quote.getPrice()+"");
        viewHolder.lead.setText(quote.getLead()+"");


        return convertView;
    }

}
