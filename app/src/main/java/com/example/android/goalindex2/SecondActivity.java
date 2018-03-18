package com.example.android.goalindex2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_FIRST_TEAM = "FIRST_TEAM";
    public static final String EXTRA_SECOND_TEAM = "SECOND_TEAM";
    public static final String EXTRA_VENUE = "VENUE";
    public static final String EXTRA_DURATION = "DURATION";
    public static final String EXTRA_DATE_OF_GAME = "DATE_OF_GAME";
    public static final String EXTRA_TIME_OF_GAME = "TIME_OF_GAME";

    private EditText mFirstTeamEdittext;
    private EditText mSecondTeamEdittext;
    private EditText mVenueEdittext;
    private EditText mDurationEdittext;

    private TextView mDateTextView;
    private TextView mTimeTextView;

    private String mFirstTeam;
    private String mSecondTeam;
    private String mVenue;
    private String mDuration;

    private String mDate;
    private String mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);

        mFirstTeamEdittext = (EditText)findViewById(R.id.first_team_edittext);
        mSecondTeamEdittext = (EditText)findViewById(R.id.second_team_edittext);
        mVenueEdittext = (EditText)findViewById(R.id.venue_game_edittext);
        mDurationEdittext = (EditText)findViewById(R.id.duration_game_edittext);
        mDateTextView = (TextView)findViewById(R.id.date_game_textview);
        mTimeTextView = (TextView)findViewById(R.id.time_game_textview);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(EXTRA_DATE_OF_GAME,mDate);
        saveInstanceState.putString(EXTRA_TIME_OF_GAME,mTime);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        mDate = saveInstanceState.getString(EXTRA_DATE_OF_GAME);
        mDateTextView.setText(mDate);
        mTime = saveInstanceState.getString(EXTRA_TIME_OF_GAME);
        mTimeTextView.setText(mTime);
    }

    public void toThirdActivity(View view) {
        mFirstTeam = mFirstTeamEdittext.getText().toString();
        mSecondTeam = mSecondTeamEdittext.getText().toString();
        mVenue = mVenueEdittext.getText().toString();
        mDuration = mDurationEdittext.getText().toString();

        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra(EXTRA_FIRST_TEAM,mFirstTeam);
        intent.putExtra(EXTRA_SECOND_TEAM,mSecondTeam);
        intent.putExtra(EXTRA_VENUE,mVenue);
        intent.putExtra(EXTRA_DURATION,mDuration);
        intent.putExtra(EXTRA_DATE_OF_GAME,mDate);
        intent.putExtra(EXTRA_TIME_OF_GAME,mTime);
        startActivity(intent);
    }

    public void showDatePickerDialog(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),getString(R.string.date_picker));
    }

    public void processDatePickerFragmentResult(int i, int i1, int i2) {
        String year_string = Integer.toString(i);
        String month_string = Integer.toString(i1);
        String day_string = Integer.toString(i2);

        mDate = day_string + "/" + month_string + "/" + year_string;
        mDateTextView.setText(mDate);
    }

    public void showTimePickerDialog(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(),getString(R.string.time_picker));
    }

    public void processTimePickerFragment(int i, int i1) {
        if (i>12){
            i -= 12;
            String hour_string = Integer.toString(i);
            String minute_string = Integer.toString(i1);
            mTime = hour_string + getString(R.string.colon) + minute_string + getString(R.string.pm);
            mTimeTextView.setText(mTime);
        } else {
            String hour_string = Integer.toString(i);
            String minute_string = Integer.toString(i1);
            mTime = hour_string + getString(R.string.colon) + minute_string + getString(R.string.am);
            mTimeTextView.setText(mTime);
        }
    }
}
