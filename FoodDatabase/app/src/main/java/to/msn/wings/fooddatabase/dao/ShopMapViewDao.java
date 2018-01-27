package to.msn.wings.fooddatabase.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import to.msn.wings.fooddatabase.basicdata.MapData;
import to.msn.wings.fooddatabase.databasehelper.SimpleDatabaseHelper;

/**
 * Created by Tomu on 2017/12/14.
 */

public class ShopMapViewDao {
    private static final String TABLE_NAME = "shopmapview";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NAMEKANA = "name_kana";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TEL = "tel";
    private static final String COLUMN_OPENTIME = "opentime";
    private static final String COLUMN_HOLIDAY = "holiday";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_FAVORITE = "favorite";
    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_NAMEKANA, COLUMN_ADDRESS, COLUMN_TEL, COLUMN_OPENTIME, COLUMN_HOLIDAY, COLUMN_IMAGE, COLUMN_FAVORITE};

    private SQLiteDatabase db = null;
    private SimpleDatabaseHelper helper = null;
    protected Context context;

    /*public ShopMapViewDao(SQLiteDatabase db){
        this.db = db;
    }*/
    public ShopMapViewDao(Context context){
        this.context = context;
        helper = new SimpleDatabaseHelper(this.context);
    }

    /**
     * DBの読み書き
     * openDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopMapViewDao openDB() {
        db = helper.getWritableDatabase();        // DBの読み書き
        return this;
    }

    /**
     * DBの読み込み 今回は未使用
     * readDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopMapViewDao readDB() {
        db = helper.getReadableDatabase();        // DBの読み込み
        return this;
    }

    /**
     * DBを閉じる
     * closeDB()
     */
    public void closeDB() {
        db.close();     // DBを閉じる
        db = null;
    }


    /**
     * 全データの取得   ----------------①
     * @return
     */
    public List<MapData> findAll(){
        List<MapData> mapDataList = new ArrayList<MapData>();
        Log.d("Error","dao1");
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                null,
                null,
                null,
                null,
                null);
        Log.d("Error",String.valueOf(cursor.getCount()));
        while(cursor.moveToNext()) {
            MapData mapData = new MapData();
            mapData.setId(cursor.getInt(0));
            mapData.setName(cursor.getString(1));
            mapData.setNameKana(cursor.getString(2));
            mapData.setAddress(cursor.getString(3));
            mapData.setTel(cursor.getString(4));
            mapData.setOpentime(cursor.getString(5));
            mapData.setHoliday(cursor.getString(6));
            mapData.setImage(cursor.getString(7));
            mapData.setFavorite(cursor.getInt(8));
            mapDataList.add(mapData);
        }
        return mapDataList;
    }


}
