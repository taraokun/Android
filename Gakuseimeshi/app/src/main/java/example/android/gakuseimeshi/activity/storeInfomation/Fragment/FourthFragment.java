package example.android.gakuseimeshi.activity.storeInfomation.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.android.gakuseimeshi.R;

/**
 * Created by Taiki on 2017/12/04.
 */

public class FourthFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText("地図");
        return view;
    }
}
