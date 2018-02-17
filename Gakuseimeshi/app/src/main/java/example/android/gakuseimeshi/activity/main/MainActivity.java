package example.android.gakuseimeshi.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.activity.searchResult.SearchResultActivity;
import example.android.gakuseimeshi.activity.main.expandLayout.ResizeAnimation;
import example.android.gakuseimeshi.activity.main.expandLayout.StoreInformationLayout;
import example.android.gakuseimeshi.activity.storeInfomation.StoreInfomationActivity;
import example.android.gakuseimeshi.database.basicData.MapData;
import example.android.gakuseimeshi.database.basicData.MapSearch;
import example.android.gakuseimeshi.database.dao.ShopMapSearchDao;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;
import example.android.gakuseimeshi.gurunavi.UploadAsyncTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button detailedButton;
    private CheckBox openCheck;
    private CheckBox studentDiscountCheck;
    private LinearLayout detailedContentsAreaLinear;
    private Spinner areaSpinner; //エリア選択スピナー
    private EditText Mealname;
    private EditText maxPrice;
    private EditText minPrice;
    private TextView categoryText;
    private StoreInformationLayout storeInformationLayout;
    private Intent genre;
    private String areaSpinnerItems[] = {"野々市", "有松","押野", "金沢"};
    private final static int DURATION = 200;
    //2018/1/22 追加 ナカヤマ
    private ShopMapSearchDao shopMapSearchDao;
    //2018/1/24 追加 ナカヤマ
    private SharedPreferences sharedPref;
    private UploadAsyncTask uploadAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
        areaSpinnerAdapter();
        customLayoutSet();

        UploadDatabase();
    }


    /**
     * データベースの更新
     */
    public void UploadDatabase(){
        shopMapSearchDao = new ShopMapSearchDao(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int before_entry_year = sharedPref.getInt("before_entry_year", 0);
        int before_entry_month = sharedPref.getInt("before_entry_month", 0);
        int before_entry_day = sharedPref.getInt("before_entry_day", 0);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        Log.d("Error", String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));
        Log.d("Error", before_entry_year + "/" + before_entry_month + "/" + before_entry_day);
        if((year > before_entry_year) || (year == before_entry_year && month > before_entry_month)
                || (year == before_entry_year && month == before_entry_month && day > before_entry_day)){
            uploadAsyncTask = new UploadAsyncTask(this,this);
            uploadAsyncTask.execute();
            //searchKitFoodArea = new SearchKitFoodArea(this);
            //searchKitFoodArea.execute();
            //uploadAsyncTask.cancel(true);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("before_entry_year", year);
            editor.putInt("before_entry_month", month);
            editor.putInt("before_entry_day", day);
            editor.commit();
        }
    }

    //ボタン処理
    @Override
    public void onClick(View v){
        if (v != null) {
            switch (v.getId()) { //検索
                case R.id.search:
                    Mealname.selectAll();
                    String mealName = Mealname.getText().toString();
                    if (!TextUtils.isEmpty(mealName)){
                        shopMapSearchDao.readDB();
                        List<MapSearch> allSearchList = new ArrayList<MapSearch>();
                        allSearchList = shopMapSearchDao.nameSearch(mealName);
                        shopMapSearchDao.closeDB();
                        ArrayList<MapSearch> allSearchArrayList = (ArrayList<MapSearch>)allSearchList;
                        //Intent intent = new Intent(this, SearchResultActivity.class);
                        Intent intent = new Intent(this, StoreInfomationActivity.class);
                        intent.putExtra("Answers", allSearchArrayList);
                        this.startActivity(intent);
                        Toast.makeText(this, mealName, Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.genre_button: //ジャンル選択ボタン
                    genre = new Intent(MainActivity.this, GenreListActivity.class);
                    startActivity(genre);
                    break;

                case R.id.search_detail_button: //詳細検索ボタン
                    Intent intent = new Intent(this, SearchResultActivity.class);

                    int min = minPrice.getText().toString().isEmpty() ? 0 : Integer.parseInt(minPrice.getText().toString());
                    int max = maxPrice.getText().toString().isEmpty() ? 99999 : Integer.parseInt(maxPrice.getText().toString());
                    int isOpen = openCheck.isChecked() ? 1 : 0;
                    int existsStudentDiscount = studentDiscountCheck.isChecked() ? 1 : 0;

                    intent.putExtra("Category", categoryText.getText().toString());
                    intent.putExtra("MinPrice", min);
                    intent.putExtra("MaxPrice", max);
                    intent.putExtra("Area", (String)areaSpinner.getSelectedItem());
                    intent.putExtra("OpenTime", isOpen);
                    intent.putExtra("StudentDiscount", existsStudentDiscount);
                    /*shopMapSearchDao.readDB();
                    List<MapSearch> allSearchList = new ArrayList<MapSearch>();
                    allSearchList = shopMapSearchDao.detailedSearch(category, min, max, area, open, studentDiscount);
                    shopMapSearchDao.closeDB();
                    ArrayList<MapSearch> allSearchArrayList = (ArrayList<MapSearch>)allSearchList;
                    intent.putExtra("Answers", allSearchArrayList);*/
                    this.startActivity(intent);
                    break;
            }
        }
    }

    //登録
    private void findViews() {
        Mealname = (EditText) findViewById(R.id.search_meal);
        minPrice = (EditText)findViewById(R.id.min_price);
        maxPrice = (EditText)findViewById(R.id.max_price);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.genre_button).setOnClickListener(this);
        findViewById(R.id.search_detail_button).setOnClickListener(this);
        categoryText = (TextView)findViewById(R.id.genre_button);
        detailedContentsAreaLinear = (LinearLayout)findViewById(R.id.detailed_contents_area_linear);
        detailedButton = (Button)findViewById(R.id.detailed_button);
        areaSpinner = (Spinner)findViewById(R.id.Area_spinner);
        openCheck = (CheckBox)findViewById(R.id.open_check);
        studentDiscountCheck = (CheckBox)findViewById(R.id.student_discout_check);
        storeInformationLayout = (StoreInformationLayout)findViewById(R.id.custom_layout1);
    }

    //初期化
    private void init() {
    }

    //スピナー用アダプター
    private void areaSpinnerAdapter(){
        ArrayAdapter<String> area_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areaSpinnerItems);
        area_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(area_adapter);
    }

    //回転ボタン
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // ExpandするViewの元のサイズを保持
        final int originalHeight = 552;
        final int btnWidth = detailedButton.getWidth();
        final int btnHeight = detailedButton.getLineHeight();
        //展開ボタン押下時
        detailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailedContentsAreaLinear.getHeight() > 0) {
                    detailedButton.setBackgroundResource(R.drawable.up);
                    startRotateAnim(-180, btnWidth/2, btnHeight/2);
                    // 内容エリアを閉じるアニメーション
                    ResizeAnimation closeAnimation = new ResizeAnimation(detailedContentsAreaLinear, -originalHeight, originalHeight);
                    closeAnimation.setDuration(DURATION);
                    detailedContentsAreaLinear.startAnimation(closeAnimation);
                } else {
                    detailedButton.setBackgroundResource(R.drawable.down);
                    // 展開ボタンのアニメーション(時計周りに180度回転)
                    startRotateAnim(180, btnWidth / 2, btnHeight / 2);
                    // 内容エリアを開くアニメーション
                    ResizeAnimation openAnimation = new ResizeAnimation(detailedContentsAreaLinear, originalHeight, 0);
                    openAnimation.setDuration(DURATION);    // アニメーションにかける時間(ミリ秒)
                    detailedContentsAreaLinear.startAnimation(openAnimation);   // アニメーション開始
                }
            }
        });
    }

    private void startRotateAnim(int toDegrees, int pivotX, int pivotY) {
        // 展開ボタンの180度回転アニメーションを生成
        RotateAnimation rotate = new RotateAnimation(0, toDegrees, pivotX, pivotY);
        rotate.setDuration(DURATION);       // アニメーションにかける時間(ミリ秒)
        rotate.setFillAfter(true);          // アニメーション表示後の状態を保持
        detailedButton.startAnimation(rotate);    // アニメーション開始
    }

    //カスタムレイアウトデバッグ用
    private void customLayoutSet(){
        storeInformationLayout.setShopImage("https://uds.gnst.jp/rest/img/kkb1p5160000/t_001f.jpg");
        storeInformationLayout.setShopName("カレーのチャンピオン");
        storeInformationLayout.setShopHour("距離：100m  徒歩：3分");
        storeInformationLayout.shopName.setTypeface(Typeface.createFromAsset(getAssets(),"ipaexm.ttf"));
        storeInformationLayout.shopHour.setTypeface(Typeface.createFromAsset(getAssets(),"GenEiAntiqueTN-M.ttf"));
    }
}