package com.mizzio.geoquiz.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.mizzio.geoquiz.qeoquiz.answer_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.mizzio.geoquiz.qeoquiz.answer_shown";
    private static final String TAG = "CheatActivity";
    private static final String TEXT_ANSWER = "text_answer";
    private static final String REMEMBER_ANSWER = "remember_answer";
    private static final String COUNT_CHEAT = "count_cheats";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mRememberAnswer;
    private TextView mShowAPIDevice;
    private TextView mShowCountCheatsForUser;
    private int mCountCheats;


    public static Intent newIntent(Context packageContext, boolean answerIsTrue, int mCountCheats){
        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        intent.putExtra("count_cheats", mCountCheats);
        return intent;
    }

    public static  boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mCountCheats = getIntent().getIntExtra("count_cheats",0);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        String text = "";

        mShowCountCheatsForUser = findViewById(R.id.show_count_cheats);

        if (savedInstanceState != null){
            text = savedInstanceState.getString(TEXT_ANSWER);
            mRememberAnswer = savedInstanceState.getBoolean(REMEMBER_ANSWER);

            //mCountCheats = savedInstanceState.getInt(COUNT_CHEAT);
        }
        mAnswerTextView.setText(text);
        setShowAnswerResult(mRememberAnswer, mCountCheats);

        mShowCountCheatsForUser.setText("You are using: " + mCountCheats+" from 3 cheats");

        //mShowAPIDevice = findViewById(R.id.show_api_device);
        //mShowAPIDevice.setText("API Level "+String.valueOf(Build.VERSION.SDK_INT));

        mShowAnswerButton = findViewById(R.id.show_answer_button);

        if (mCountCheats >= 3){
            mShowAnswerButton.setEnabled(false);
        }
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mCountCheats += 1;
                //Toast.makeText(getApplicationContext(),"Осталось попыток: "+mCountCheats,Toast.LENGTH_LONG).show();
                mShowCountCheatsForUser.setText("You are using: " + mCountCheats+" from 3 cheats");
                mRememberAnswer = true;
                setShowAnswerResult(mRememberAnswer,mCountCheats);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();

                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private void setShowAnswerResult(boolean isAnswerShown, int countCheat){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        data.putExtra("count_cheats", countCheat);
        setResult(RESULT_OK,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onStart() called");
    }


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);

        TextView textAnswer = findViewById(R.id.answer_text_view);
        saveInstanceState.putString(TEXT_ANSWER,textAnswer.getText().toString());

        saveInstanceState.putBoolean(REMEMBER_ANSWER,mRememberAnswer);
        //saveInstanceState.putInt(COUNT_CHEAT,mCountCheats);

//        TextView showCheats = findViewById(R.id.show_count_cheats);
//        saveInstanceState.putString(SHOW_CHEATS,showCheats.getText().toString());
        Log.i(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle restoreInstanceState){
        super.onRestoreInstanceState(restoreInstanceState);
       //  mCountCheats = restoreInstanceState.getInt(COUNT_CHEAT);
       // TextView restoreCheats = findViewById(R.id.show_count_cheats);
        //restoreCheats.setText("U have "+ mCountCheats);
//        mShowCountCheats = restoreInstanceState.getString(SHOW_CHEATS);
//        TextView showCheats = findViewById(R.id.show_count_cheats);
//        showCheats.setText(mShowCountCheats);
        Log.i(TAG,"onRestoreInstanceState");
    }


    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
