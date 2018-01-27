package to.msn.wings.englishpracticeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectMode extends Activity {

    ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_mode);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);
/*        ArrayList<Map<String, String>> menu_list = new ArrayList<>();
        ArrayList<List<Map<String, String>>> level_list = new ArrayList<>();

        for (int i = 0; i < menu_title.length; i++) {
            HashMap<String, String> group = new HashMap<>();
            group.put("group_title", menu_title[i]);
            menu_list.add(group);
            ArrayList<Map<String, String>> childs = new ArrayList<>();
            for (int j = 0; j < level.length; j++) {
                HashMap<String, String> child = new HashMap<>();
                child.put("child_title", level[j]);
                childs.add(child);
            }
            level_list.add(childs);
        }

        adapter = new SimpleExpandableListAdapter(
                this,
                menu_list, android.R.layout.simple_expandable_list_item_1,
                new String[]{"group_title"},
                new int[]{android.R.id.text1},
                level_list, R.layout.list_sub,
                new String[]{"child_title"},
                new int[]{R.id.level}
        );*/


        adapter = new MyExpandableListAdapterInner();
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        Intent intent = new Intent(to.msn.wings.englishpracticeapp.SelectMode.this, to.msn.wings.englishpracticeapp.Question.class);
                        intent.putExtra("type",String.valueOf(groupPosition + 1));
                        intent.putExtra("level",String.valueOf(childPosition + 1));
                        startActivity(intent);
                        TextView txt = (TextView) v.findViewById(R.id.TextView01);
                        Log.d("Debug","groupPosition" + groupPosition);
                        Log.d("Debug","childPosition" + childPosition);
                        Log.d("Debug","id" + id);
                        Toast.makeText(SelectMode.this, txt.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );


    }





    public class MyExpandableListAdapterInner extends BaseExpandableListAdapter {

        // @formatter:off
        private final String[] groups = {"名詞", "動詞", "形容詞","副詞"};
        private final String[][][] children = {
                {
                        {"EASY", ""},
                        {"NORMAL", ""},
                        {"HARD", ""}
                },{
                        {"EASY", ""},
                        {"NORMAL", ""},
                        {"HARD", ""}
                },{
                        {"EASY", ""},
                        {"NORMAL", ""},
                        {"HARD", ""}
                },{
                        {"EASY", ""}
                }
        };
        // @formatter:on

        private LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition][0];
        }


        public String getChild(int groupPosition, int childPosition, int textPosition) {
            return children[groupPosition][childPosition][textPosition];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            // 子要素のViewを作る
            // convertViewがnullの時だけインフレートする（１行ごとに呼ばれるので使いまわす）
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.expandablechildview,
                        null);
            }

            // 子要素の現在位置が奇数の場合はグレー、偶数の場合は黒を背景にセットする
            LinearLayout linearLayout = (LinearLayout)
                    convertView.findViewById(R.id.ChildLinearLayout);
            if ((childPosition % 3) == 0) {
                linearLayout.setBackgroundColor(Color.argb(200,0,50,0));
            } else if ((childPosition % 3) == 1){
                linearLayout.setBackgroundColor(Color.argb(200,0,100,0));
            }else{
                linearLayout.setBackgroundColor(Color.argb(200,0,200,0));
            }

            TextView textView1 = (TextView) convertView.findViewById(R.id.TextView01);
            //TextView textView2 = (TextView) convertView.findViewById(R.id.TextView02);
            textView1.setText(getChild(groupPosition, childPosition, 0).toString());
           // textView2.setText(getChild(groupPosition, childPosition, 1).toString());

            return convertView;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            // グループのViewを作る処理
            // convertViewがnullの時だけインフレートする（１行ごとに呼ばれるので使いまわす）
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.expandablegroupview,
                        null);
            }

            // 子要素の現在位置が偶数の場合はグレー、奇数の場合は黒を背景にセットする
            LinearLayout linearLayout = (LinearLayout) convertView
                    .findViewById(R.id.GroupLinearLayout);
            /*if ((groupPosition % 2) == 0) {
                int menu_design = R.drawable.menu_design;
                linearLayout.setBackgroundResource(menu_design);
            } else {
                linearLayout.setBackgroundColor(Color.BLACK);
            }*/
            int menu_design = R.drawable.menu_design;
            linearLayout.setBackgroundResource(menu_design);

            TextView textView = (TextView) convertView.findViewById(R.id.GroupTextView01);
            textView.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                //戻るボタンが押された時の処理。
                Intent intent = new Intent(this, to.msn.wings.englishpracticeapp.MainActivity.class);
                this.startActivity(intent);
                this.finish();
                return true;
        }
        return false;
    }
    
}

