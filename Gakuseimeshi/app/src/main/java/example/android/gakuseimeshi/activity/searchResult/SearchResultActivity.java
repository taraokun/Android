package example.android.gakuseimeshi.activity.searchResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.database.basicData.MapSearch;
import example.android.gakuseimeshi.database.dao.ShopMapSearchDao;
import example.android.gakuseimeshi.database.dao.ShopMapViewDao;

/**
 * Created by Yusuke on 2018/02/09/.
 * 検索結果画面
 */

public class SearchResultActivity extends Activity implements View.OnFocusChangeListener, SearchView.OnQueryTextListener{

    private ShopMapSearchDao shopMapSearchDao;
    private ShopMapViewDao shopMapViewDao;
    private SearchView mSearchView;           // 検索窓
    private ArrayList<MapSearch> resultArrayList;
    private ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sheet_table);
        shopMapSearchDao = new ShopMapSearchDao(this);
        shopMapViewDao = new ShopMapViewDao(this);
        findViews();    // 各部品の結び付け
        // 検索窓を開いた状態にする(設定していない場合はアイコンをクリックしないと入力箇所が開かない)
        mSearchView.setIconified(false);
        // 検索窓のイベント処理
        mSearchView.setOnQueryTextListener(this);
        onQueryTextSubmit("");
    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {
        mSearchView = (SearchView)findViewById(R.id.searchView);   // 検索窓
        resultListView = (ListView)findViewById(R.id.resultList);
    }

    /**
     * SearchViewの各イベント処理
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        /* Do Nothing */
    }

    // 検索を始める時
    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();                 // 検索窓のフォーカスを外す(=キーボードを非表示)
        resultArrayList = (ArrayList<MapSearch>)getIntent().getSerializableExtra("Answers");
        SearchResultAdapter adapter = new SearchResultAdapter(this, resultArrayList, R.layout.custom_layout);
        resultListView.setAdapter(adapter);

        return false;
    }

    // 検索窓のテキストが変わった時
    @Override
    public boolean onQueryTextChange(String newText) {
        resultArrayList.clear();        // ListViewの項目を全て消す

        return false;
    }

}