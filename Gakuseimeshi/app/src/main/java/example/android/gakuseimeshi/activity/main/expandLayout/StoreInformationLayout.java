package example.android.gakuseimeshi.activity.main.expandLayout;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.gurunavi.ImageAsyncTask;

/**
 * Created by Sow12 on 2017/12/15.
 */

public class StoreInformationLayout extends LinearLayout {//custom_layout.xmlのクラス
    private ImageView shopImage;
    public TextView shopName;
    public TextView shopHour;

    public void setShopImage(String image_url) {
        if(image_url.isEmpty()){
            this.shopImage.setImageResource(R.drawable.no_image);
        }else{
            Uri uri = Uri.parse(image_url);
            Uri.Builder builder = uri.buildUpon();
            ImageAsyncTask task = new ImageAsyncTask(shopImage);
            task.execute(builder);
        }
    }

    public void setShopName(String string) {shopName.setText(string);}

    public void setShopHour(String string) {
        shopHour.setText(string);
    }

    public StoreInformationLayout(Context context, AttributeSet attr){
        super(context,attr);

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_layout, this);

        shopImage = (ImageView)layout.findViewById(R.id.shopImage);
        shopName = (TextView)layout.findViewById(R.id.shopName);
        shopHour = (TextView)layout.findViewById(R.id.shopHour);
    }


}
