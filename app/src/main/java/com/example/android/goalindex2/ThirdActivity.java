package com.example.android.goalindex2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

import static android.os.DropBoxManager.EXTRA_TIME;
import static android.provider.AlarmClock.EXTRA_MINUTES;

public class ThirdActivity extends AppCompatActivity {

    private static final String EXTRA_FIRST_TEAM_SCORE = "FIRST_TEAM_SCORE" ;
    private static final String EXTRA_SECOND_TEAM_SCORE = "SECOND_TEAM_SCORE";
    private static final String EXTRA_RED_FIRST_TEAM = "FIRST_TEAM";
    private static final String EXTRA_RED_SECOND_TEAM = "SECOND_TEAM";
    private static final String EXTRA_YELLOW_FIRST_TEAM = "YELLOW_FIRST_TEAM";
    private static final String EXTRA_YELLOW_SECOND_TEAM = "YELLOW_SECOND_TEAM";
    private static final String EXTRA_TIME_MILLIS = "TIME_MILLIS";
    private static final String EXTRA_TIMER_RUNNING = "TIMER_RUNNING";
    private static long START_TIME_IN_MILLIS = 60000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private TextView mFirstTeamTextView;
    private TextView mSecondTeamTextView;
    private TextView mVenueTextView;
    private TextView mDateOfGameTextView;
    private TextView mTimeOfGameTextView;

    private String mFirstTeamName;
    private String mSecondTeamName;
    private String mVenueOfGame;
    private String mGameDuraionInString;
    private String mDate;
    private String mTime;
    private Long mGameDurationInLong;

    private TextView mCounterTextView;
    private TextView mStartTimerTextView;
    private LinearLayout mStopPauseResumeTimerLayout;
    private TextView mPauseResumeTimerTextView;
    private TextView mStopTimerTextView;
    private CountDownTimer mCounterTimer;
    private boolean mTimerRunning;

    private TextView mFirstTeamScoreTextView;
    private TextView mSecondTeamScoreTextView;
    private int mFirstTeamScoreInt = 0;
    private int mSecondTeamScoreInt = 0;
    private String mFirstTeamScoreString;
    private String mSecondTeamScoreString;

    private TextView mFirstTeamRedCardTextView;
    private TextView mSecondTeamRedCardTextView;
    private TextView mFirstTeamYellowCardTextView;
    private TextView mSecondTeamYellowCardTextView;
    private int mFirstTeamRedCardInt = 0;
    private int mSecondTeamRedCardInt = 0;
    private int mFirstTeamYellowCardInt = 0;
    private int mSecondTeamYellowCardInt = 0;
    private String mFirstTeamRedCardString;
    private String mSecondTeamRedCardString;
    private String mFirstTeamYellowCardString;
    private String mSecondTeamYellowCardString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_third);

        final AlertDialog.Builder guideDialog = new AlertDialog.Builder(this);
        guideDialog.setTitle(R.string.dialog_title);
        guideDialog.setMessage(getString(R.string.dialog_msgone) +"\n\n"+
                getString(R.string.dialog_msgtwo) +"\n\n"+
                getString(R.string.dialog_msgthree) +"\n\n"+
                getString(R.string.dialog_msgfour) +"\n\n"+
                getString(R.string.dialog_msgfive));
        guideDialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        guideDialog.setCancelable(false);
        guideDialog.show();

        mFirstTeamTextView = (TextView)findViewById(R.id.first_team_textview);
        mSecondTeamTextView = (TextView)findViewById(R.id.second_team_textview);
        mVenueTextView = (TextView)findViewById(R.id.venue_game_textview);
        mDateOfGameTextView = (TextView)findViewById(R.id.dateOfGame_textview);
        mTimeOfGameTextView = (TextView)findViewById(R.id.timeOfGame_textview);

        mFirstTeamScoreTextView = (TextView)findViewById(R.id.first_team_score_textview);
        mSecondTeamScoreTextView = (TextView)findViewById(R.id.second_team_score_textview);
        mFirstTeamRedCardTextView = (TextView)findViewById(R.id.first_team_red_card_text_view);
        mFirstTeamYellowCardTextView = (TextView)findViewById(R.id.first_team_yellow_card_text_view);
        mSecondTeamRedCardTextView = (TextView)findViewById(R.id.second_team_red_card_text_view);
        mSecondTeamYellowCardTextView = (TextView)findViewById(R.id.second_team_yellow_card_text_view);

        Intent intent = getIntent();
        mFirstTeamName = intent.getStringExtra(SecondActivity.EXTRA_FIRST_TEAM);
        mSecondTeamName = intent.getStringExtra(SecondActivity.EXTRA_SECOND_TEAM);
        mVenueOfGame = intent.getStringExtra(SecondActivity.EXTRA_VENUE);
        mGameDuraionInString = intent.getStringExtra(SecondActivity.EXTRA_DURATION);
        mDate = intent.getStringExtra(SecondActivity.EXTRA_DATE_OF_GAME);
        mTime = intent.getStringExtra(SecondActivity.EXTRA_TIME_OF_GAME);

        mFirstTeamTextView.setText(mFirstTeamName);
        mSecondTeamTextView.setText(mSecondTeamName);
        mVenueTextView.setText(getString(R.string.venue) + mVenueOfGame);
        mGameDurationInLong = Long.parseLong(mGameDuraionInString) * 60000;
        START_TIME_IN_MILLIS = mGameDurationInLong;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mDateOfGameTextView.setText(getString(R.string.date) + mDate);
        mTimeOfGameTextView.setText(getString(R.string.time) + mTime);

        mCounterTextView = (TextView)findViewById(R.id.timer);
        mStopPauseResumeTimerLayout = (LinearLayout)findViewById(R.id.stopPauseResume_linearLayout);

        mStartTimerTextView = (TextView)findViewById(R.id.start_timer_textview);
        mStartTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        mPauseResumeTimerTextView = (TextView)findViewById(R.id.pause_resume_timer_textview);
        mPauseResumeTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseResumeTimer();
            }
        });

        mStopTimerTextView = (TextView)findViewById(R.id.stop_timer_textview);
        mStopTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
            }
        });
        updateCountDownTimer();
    }

    public void shareString(View view) {
        if (mFirstTeamScoreInt == 0 && mSecondTeamScoreInt == 0){ mFirstTeamScoreString = mSecondTeamScoreString = "0";}
        String string = mFirstTeamName + " " + mFirstTeamScoreString + " - " + mSecondTeamScoreString + " " + mSecondTeamName;
        string += "\n" + getString(R.string.venue) + mVenueOfGame;
        string += "\n" + getString(R.string.date) + mDate + "  " + getString(R.string.time) + mTime;
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_title)
                .setText(string)
                .startChooser();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putInt(EXTRA_FIRST_TEAM_SCORE, mFirstTeamScoreInt);
        saveInstanceState.putInt(EXTRA_SECOND_TEAM_SCORE, mSecondTeamScoreInt);
        saveInstanceState.putInt(EXTRA_RED_FIRST_TEAM, mFirstTeamRedCardInt);
        saveInstanceState.putInt(EXTRA_RED_SECOND_TEAM, mSecondTeamRedCardInt);
        saveInstanceState.putInt(EXTRA_YELLOW_FIRST_TEAM, mFirstTeamYellowCardInt);
        saveInstanceState.putInt(EXTRA_YELLOW_SECOND_TEAM, mSecondTeamYellowCardInt);
        saveInstanceState.putLong(EXTRA_TIME_MILLIS, mTimeLeftInMillis);
        saveInstanceState.putBoolean(EXTRA_TIMER_RUNNING, mTimerRunning);
        super.onSaveInstanceState(saveInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        mFirstTeamScoreInt = saveInstanceState.getInt(EXTRA_FIRST_TEAM_SCORE);
        mFirstTeamScoreString = String .valueOf(mFirstTeamScoreInt);
        mFirstTeamScoreTextView.setText(mFirstTeamScoreString);

        mSecondTeamScoreInt = saveInstanceState.getInt(EXTRA_SECOND_TEAM_SCORE);
        mSecondTeamScoreString = String.valueOf(mSecondTeamScoreInt);
        mSecondTeamScoreTextView.setText(mSecondTeamScoreString);

        mFirstTeamRedCardInt = saveInstanceState.getInt(EXTRA_RED_FIRST_TEAM);
        mFirstTeamRedCardString = String.valueOf(mFirstTeamRedCardInt);
        mFirstTeamRedCardTextView.setText(mFirstTeamRedCardString);

        mSecondTeamRedCardInt = saveInstanceState.getInt(EXTRA_RED_SECOND_TEAM);
        mSecondTeamRedCardString = String.valueOf(mSecondTeamRedCardInt);
        mSecondTeamRedCardTextView.setText(mSecondTeamRedCardString);

        mFirstTeamYellowCardInt = saveInstanceState.getInt(EXTRA_YELLOW_FIRST_TEAM);
        mFirstTeamYellowCardString = String.valueOf(mFirstTeamYellowCardInt);
        mFirstTeamYellowCardTextView.setText(mFirstTeamYellowCardString);

        mSecondTeamYellowCardInt = saveInstanceState.getInt(EXTRA_YELLOW_SECOND_TEAM);
        mSecondTeamYellowCardString = String.valueOf(mSecondTeamYellowCardInt);
        mSecondTeamYellowCardTextView.setText(mSecondTeamYellowCardString);

        mTimeLeftInMillis = saveInstanceState.getLong(EXTRA_TIME_MILLIS);
        mTimerRunning = saveInstanceState.getBoolean(EXTRA_TIMER_RUNNING);
        if (mTimerRunning){
            mStopPauseResumeTimerLayout.setVisibility(View.VISIBLE);
            mStartTimerTextView.setVisibility(View.INVISIBLE);
            startTimer();
            updateCountDownTimer();
        } else{
            mStopPauseResumeTimerLayout.setVisibility(View.INVISIBLE);
            mStartTimerTextView.setVisibility(View.VISIBLE);
            updateCountDownTimer();
        }
    }


    public void goalForFirstTeam(View view) {
        mFirstTeamScoreInt += 1;
        displayForFirstTeam(mFirstTeamScoreInt);
    }

    private void displayForFirstTeam(int mFirstTeamScoreInt) {
        mFirstTeamScoreString = String.valueOf(mFirstTeamScoreInt);
        mFirstTeamScoreTextView.setText(mFirstTeamScoreString);
    }

    public void goalForSecondTeam(View view) {
        mSecondTeamScoreInt += 1;
        displayForSecondTeam(mSecondTeamScoreInt);
    }

    private void displayForSecondTeam(int mSecondTeamScoreInt) {
        mSecondTeamScoreString = String.valueOf(mSecondTeamScoreInt);
        mSecondTeamScoreTextView.setText(mSecondTeamScoreString);
    }

    public void startTimer() {
        mCounterTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownTimer();
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                updateCountDownTimer();
                mTimerRunning = false;
                mStartTimerTextView.setVisibility(View.VISIBLE);
                mStopPauseResumeTimerLayout.setVisibility(View.INVISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mStartTimerTextView.setVisibility(View.INVISIBLE);
        mStopPauseResumeTimerLayout.setVisibility(View.VISIBLE);
    }

    public void pauseResumeTimer() {
        if (mTimerRunning) {
            mCounterTimer.cancel();
            mTimerRunning = false;
            mPauseResumeTimerTextView.setText(R.string.resume);
        }else {
            startTimer();
            mTimerRunning = true;
            mPauseResumeTimerTextView.setText(R.string.pause);
        }
    }

    public void stopTimer() {
        mCounterTimer.cancel();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownTimer();
        mStartTimerTextView.setVisibility(View.VISIBLE);
        mStopPauseResumeTimerLayout.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownTimer() {
        int minutesInt = (int) (mTimeLeftInMillis/1000)/60;
        int secondsInt = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutesInt,secondsInt);
        mCounterTextView.setText(timeLeftFormatted);
    }

    public void redForFirstTeam(View view) {
        mFirstTeamRedCardInt += 1;
        displayRedForFirstTeam(mFirstTeamRedCardInt);
    }

    private void displayRedForFirstTeam(int mFirstTeamRedCardInt) {
        mFirstTeamRedCardString = String.valueOf(mFirstTeamRedCardInt);
        mFirstTeamRedCardTextView.setText(mFirstTeamRedCardString);
    }

    public void redForSecondTeam(View view) {
        mSecondTeamRedCardInt += 1;
        displayRedForSecondTeam(mSecondTeamRedCardInt);
    }

    private void displayRedForSecondTeam(int mSecondTeamRedCardInt) {
        mSecondTeamRedCardString = String.valueOf(mSecondTeamRedCardInt);
        mSecondTeamRedCardTextView.setText(mSecondTeamRedCardString);
    }

    public void yellowForFirstTeam(View view) {
        mFirstTeamYellowCardInt += 1;
        displayYellowForFirstTeam(mFirstTeamYellowCardInt);
    }

    private void displayYellowForFirstTeam(int mFirstTeamYellowCardInt) {
        mFirstTeamYellowCardString = String.valueOf(mFirstTeamYellowCardInt);
        mFirstTeamYellowCardTextView.setText(mFirstTeamYellowCardString);
    }

    public void yellowForSecondTeam(View view) {
        mSecondTeamYellowCardInt += 1;
        displayYellowForSecondTeam(mSecondTeamYellowCardInt);
    }

    private void displayYellowForSecondTeam(int mSecondTeamYellowCardInt) {
        mSecondTeamYellowCardString = String.valueOf(mSecondTeamYellowCardInt);
        mSecondTeamYellowCardTextView.setText(mSecondTeamYellowCardString);
    }
}
