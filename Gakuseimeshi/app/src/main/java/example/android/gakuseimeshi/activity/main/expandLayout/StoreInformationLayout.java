package example.android.gakuseimeshi.activity.main.expandLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.Random;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.activity.storeInfomation.StoreInfomationActivity;
import example.android.gakuseimeshi.database.basicData.MapData;
import example.android.gakuseimeshi.database.basicData.MapSearch;
import example.android.gakuseimeshi.database.dao.ShopMapSearchDao;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;
import example.android.gakuseimeshi.gurunavi.ImageAsyncTask;

/**
 * Created by Sow12 on 2017/12/15.
 */

public class StoreInformationLayout extends LinearLayout {//custom_layout.xmlのクラス
    private ImageView shopImage;
    private View layout;
    private ImageView favorite;
    public TextView shopName;
    public TextView shopHour;


    public StoreInformationLayout(Context context, AttributeSet attr) {
        super(context, attr);

        layout = LayoutInflater.from(context).inflate(R.layout.custom_layout, this);
        shopImage = (ImageView) layout.findViewById(R.id.shopImage);
        shopName = (TextView) layout.findViewById(R.id.shopName);
        shopHour = (TextView) layout.findViewById(R.id.shopHour);
        LinearLayout button_liear = (LinearLayout) layout.findViewById(R.id.search_detail_button_liner);
        button_liear.setVisibility(View.VISIBLE);
        BootstrapButton random_button = findViewById(R.id.search_detail_button);
        random_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setRandomData();
            }
        });

        setRandomData();
    }

    public void setRandomData(){
        final MapData mapData = randomSearch();
        setShopImage(mapData.getImage());
        setShopName(mapData.getName());
        setShopHour(mapData.getAddress());

        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), StoreInfomationActivity.class);
                intent.putExtra("StoreId", mapData.getId());

                getContext().startActivity(intent);
            }
        });
    }



    public void setShopImage(String image_url) {
        shopImage.setTag(image_url);

        if (image_url.equals("{}")) {
            this.shopImage.setImageResource(R.drawable.no_image);
        } else {
            Uri uri = Uri.parse(image_url);
            Uri.Builder builder = uri.buildUpon();
            //shopImage.setTag(image_url);
            ImageAsyncTask task = new ImageAsyncTask(shopImage, null);
            task.execute(builder);
        }
    }

    public void setShopName(String string) {
        shopName.setText(string);
    }

    public void setShopHour(String string) {
        shopHour.setText(string);
    }

    public MapData randomSearch() {
        ShopMapViewDao shopMapViewDao = new ShopMapViewDao(getContext());
        shopMapViewDao.readDB();
        ArrayList<MapData> allSearchResult;
        allSearchResult = (ArrayList<MapData>)shopMapViewDao.findAll();
        shopMapViewDao.closeDB();

        long seed = System.currentTimeMillis(); // 現在時刻のミリ秒
        Random r = new Random(seed);
        int rand = r.nextInt();
        rand = rand < 0 ? rand * (-1) : rand;
        int random = rand % (allSearchResult.size() - 1);
        MapData mapData = allSearchResult.get(random);
        return mapData;
    }


}