package to.msn.wings.englishpracticeapp;

/**
 * Created by Tomu on 2017/09/25.
 */
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question extends Activity {

    private SimpleDatabaseHelper helper = null;
    private ProgressBar progressBar;
    private List<RowData> questionList; // 問題と答えのlist
    private ArrayList<Answers> answersList;
    private int current_id; // 現在のソートした問題のlistの番号
    private int count_answer; // 問題の答えの数
    private int questionNumber; // 問題数
    private int nextQuestionNumber; // すべての問題を出題するための番号Max4
    private double countPlayTime; // プレイ時間

    MyCountDownTimer myCountDownTimer;
    TextView txtResult;
    TextView questionCount;
    TextView question;
    TextView questionButton1;
    TextView questionButton2;
    TextView questionButton3;
    TextView questionButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qustion);

        init();

        helper = new SimpleDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        myCountDownTimer = new MyCountDownTimer(10000,100);
        myCountDownTimer.start();

        helper = new SimpleDatabaseHelper(this);

        // SelectModeのボタンから取り出す変数
        Intent intent = this.getIntent();
        String type = intent.getStringExtra("type");
        String level = intent.getStringExtra("level");
        //searchInfo("2");
        searchInfo(type,level);
    }

    /**
     * 初期化、初期宣言
     * */
    private void init(){
        count_answer = 1;
        current_id = 0;
        questionNumber = 0;
        nextQuestionNumber = 0;
        questionCount = (TextView)findViewById(R.id.question_count);
        question =  (TextView)findViewById(R.id.question_word);
        txtResult = (TextView)findViewById(R.id.txtResult);
        questionButton1 = (TextView) findViewById(R.id.button1);
        questionButton2 = (TextView) findViewById(R.id.button2);
        questionButton3 = (TextView) findViewById(R.id.button3);
        questionButton4 = (TextView) findViewById(R.id.button4);
        progressBar = (ProgressBar)findViewById(R.id.time_limit);
        progressBar.setMax(100);
    }


    /**
    * データベース検索
    * typeとlevelから問題のデータ取得
     *  @param type
     *  @param level
    */
    public void searchInfo(String type, String level){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {"id","word","japanese1","japanese2","japanese3","level","type"};
        String selection = "type = ? and level = ?";
        String[] selectionArgs = {type, level};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        questionList = new ArrayList<RowData>();
        //answers_word = new ArrayList<String>();
        //answers_result = new ArrayList<Boolean>();
        answersList = new ArrayList<Answers>();

        try {
            Cursor cursor = db.query("english_word", columns, selection, selectionArgs, groupBy, having, orderBy);
            StringBuilder text = new StringBuilder();
            while (cursor.moveToNext()){
                //String textColumn = cursor.getString(0);
                //int numColumn = cursor.getInt(1);
                RowData rowData = new RowData();
                rowData.id = cursor.getInt(0);
                rowData.word = cursor.getString(1);
                rowData.japanese1 = cursor.getString(2);
                rowData.japanese2 = cursor.getString(3);
                rowData.japanese3 = cursor.getString(4);
                rowData.level = cursor.getInt(5);
                rowData.type  = cursor.getInt(6);
                questionList.add(rowData);
                text.append(cursor.getString(1)).append("\n");
            }
            Collections.shuffle(questionList);
            setNextText();

            //txtResult.setText(questionList.get(0).word);
        } finally {
            db.close();
        }
    }




    /**
     * テキストの挿入
     */
    public void setNextText(){
        List<String> answers = new ArrayList<String>();

        answers.add(questionList.get(current_id).japanese1);
        if(questionList.get(current_id).japanese2.equals("")) {
            answers.add(questionList.get(current_id + 1).japanese1);
        }else{
            answers.add(questionList.get(current_id).japanese2);
            count_answer++;
        }
        if(questionList.get(current_id).japanese3.equals("")) {
            answers.add(questionList.get(current_id + 2).japanese1);
        }else{
            answers.add(questionList.get(current_id).japanese3);
            count_answer++;
        }
        answers.add(questionList.get(current_id + 3).japanese1);
        Collections.shuffle(answers);

        questionNumber++;
        if(questionNumber <= 10){
            questionCount.setText(questionNumber + "/10");
            // 結果の送信用の変数に格納（問題文）
            //answers_word.add(questionList.get(current_id).word);

            question.setText(questionList.get(current_id).word);
            questionButton1.setText(answers.get(0));
            questionButton2.setText(answers.get(1));
            questionButton3.setText(answers.get(2));
            questionButton4.setText(answers.get(3));
        }else{
            Log.d("DEBUG","OverQuestion");
            myCountDownTimer.cancel();
            Intent intent = new Intent(this, ResultActivity.class);
            //intent.putExtra("Question",answers_word);
            //intent.putExtra("Result",answers_result);
            intent.putExtra("Answers", answersList);
            this.startActivity(intent);
        }
    }



    /**
     *  回答選択時のアニメーション
     *  @param view
     *  */
    public void selectAnswer(View view){

        boolean falseFlag = false;

        if(count_answer > 0) {
            switch (view.getId()) {
                case R.id.button1:
                    questionButton1.setClickable(false);
                    animateAnimatorSetSample(questionButton1, 45, -1800,1000);
                    if(questionList.get(current_id).japanese1.equals(questionButton1.getText().toString())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese2.equals(questionButton1.getText().toString())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese3.equals(questionButton1.getText().toString())){
                        count_answer--;
                    }else{
                        falseFlag = true;
                    }
                    break;

                case R.id.button2:
                    questionButton2.setClickable(false);
                    animateAnimatorSetSample(questionButton2, 45, -1800,1000);
                    if(questionList.get(current_id).japanese1.equals(questionButton2.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese2.equals(questionButton2.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese3.equals(questionButton2.getText())){
                        count_answer--;
                    }else{
                        falseFlag = true;
                    }
                    break;

                case R.id.button3:
                    questionButton3.setClickable(false);
                    animateAnimatorSetSample(questionButton3, 45, -1800,1000);
                    if(questionList.get(current_id).japanese1.equals(questionButton3.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese2.equals(questionButton3.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese3.equals(questionButton3.getText())){
                        count_answer--;
                    }else{
                        falseFlag = true;
                    }
                    break;

                case R.id.button4:
                    questionButton4.setClickable(false);
                    animateAnimatorSetSample(questionButton4, 45, -1800,1000);
                    if(questionList.get(current_id).japanese1.equals(questionButton4.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese2.equals(questionButton4.getText())){
                        count_answer--;
                    }else if(questionList.get(current_id).japanese3.equals(questionButton4.getText())){
                        count_answer--;
                    }else{
                        falseFlag = true;
                    }
                    break;

            }
        }

        if(falseFlag){
            finishQuestion(false);
        }
        if(count_answer <= 0){
            finishQuestion(true);
        }
    }


    /*＊
     カウントダウンタイマー
      */
    public class MyCountDownTimer extends CountDownTimer {

        /* コンストラクタ
         * タイマーの初期値の代入 */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //textCounter.setText(String.valueOf(millisUntilFinished));
            int progress = (int) (millisUntilFinished/100);
            countPlayTime = 10.0 - ((double)millisUntilFinished/1000);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
            countPlayTime = 10.0;
            finishQuestion(false);
        }

    }


    /**
     *  問題終了時の処理
     *  @param result
     *  */
    private void finishQuestion(boolean result){
        Animation animation = AnimationUtils.loadAnimation(Question.this,R.anim.touch);
        final ImageView bad = (ImageView) findViewById(R.id.wrong_img);
        final ImageView good = (ImageView) findViewById(R.id.correct_img);
        final Handler handler = new Handler();
        final boolean resultAnswer = result;


        Answers answers = new Answers();
        answers.setWord(questionList.get(current_id).word);
        answers.setResult(resultAnswer);
        answers.setPlayTime(countPlayTime);

        // 問題の情報の更新と初期化
        current_id += 4;
        count_answer = 1;
        if(current_id >= questionList.size()){
            nextQuestionNumber++;
            current_id = nextQuestionNumber;
        }
        answersList.add(answers);

        if(resultAnswer) {
            good.setVisibility(View.VISIBLE);
            good.startAnimation(animation);
            myCountDownTimer.cancel();
        }else{
            bad.setVisibility(View.VISIBLE);
            bad.startAnimation(animation);
            myCountDownTimer.cancel();
        }

        myCountDownTimer.cancel();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(resultAnswer){
                    good.setVisibility(View.GONE);
                }else {
                    bad.setVisibility(View.GONE);
                }
                questionButton1.setClickable(true);
                questionButton2.setClickable(true);
                questionButton3.setClickable(true);
                questionButton4.setClickable(true);
                animateAnimatorSetSample(questionButton1, -45, 0,100);
                animateAnimatorSetSample(questionButton2, -45, 0,100);
                animateAnimatorSetSample(questionButton3, -45, 0,100);
                animateAnimatorSetSample(questionButton4, -45, 0,100);
                setNextText();
                myCountDownTimer.start();
            }
        }, 1000);
    }


    /**
     * 2秒かけてターゲットを表示した後に、2秒かけて引数に与えた角度と距離の位置に回転させながら移動させる
     *
     * @param target
     * @param degree
     * @param distance*/

    private void animateAnimatorSetSample(TextView target, float degree, float distance ,int time) {

        // AnimatorSetに渡すAnimatorのリストです
        List<Animator> animatorList= new ArrayList<Animator>();

       /* // alphaプロパティを0fから1fに変化させます
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat( target, "alpha", 0f, 1f );
        // 2秒かけて実行させます
        alphaAnimator.setDuration( 2000 );
        // リストに追加します
        animatorList.add( alphaAnimator );*/

        // 距離と半径から到達点となるX座標、Y座標を求めます
        float toX = (float) ( distance * Math.cos( Math.toRadians( degree ) ) );
        //float toY = (float) ( distance * Math.sin( Math.toRadians( degree ) ) );

        // translationXプロパティを0fからtoXに変化させます
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", 0f, toX );
        // translationYプロパティを0fからtoYに変化させます
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat( "translationY", 0f, 0f );
        // rotationプロパティを0fから360に変化させます
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat( "rotation", 0f, 360f );

        // targetに対してholderX, holderY, holderRotationを同時に実行します
        ObjectAnimator translationXYAnimator =
                ObjectAnimator.ofPropertyValuesHolder( target, holderX, holderY, holderRotaion );
        // 2秒かけて実行させます
        translationXYAnimator.setDuration( time );
        // リストに追加します
        animatorList.add( translationXYAnimator );

        final AnimatorSet animatorSet = new AnimatorSet();
        // リストのAnimatorを順番に実行します
        animatorSet.playSequentially( animatorList );

        // アニメーションを開始します
        animatorSet.start();
    }


    /**
     * 戻るボタンが押されたときの処理
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(this, "Back button!" , Toast.LENGTH_SHORT).show();
                this.finish();
                return true;
        }
        return false;
    }


    /**
     * 戻るボタンなど押した場合タイマーを止める
     */
    public void onPause(){
        super.onPause();
        myCountDownTimer.cancel();
    }


}
