package to.msn.wings.fooddatabase.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;

import to.msn.wings.fooddatabase.basicdata.MapSearch;
import to.msn.wings.fooddatabase.databasehelper.SimpleDatabaseHelper;

/**
 * Created by Tomu on 2017/12/20.
 */

public class ShopMapSearchDao {
    private static final String TABLE_NAME = "shopsearch";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NAMEKANA = "name_kana";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_OPENTIME = "opentime";
    private static final String COLUMN_HOLIDAY = "holiday";
    private static final String COLUMN_CATEGORYNAME1 = "category_name1";
    private static final String COLUMN_CATEGORYNAME2 = "category_name2";
    private static final String COLUMN_BUDGET = "budget";
    private static final String COLUMN_STUDENTDISCOUNT = "student_discount";
    private static final String COLUMN_STUDENTDISCOUNTINFO = "student_discount_info";
    private static final String COLUMN_FAVORITE = "favorite";
    private static final String COLUMN_SEARCHTIME1 = "search_time1";
    private static final String COLUMN_SEARCHTIME2 = "search_time2";
    private static final String COLUMN_SEARCHTIME3 = "search_time3";
    private static final String COLUMN_SEARCHHOLIDAYDAY = "search_holiday_day";
    private static final String COLUMN_SEARCHHOLIDAYMONTH = "search_holiday_month";

    /*private static final String[] COLUMNS = {COLUMN_NAME, COLUMN_NAMEKANA, COLUMN_ADDRESS, COLUMN_OPENTIME, COLUMN_HOLIDAY, COLUMN_CATEGORYNAME1,
            COLUMN_CATEGORYNAME2, COLUMN_BUDGET, COLUMN_STUDENTDISCOUNT, COLUMN_STUDENTDISCOUNTINFO, COLUMN_FAVORITE};*/
    private static final String[] COLUMNS = {COLUMN_NAME, COLUMN_NAMEKANA, COLUMN_ADDRESS, COLUMN_OPENTIME, COLUMN_HOLIDAY, COLUMN_CATEGORYNAME1,
            COLUMN_CATEGORYNAME2, COLUMN_BUDGET, COLUMN_STUDENTDISCOUNT, COLUMN_STUDENTDISCOUNTINFO, COLUMN_FAVORITE, COLUMN_SEARCHTIME1,
            COLUMN_SEARCHTIME2, COLUMN_SEARCHTIME3, COLUMN_SEARCHHOLIDAYDAY, COLUMN_SEARCHHOLIDAYMONTH};

    private SQLiteDatabase db = null;
    private SimpleDatabaseHelper helper = null;
    protected Context context;

    public ShopMapSearchDao(Context context){
        this.context = context;
        Log.d("Error","aaaaa");
        helper = new SimpleDatabaseHelper(this.context);
        Log.d("Error","bbbbb");
    }

    /**
     * DBの読み書き
     * openDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopMapSearchDao openDB() {
        db = helper.getWritableDatabase();        // DBの読み書き
        return this;
    }

    /**
     * DBの読み込み 今回は未使用
     * readDB()
     *
     * @return this 自身のオブジェクト
     */
    public ShopMapSearchDao readDB() {
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
    public List<MapSearch> findAll(){
        Log.d("Error","dao1");
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                null,
                null,
                null,
                null,
                null);
        return getDatabaseData(cursor);
    }

    /**
     * 名前データから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> nameSearch(String name){

        String whereText = COLUMN_NAME + " LIKE ? or " + COLUMN_NAMEKANA + " LIKE ?";
        String[] names = {"%" + name + "%", "%" + name + "%"};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                names,
                null,
                null,
                null);
        return getDatabaseData(cursor);
    }

    /**
     * カテゴリーデータから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> categorySearch(String category){

        String whereText = COLUMN_CATEGORYNAME1 + " LIKE ? or " + COLUMN_CATEGORYNAME2 + " LIKE ?";
        String[] categorys = {"%" + category + "%", "%" + category + "%"};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                categorys,
                null,
                null,
                null);
        return getDatabaseData(cursor);
    }

    /**
     * 平均金額データから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> budgetSearch(int min, int max){

        String whereText = COLUMN_BUDGET + " >= ? and " + COLUMN_BUDGET + " <= ?";
        String[] budgets = {String.valueOf(min) , String.valueOf(max)};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                budgets,
                null,
                null,
                null);
        return getDatabaseData(cursor);
    }

    /**
     * 住所データから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> addressSearch(String address){

        String whereText = COLUMN_ADDRESS + " LIKE ?";
        String[] addresses = {"%" + address + "%"};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                addresses,
                null,
                null,
                null);

        return getDatabaseData(cursor);
    }

    /**
     * データから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> businessHoursSearch(){
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int day_of_week_in_month = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        Log.d("Error", String.valueOf(month));
        Log.d("Error", String.valueOf(day));
        Log.d("Error", String.valueOf(day_of_week_in_month));

        String whereText = COLUMN_SEARCHTIME1 + " LIKE ? or " +
                           COLUMN_SEARCHTIME2 + " LIKE ? or " +
                           COLUMN_SEARCHTIME3 + " LIKE ?";
        String[] day_of_weeks = {"%" + String.valueOf(day_of_week) + "%", "%" + String.valueOf(day_of_week) + "%", "%" + String.valueOf(day_of_week) + "%"};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                day_of_weeks,
                null,
                null,
                null);

        List<MapSearch> mapSearchList = new ArrayList<MapSearch>();
        Log.d("Error", String.valueOf(cursor.getCount()));

        while(cursor.moveToNext()) {
            boolean flag  = false;
            Log.d("Error", cursor.getString(11));
            if (!cursor.getString(11).equals("{}")) {

                String[] search_time1 = cursor.getString(11).split(",", 0);
                if ((cursor.getString(11).matches(".*" + String.valueOf(day_of_week) + ".*") && compareTime(search_time1[1], search_time1[2], hour, minute)) ||
                        (cursor.getString(11).matches(".*" + String.valueOf(day_of_week - 1) + ".*") && compareTime(search_time1[1], search_time1[2], hour+24, minute))) {
                    flag = true;
                }
            }

            if (!cursor.getString(12).equals("{}")) {
                String[] search_time2 = cursor.getString(12).split(",", 0);
                if ((cursor.getString(12).matches(".*" + String.valueOf(day_of_week) + ".*") && compareTime(search_time2[1], search_time2[2], hour, minute)) ||
                        (cursor.getString(12).matches(".*" + String.valueOf(day_of_week - 1) + ".*") && compareTime(search_time2[1], search_time2[2], hour+24, minute))) {
                    flag = true;
                }
            }

            if (!cursor.getString(13).equals("{}")) {
                String[] search_time3 = cursor.getString(13).split(",", 0);
                if ((cursor.getString(13).matches(".*" + String.valueOf(day_of_week) + ".*") && compareTime(search_time3[1], search_time3[2], hour, minute)) ||
                        (cursor.getString(13).matches(".*" + String.valueOf(day_of_week - 1) + ".*") && compareTime(search_time3[1], search_time3[2], hour+24, minute))) {
                    flag = true;
                }
            }

            if(!cursor.getString(14).equals("{}")){
                String[] search_holiday_days = cursor.getString(14).split(",",0);
                Log.d("Error", search_holiday_days[0]);
                for(String search_holiday_day0: search_holiday_days){
                    String[] search_holiday_day = search_holiday_day0.split("-",0);
                    if(month == Integer.parseInt(search_holiday_day[0]) && day == Integer.parseInt(search_holiday_day[1])){
                        flag = false;
                    }
                }
            }

            if(!cursor.getString(15).equals("{}")){
                String[] search_holiday_month = cursor.getString(15).split(",",0);
                Log.d("Error", search_holiday_month[0]);
                if(day_of_week_in_month == Integer.parseInt(search_holiday_month[0]) && day_of_week == Integer.parseInt(search_holiday_month[1])){
                    flag = false;
                }
            }

            if(flag){
                MapSearch mapSearch = new MapSearch();
                mapSearch.setName(cursor.getString(0));
                mapSearch.setNameKana(cursor.getString(1));
                mapSearch.setAddress(cursor.getString(2));
                mapSearch.setOpentime(cursor.getString(3));
                mapSearch.setHoliday(cursor.getString(4));
                mapSearch.setCategoryName1(cursor.getString(5));
                mapSearch.setCategoryName2(cursor.getString(6));
                mapSearch.setBudget(cursor.getInt(7));
                mapSearch.setStudentDiscount(cursor.getInt(8));
                mapSearch.setStudentDiscountInfo(cursor.getString(9));
                mapSearch.setFavorite(cursor.getInt(10));
                mapSearch.setSearchTime1(cursor.getString(11));
                mapSearch.setSearchTime2(cursor.getString(12));
                mapSearch.setSearchTime3(cursor.getString(13));
                mapSearch.setSearchHolidayDay(cursor.getString(14));
                mapSearch.setSearchHolidayMonth(cursor.getString(15));
                mapSearchList.add(mapSearch);
            }
        }

        return mapSearchList;
    }

    /**
     * データから取得
     * 精密検索
     * @return
     */
    public List<MapSearch> studentDiscountSearch(int student_discount){

        String whereText = COLUMN_STUDENTDISCOUNT + " = ?";
        String[] student_discounts = {String.valueOf(student_discount)};
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                whereText,
                student_discounts,
                null,
                null,
                null);
        return getDatabaseData(cursor);
    }

    /**
     *
     * @param open_time
     * @param close_time
     * @param hour
     * @param minute
     * @return
     */
    private boolean compareTime(String open_time, String close_time, int hour, int minute){
        String[] open_string = open_time.split("-",0);
        String[] close_string = close_time.split("-",0);
        int now_time_minute = hour * 60 + minute;
        int open_int = Integer.parseInt(open_string[0]) * 60 + Integer.parseInt(open_string[1]);
        int close_int = Integer.parseInt(close_string[0]) * 60 + Integer.parseInt(close_string[1]);
        if (open_int < now_time_minute && now_time_minute< close_int){
            return true;
        } else {
            return false;
        }
    }

    /**
     *  データベースのデータ取得用
     *  @return
     */
    private List<MapSearch> getDatabaseData(Cursor cursor){

        List<MapSearch> mapSearchList = new ArrayList<MapSearch>();
        while(cursor.moveToNext()) {
            MapSearch mapSearch = new MapSearch();
            mapSearch.setName(cursor.getString(0));
            mapSearch.setNameKana(cursor.getString(1));
            mapSearch.setAddress(cursor.getString(2));
            mapSearch.setOpentime(cursor.getString(3));
            mapSearch.setHoliday(cursor.getString(4));
            mapSearch.setCategoryName1(cursor.getString(5));
            mapSearch.setCategoryName2(cursor.getString(6));
            mapSearch.setBudget(cursor.getInt(7));
            mapSearch.setStudentDiscount(cursor.getInt(8));
            mapSearch.setStudentDiscountInfo(cursor.getString(9));
            mapSearch.setFavorite(cursor.getInt(10));
            mapSearch.setSearchTime1(cursor.getString(11));
            mapSearch.setSearchTime2(cursor.getString(12));
            mapSearch.setSearchTime3(cursor.getString(13));
            mapSearch.setSearchHolidayDay(cursor.getString(14));
            mapSearch.setSearchHolidayMonth(cursor.getString(15));
            mapSearchList.add(mapSearch);
        }
        return mapSearchList;
    }

}
