package to.msn.wings.englishpracticeapp;

import android.content.Context;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static to.msn.wings.englishpracticeapp.R.id.txtResult;

public class SimpleDatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "english_word.sqlite";
    static final private int VERSION = 1;
    private Context mContext;


    public SimpleDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE english_word (" +
                "id INTEGER PRIMARY KEY, word TEXT, japanese1 TEXT, japanese2 TEXT, japanese3 TEXT, level INTEGER, type INTEGER)");

        List<RowData> json_list = new ArrayList<RowData>();
        try {

            Log.d("word", "aaaa");
            Resources res = mContext.getResources();
            Log.d("word", "aaaa");
            InputStream inputStream = res.openRawResource(R.raw.english_questions);
            Log.d("word", "aaaa");
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));
            String str = bufferedReader.readLine();

            // JSONObject に変換します
            JSONObject json = new JSONObject(str);

            JSONArray words = json.getJSONArray("english_word");

            Log.d("word", "aaaa");
            for(int i = 0; i < words.length(); i++){
                // (?)は配列番号
                RowData rowData = new RowData();
                JSONObject word = words.getJSONObject(i);
                rowData.id = word.getInt("id");
                rowData.word = word.getString("word");
                rowData.japanese1 = word.getString("japanese1");
                rowData.japanese2 = word.getString("japanese2");
                rowData.japanese3 = word.getString("japanese3");
                rowData.level = word.getInt("level");
                rowData.type = word.getInt("type");
                json_list.add(rowData);
            }
            Log.d("row", json_list.get(5).word);
            // JSONObject を文字列に変換してログ出力します
            Log.d(TAG, json.toString());
            //txtResult.setText(list.toString());

            db.beginTransaction();
            try {
                SQLiteStatement sql = db.compileStatement(
                        "INSERT INTO english_word(id,word,japanese1,japanese2,japanese3,level,type) VALUES(?, ?, ?, ?, ?, ?, ?)");
                for (int i = 0; i < json_list.size(); i++) {
                    sql.bindLong(1, json_list.get(i).id);
                    sql.bindString(2, json_list.get(i).word);
                    sql.bindString(3, json_list.get(i).japanese1);
                    sql.bindString(4, json_list.get(i).japanese2);
                    sql.bindString(5, json_list.get(i).japanese3);
                    sql.bindLong(6, json_list.get(i).level);
                    sql.bindLong(7, json_list.get(i).type);
                    sql.executeInsert();
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }

            inputStream.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }
}
