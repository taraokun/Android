package example.android.gakuseimeshi.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import example.android.gakuseimeshi.R;

public class AreaListActivity extends AppCompatActivity {
    private ListView areaListView;
    private Intent intent;
    private static final String[] areaContents = {
            "未選択", "野々市","有松","押野","金沢"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);
        areaListView = (ListView) findViewById(R.id.area_list);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, areaContents);
        areaListView.setAdapter(arrayAdapter);

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListView areaListView = (ListView) parent;
                intent = new Intent(AreaListActivity.this, MainActivity.class);
                if(id == 0) intent.putExtra("AREA_CONTENT", "");
                else intent.putExtra("AREA_CONTENT", (String)areaListView.getItemAtPosition(position));


                intent.putExtra("AREA", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}