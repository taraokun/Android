package to.msn.wings.fooddatabase.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import to.msn.wings.fooddatabase.basicdata.LocationInformation;
import to.msn.wings.fooddatabase.databasehelper.SimpleDatabaseHelper;

/**
 * Created by Tomu on 2017/12/19.
 */

public class ShopLocationDao {
    private static final String TABLE_NAME = "shoplocation";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String[] COLUMNS = {COLUMN_NAME, COLUMN_LATITUDE, COLUMN_LONGITUDE};

    private SQLiteDatabase db = null;
    private SimpleDatabaseHelper helper = null;
    protected Context context;

    /*public ShopMapViewDao(SQLiteDatabase db){
        this.db = db;
    }*/
    public ShopLocationDao(Context context){
        this.context = context;
        helper = new SimpleDatabaseHelper(this.context);
    }

    /**
     * DBの読み書き
     * openDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopLocationDao openDB() {
        db = helper.getWritableDatabase();        // DBの読み書き
        return this;
    }

    /**
     * DBの読み込み 今回は未使用
     * readDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopLocationDao readDB() {
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
    public List<LocationInformation> findAll(){
        List<LocationInformation> locationList = new ArrayList<LocationInformation>();
        Log.d("Error","lodao1");
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
            LocationInformation locationInformation = new LocationInformation();
            locationInformation.setName(cursor.getString(0));
            locationInformation.setLatitude(cursor.getFloat(1));
            locationInformation.setLongitude(cursor.getFloat(2));
            locationList.add(locationInformation);
        }
        return locationList;
    }
}
