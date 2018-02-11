package example.android.gakuseimeshi.activity.searchResult;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.database.basicData.MapData;
import example.android.gakuseimeshi.database.basicData.MapSearch;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;
import example.android.gakuseimeshi.gurunavi.ImageAsyncTask;

/**
 * Created by Yusuke on 2018/02/06.
 * 検索結果画面のリストビューのアダプター
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<MapSearch> researchResult = null;
    private int resource = 0;
    private Typeface shopNameFont;
    private Typeface shopHourFont;
    // 2018/02/07 中山 翔夢
    private List<MapData> ImageURLList = new ArrayList<MapData>();

    // コンストラクター(コンテキスト、データソース、レイアウトファイルを設定)
    public SearchResultAdapter(Context context,
                               ArrayList<MapSearch> researchResult, int resource) {
        this.context = context;
        this.researchResult = researchResult;
        this.resource = resource;

        ShopMapViewDao shopMapViewDao = new ShopMapViewDao(context);
        shopMapViewDao.readDB();
        Log.d("Error",String.valueOf(getCount()));
        for (int i = 0; i < getCount(); i++) {
            List<MapData> id = shopMapViewDao.searchId(researchResult.get(i).getId());
            Log.d("Error", String.valueOf(i) + String.valueOf(id.get(0).getId()));
            ImageURLList.add(id.get(0));
            Log.d("Error", String.valueOf(i) + ImageURLList.get(i).getImage());
        }
        shopMapViewDao.closeDB();

        shopNameFont = Typeface.createFromAsset(context.getAssets(),"ipaexm.ttf");
        shopHourFont = Typeface.createFromAsset(context.getAssets(),"GenEiAntiqueTN-M.ttf");
    }

    // データ項目の個数を取得
    @Override
    public int getCount() {
        return researchResult.size();
    }

    // 指定された項目を取得
    @Override
    public Object getItem(int index) {
        return researchResult.get(index);
    }

    // 指定された項目を識別するためのid値を取得
    @Override
    public long getItemId(int index){
        return researchResult.get(index).getId();
    }

    // リスト項目を表示するためのViewを取得
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        Activity activity = (Activity)context;
        MapSearch item = (MapSearch)getItem(index);
        if (convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        // 画像の設定 "{}"ならno_imageをセットし，URLなら画像を取得してセット
        if(ImageURLList.get(index).getImage().equals("{}")){
            ((ImageView)convertView.findViewById(R.id.shopImage)).setImageResource(R.drawable.no_image);
        } else {
            Uri uri = Uri.parse(ImageURLList.get(index).getImage());
            Uri.Builder builder = uri.buildUpon();
            ImageAsyncTask task = new ImageAsyncTask(((ImageView)convertView.findViewById(R.id.shopImage)));
            task.execute(builder);
        }
        ((TextView)convertView.findViewById(R.id.shopName)).setText(item.getName());
        ((TextView)convertView.findViewById(R.id.shopName)).setTypeface(shopNameFont);
        ((TextView)convertView.findViewById(R.id.shopHour)).setText(item.getAddress());
        ((TextView)convertView.findViewById(R.id.shopHour)).setTypeface(shopHourFont);

        return convertView;
    }
}