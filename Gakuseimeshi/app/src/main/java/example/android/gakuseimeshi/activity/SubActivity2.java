package example.android.gakuseimeshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.database.basicData.MapData;
import example.android.gakuseimeshi.database.basicData.MapSearch;
import example.android.gakuseimeshi.database.dao.ShopMapSearchDao;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;
import example.android.gakuseimeshi.database.databaseHelper.SimpleDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * デバッグ用画面
 * Created by Tomu on 2017/12/19.
 */

public class SubActivity2 extends Activity implements View.OnFocusChangeListener, SearchView.OnQueryTextListener{

    private ShopMapSearchDao shopMapSearchDao;
    private ShopMapViewDao shopMapViewDao;
    private SimpleDatabaseHelper helper = null;
    private SearchView mSearchView03;           // 検索窓
    private TableLayout mTableLayout03List;     //データ表示用TableLayout
    private int colorFlg = 1;                   //背景切り替え用フラグ
    private ArrayList<MapSearch> resultArrayList;

    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int GCH = Gravity.CENTER_HORIZONTAL;
    private final static int GE = Gravity.END;         // Gravity.RIGHTでもよい

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sheet_table);
        shopMapSearchDao = new ShopMapSearchDao(this);
        shopMapViewDao = new ShopMapViewDao(this);
        init();
        findViews();        // 各部品の結び付け
        // 検索窓を開いた状態にする(設定していない場合はアイコンをクリックしないと入力箇所が開かない)
        mSearchView03.setIconified(false);
        // 検索窓のイベント処理
        mSearchView03.setOnQueryTextListener(this);
        showResult();
    }

    private void init(){
        Intent intent = getIntent();
        resultArrayList = (ArrayList<MapSearch>) intent.getSerializableExtra("Answers");
    }

    public void showResult(){
        mSearchView03.clearFocus();                 // 検索窓のフォーカスを外す(=キーボードを非表示)

        TableRow rowHeader = new TableRow(this);    // 行を作成
        rowHeader.setPadding(16, 12, 16, 12);       // 行のパディングを指定(左, 上, 右, 下)

        // ヘッダー：産地
        TextView headerMadeIn = setTextItem("名前", GCH);            // TextViewのカスタマイズ処理
        TableRow.LayoutParams paramsMadeIn = setParams(0.4f);       // LayoutParamsのカスタマイズ処理
        // ヘッダー：個数
        TextView headerNumber = setTextItem("カテゴリ1", GCH);
        TableRow.LayoutParams paramsNumber = setParams(0.3f);
        // ヘッダー：単価
        TextView headerImage = setTextItem("カテゴリ2", GCH);
        TableRow.LayoutParams paramsImage = setParams(0.3f);
        // rowHeaderにヘッダータイトルを追加
        rowHeader.addView(headerMadeIn, paramsMadeIn);          // ヘッダー：産地
        rowHeader.addView(headerNumber, paramsNumber);          // ヘッダー：個数
        rowHeader.addView(headerImage, paramsImage);            // ヘッダー：単価
        rowHeader.setBackgroundResource(R.drawable.row_deco1);  // 背景

        // TableLayoutにrowHeaderを追加
        mTableLayout03List.addView(rowHeader);

        for(MapSearch search_data: resultArrayList) {
            TableRow row = new TableRow(this);          // 行を作成
            row.setPadding(16, 12, 16, 12);             // 行のパディングを指定(左, 上, 右, 下)

            // 産地
            TextView textMadeIn = setTextItem(search_data.getName(), GCH);     // TextViewのカスタマイズ処理
            // 個数
            TextView textNumber = setTextItem(String.valueOf(search_data.getAddress()), GE);      // TextViewのカスタマイズ処理
            // 単価
            TextView textPrice = setTextItem(String.valueOf(search_data.getId()), GE);      // TextViewのカスタマイズ処理
            //TextView textPrice = setTextItem(String.valueOf(search_data.getFavorite()), GE);      // TextViewのカスタマイズ処理

            // rowHeaderに各項目(DBから取得した産地,個数,単価)を追加
            row.addView(textMadeIn, paramsMadeIn);      // 産地
            row.addView(textNumber, paramsNumber);      // 個数
            row.addView(textPrice, paramsImage);        // 単価

            mTableLayout03List.addView(row);            // TableLayoutにrowHeaderを追加

            // 交互に行の背景を変える
            if (colorFlg % 2 != 0) {
                row.setBackgroundResource(R.drawable.row_deco2);
            } else {
                row.setBackgroundResource(R.drawable.row_deco1);
            }
            colorFlg++;
        }
    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {
        mSearchView03 = (SearchView) findViewById(R.id.searchView03);               // 検索窓
        mTableLayout03List = (TableLayout) findViewById(R.id.tableLayout03List);    //データ表示用TableLayout
    }

    /**
     * SearchViewの各イベント処理
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    // 検索を始める時
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Error",query);
        //dbAdapter.readDB();                         // DBの読み込み(読み込みの方)
        shopMapViewDao.readDB();

        mSearchView03.clearFocus();                 // 検索窓のフォーカスを外す(=キーボードを非表示)

        TableRow rowHeader = new TableRow(this);    // 行を作成
        rowHeader.setPadding(16, 12, 16, 12);       // 行のパディングを指定(左, 上, 右, 下)

        // ヘッダー：産地
        TextView headerMadeIn = setTextItem("名前", GCH);            // TextViewのカスタマイズ処理
        TableRow.LayoutParams paramsMadeIn = setParams(0.4f);       // LayoutParamsのカスタマイズ処理
        // ヘッダー：個数
        TextView headerNumber = setTextItem("画像", GCH);
        TableRow.LayoutParams paramsNumber = setParams(0.3f);
        // ヘッダー：単価
        TextView headerImage = setTextItem("カテゴリ2", GCH);
        TableRow.LayoutParams paramsImage = setParams(0.3f);
        // rowHeaderにヘッダータイトルを追加
        rowHeader.addView(headerMadeIn, paramsMadeIn);          // ヘッダー：産地
        rowHeader.addView(headerNumber, paramsNumber);          // ヘッダー：個数
        rowHeader.addView(headerImage, paramsImage);            // ヘッダー：単価
        rowHeader.setBackgroundResource(R.drawable.row_deco1);  // 背景

        // TableLayoutにrowHeaderを追加
        mTableLayout03List.addView(rowHeader);

        // String column = "product";          //検索対象のカラム名
        //String[] name = {query};            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        //Cursor c = dbAdapter.searchDB(null, column, name);
        List<MapData> all_search_list;
        //all_search_list = shopMapSearchDao.findAll();
        //all_search_list = shopMapSearchDao.nameSearch(query);
        //all_search_list = shopMapSearchDao.categorySearch(query);
        //all_search_list = shopMapSearchDao.budgetSearch(Integer.parseInt(query),10000);
        //all_search_list = shopMapSearchDao.addressSearch(query);
        //all_search_list = shopMapSearchDao.businessHoursSearch();
        //all_search_list = shopMapSearchDao.studentDiscountSearch(0);

        all_search_list = shopMapViewDao.findAll();
        for(MapData search_data: all_search_list) {
            TableRow row = new TableRow(this);          // 行を作成
            row.setPadding(16, 12, 16, 12);             // 行のパディングを指定(左, 上, 右, 下)

            // 産地
            TextView textMadeIn = setTextItem(search_data.getName(), GCH);     // TextViewのカスタマイズ処理
            // 個数
            TextView textNumber = setTextItem(String.valueOf(search_data.getImage()), GE);      // TextViewのカスタマイズ処理
            // 単価
            TextView textPrice = setTextItem(String.valueOf(search_data.getTel()), GE);      // TextViewのカスタマイズ処理
            //TextView textPrice = setTextItem(String.valueOf(search_data.getFavorite()), GE);      // TextViewのカスタマイズ処理

            // rowHeaderに各項目(DBから取得した産地,個数,単価)を追加
            row.addView(textMadeIn, paramsMadeIn);      // 産地
            row.addView(textNumber, paramsNumber);      // 個数
            row.addView(textPrice, paramsImage);        // 単価

            mTableLayout03List.addView(row);            // TableLayoutにrowHeaderを追加

            // 交互に行の背景を変える
            if (colorFlg % 2 != 0) {
                row.setBackgroundResource(R.drawable.row_deco2);
            } else {
                row.setBackgroundResource(R.drawable.row_deco1);
            }
            colorFlg++;
        }

        shopMapViewDao.closeDB();

        return false;
    }

    // 検索窓のテキストが変わった時
    @Override
    public boolean onQueryTextChange(String newText) {

        mTableLayout03List.removeAllViews();        // TableLayoutのViewを全て消す

        return false;
    }

    /**
     * 行の各項目のTextViewカスタマイズ処理
     * setTextItem()
     *
     * @param str     String
     * @param gravity int
     * @return title TextView タイトル
     */
    private TextView setTextItem(String str, int gravity) {
        TextView title = new TextView(this);
        title.setTextSize(16.0f);           // テキストサイズ
        title.setTextColor(Color.BLACK);    // テキストカラー
        title.setGravity(gravity);          // テキストのGravity
        title.setText(str);                 // テキストのセット

        return title;
    }

    /**
     * 行の各項目のLayoutParamsカスタマイズ処理
     * setParams()
     */
    private TableRow.LayoutParams setParams(float f) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, WC);
        params.weight = f;      //weight(行内でのテキストごとの比率)

        return params;
    }
}
