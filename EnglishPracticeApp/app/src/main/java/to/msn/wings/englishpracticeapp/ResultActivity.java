package to.msn.wings.englishpracticeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Tomu on 2017/10/10.
 */

public class ResultActivity extends Activity{
    ListView listView;
    TextView resultText;
    TextView countTimerText;
    TextView scoreText;
    Button backTitleButton;
    Button backQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        init();

        ArrayList<Answers> list = new ArrayList<>();
        MyAdapter myAdapter = new MyAdapter(this);

        myAdapter.setAnswersList(list);
        listView.setAdapter(myAdapter);

        Intent intent = getIntent();
        ArrayList<Answers> answers = (ArrayList<Answers>) intent.getSerializableExtra("Answers");

        int countScore = 0;
        double countTime = 0.0;
        for(int i = 0; i < answers.size(); i++){
            Answers answer = new Answers();
            answer.setWord(answers.get(i).getWord());
            answer.setResult(answers.get(i).getResult());
            list.add(answer);
            myAdapter.notifyDataSetChanged();
            countTime += answers.get(i).getPlayTime();
            if(answers.get(i).getResult()){
                countScore += 10;
            }
        }

        BigDecimal time = new BigDecimal(countTime);
        time = time.setScale(1,BigDecimal.ROUND_DOWN);
        resultText.setText(countScore/10 + "/" + answers.size());
        countTimerText.setText(time+"秒");
        scoreText.setText(countScore + "点");
    }

    /*
    * 初期化、初期宣言用
     */
    private void init(){
        listView = (ListView) findViewById(R.id.listView);
        resultText = (TextView)findViewById(R.id.correct_count);
        countTimerText = (TextView)findViewById(R.id.count_timer);
        scoreText = (TextView)findViewById(R.id.question_word);
        backTitleButton = (Button)findViewById(R.id.back_title);
        backQuestionButton = (Button)findViewById(R.id.back_question);

        backTitleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(to.msn.wings.englishpracticeapp.ResultActivity.this, to.msn.wings.englishpracticeapp.MainActivity.class);
                startActivity(intent);
            }
        });

        backQuestionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(to.msn.wings.englishpracticeapp.ResultActivity.this, to.msn.wings.englishpracticeapp.SelectMode.class);
                startActivity(intent);
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                //戻るボタンが押された時の処理。
                Intent intent = new Intent(this, to.msn.wings.englishpracticeapp.SelectMode.class);
                this.startActivity(intent);
                this.finish();
                return true;
        }
        return false;
    }

}
