package example.android.gakuseimeshi.activity.storeInfomation.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.activity.storeInfomation.StoreInfomationActivity;

public class FirstFragment extends Fragment {
    private StoreInfomationActivity activity = new StoreInfomationActivity();
    String genre = "学生食堂";
    String phone_number = "076-248-3464";
    String Street_address = "扇が丘キャンパス 21号館";

    public FirstFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ((TextView)view.findViewById(R.id.text_view)).setText(activity.storeName);
        ((TextView)view.findViewById(R.id.text_view)).setTextColor(Color.argb(255,0,0,0));
        ((TextView)view.findViewById(R.id.text_view2)).setText(genre);
        ((TextView)view.findViewById(R.id.text_view3)).setText(phone_number);
        ((TextView)view.findViewById(R.id.text_view4)).setText(Street_address);

        ImageView imageView = (ImageView)    view.findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.no_image);
        return view;
    }
}
