package example.android.gakuseimeshi.activity.storeInfomation.Fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.activity.storeInfomation.StoreInfomationActivity;
import example.android.gakuseimeshi.database.basicData.MapData;
import example.android.gakuseimeshi.database.basicData.ReviewData;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;
import example.android.gakuseimeshi.database.dao.ShopReviewDao;
import example.android.gakuseimeshi.gurunavi.ImageAsyncTask;

public class InformationFragment extends Fragment {
    private StoreInfomationActivity activity = new StoreInfomationActivity();
    private String genre;
    private String tel;
    private String address;
    private String access;
    private String opentime;
    private String holiday;
    private String imageURL = "{}";
    private Uri uri = Uri.parse("tel:" + tel);
    private LruCache<String, Bitmap> mMemoryCache;
    private int id;

    public InformationFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ((TextView)view.findViewById(R.id.name)).setText(activity.name);
        ((TextView)view.findViewById(R.id.genre)).setText(genre);
        ((TextView)view.findViewById(R.id.tel)).setText(tel);
        ((TextView)view.findViewById(R.id.address)).setText(address);
        ((TextView)view.findViewById(R.id.access)).setText(access);
        ((TextView)view.findViewById(R.id.opentime)).setText(opentime);
        ((TextView)view.findViewById(R.id.holiday)).setText(holiday);
        // 画像をWeb上から取得しひょじ
        ImageView imageView = (ImageView)    view.findViewById(R.id.storeImage);
        showImage(imageView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView tel = (TextView)getActivity().findViewById(R.id.tel);
        tel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(call);
            }
        });
    }

    /**
     * 画像の表示
     * @param imageView
     */
    public void showImage(ImageView imageView){
        Log.d("url", imageURL);
        if(!imageURL.equals("{}")) {
            connectImage(imageView);
        }else{
            Log.d("name", activity.name);
            ShopReviewDao shopReviewDao= new ShopReviewDao(getContext());
            shopReviewDao.readDB();
            StringBuilder name = new StringBuilder(activity.name);
            name.delete(name.length() - 1, name.length());
            activity.name = name.toString();
            List<ReviewData> reviewDatas = shopReviewDao.searchName(activity.name);
            shopReviewDao.closeDB();
            for(int i = 0; i < reviewDatas.size(); i++){
                Log.d("review", String.valueOf(reviewDatas.get(i).getName()));
                imageURL = reviewDatas.get(i).getImage();
                connectImage(imageView);
            }

            imageView.setImageResource(R.drawable.no_image);
        }
    }

    /**
     *
     */
    private void connectImage(ImageView imageView){
        final Bitmap bitmap = mMemoryCache.get(imageURL);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setTag(imageURL);
            Uri uri = Uri.parse(imageURL);
            Uri.Builder builder = uri.buildUpon();
            ImageAsyncTask task = new ImageAsyncTask(imageView, mMemoryCache);
            task.execute(builder);
        }
    }

    /**
     * 初期化
     */
    private void init(){
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        Log.d("idaaaa", String.valueOf(id));
        ShopMapViewDao shopMapViewDao = new ShopMapViewDao(getContext());
        shopMapViewDao.readDB();
        List<MapData> mapdata = shopMapViewDao.searchId(id);
        shopMapViewDao.closeDB();

        if(id != -1){
            Log.d("mapdata", String.valueOf(mapdata.get(0).getImage()));

            activity.name = mapdata.get(0).getName();
            genre = mapdata.get(0).getNameKana();
            tel = mapdata.get(0).getTel();
            address = mapdata.get(0).getAddress();
            access = "";
            opentime = mapdata.get(0).getOpentime();
            holiday = mapdata.get(0).getHoliiday();
            imageURL = mapdata.get(0).getImage();

            final int memClass = ((ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            final int cacheSize = 1024 * 1024 * memClass / 8;

            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount();
                }
            };
        }

    }
}
