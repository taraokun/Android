package example.android.gakuseimeshi.activity.storeInfomation.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import example.android.gakuseimeshi.R;
import example.android.gakuseimeshi.activity.storeInfomation.StoreInfomationActivity;

public class InformationFragment extends Fragment {
    private StoreInfomationActivity activity = new StoreInfomationActivity();
    String genre = "学生食堂";
    String tel = "0123456789";
    String address = "扇が丘キャンパス 21号館";
    String access = "工大前駅から徒歩10分";
    String opentime = "7:00～19:00";
    String holiday = "日曜日";
    Uri uri = Uri.parse("tel:" + tel);

    public InformationFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ((TextView)view.findViewById(R.id.name)).setText(activity.name);
        ((TextView)view.findViewById(R.id.genre)).setText(genre);
        ((TextView)view.findViewById(R.id.tel)).setText(tel);
        ((TextView)view.findViewById(R.id.address)).setText(address);
        ((TextView)view.findViewById(R.id.access)).setText(access);
        ((TextView)view.findViewById(R.id.opentime)).setText(opentime);
        ((TextView)view.findViewById(R.id.holiday)).setText(holiday);

        ImageView imageView = (ImageView)    view.findViewById(R.id.storeImage);
        imageView.setImageResource(R.drawable.no_image);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView tel = (TextView)getActivity().findViewById(R.id.tel);
        tel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(call);
            }
        });
    }
}
