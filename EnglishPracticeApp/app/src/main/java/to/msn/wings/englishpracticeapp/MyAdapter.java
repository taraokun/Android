package to.msn.wings.englishpracticeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomu on 2017/10/10.
 */

public class MyAdapter extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Answers> answersList;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAnswersList(ArrayList<Answers> answersList) {
        this.answersList = answersList;
    }

    @Override
    public int getCount() {
        return answersList.size();
    }

    @Override
    public Object getItem(int position) {
        return answersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return answersList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.answerrow,parent,false);

        ((TextView)convertView.findViewById(R.id.name)).setText(answersList.get(position).getWord());
        //((TextView)convertView.findViewById(R.id.image)).setText(String.valueOf(answersList.get(position).getResult()));
        if(answersList.get(position).getResult()) {
            ((ImageView) convertView.findViewById(R.id.image)).setImageResource(R.drawable.mark_maru);
        }else{
            ((ImageView) convertView.findViewById(R.id.image)).setImageResource(R.drawable.mark_batsu);
        }

        return convertView;
    }


}
