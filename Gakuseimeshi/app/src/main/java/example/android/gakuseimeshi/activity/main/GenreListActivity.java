package example.android.gakuseimeshi.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import example.android.gakuseimeshi.R;


public class GenreListActivity extends AppCompatActivity {
    private static final String genreListItems[] = {"和食", "洋食", "イタリアン", "フレンチ", "中華"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        ListView genreList = (ListView)findViewById(R.id.genre_list);

        ArrayAdapter<String> genreArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genreListItems);
        genreList.setAdapter(genreArrayAdapter);

        genreList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ListView genreListView = (ListView)parent;
                String item = (String)genreListView.getItemAtPosition(pos);
            }
        });
    }
}
