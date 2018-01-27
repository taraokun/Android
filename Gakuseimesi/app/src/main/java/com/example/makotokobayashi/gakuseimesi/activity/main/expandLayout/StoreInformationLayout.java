package com.example.makotokobayashi.gakuseimesi.activity.main.expandLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.makotokobayashi.gakuseimesi.R;

/**
 * Created by Sow12 on 2017/12/15.
 */

public class StoreInformationLayout extends LinearLayout {//custom_layout.xmlのクラス
    private ImageView shopImage;
    private TextView shopName;
    private TextView shopHour;
    private TextView editText;
    private TextView editText2;
    private TextView editText3;

    public void setShopImage(ImageView shopImage) {
        shopImage = shopImage;
        if(shopImage == null){
            this.shopImage.setImageResource(R.drawable.no_image);
        }else{

        }
    }

    public void setShopName(String string) {
        shopName.setText(string);
    }

    public void setShopHour(String string) {
        shopHour.setText(string);
    }

    public void setEditText(String string) {
        editText.setText(string);
    }

    public void setEditText2(String string) {
        editText2.setText(string);
    }

    public void setEditText3(String string) {
        editText3.setText(string);
    }

    public StoreInformationLayout(Context context, AttributeSet attr){
        super(context,attr);

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_layout, this);

        shopImage = (ImageView)layout.findViewById(R.id.shopImage);
        shopName = (TextView)layout.findViewById(R.id.shopName);
        shopHour = (TextView)layout.findViewById(R.id.shopHour);
        editText = (TextView)layout.findViewById(R.id.editText);
        editText2 = (TextView)layout.findViewById(R.id.editText2);
        editText3 = (TextView)layout.findViewById(R.id.editText3);
    }

}
