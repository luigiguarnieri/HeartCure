package com.example.android.heartcure;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity {
    int score; //total score for the evaluation
    int bmi; // bmi calculated into scoreThirdQuestion method and used in the final evaluation
    boolean clicked = false; //it becomes true if buttonResults and resetButton are pressed

    ScrollView mainScrollView;

    RadioGroup radiogroup1, radiogroup2, radiogroup4, radiogroup5, radiogroup6, radiogroup7,
            radiogroup8, radiogroup9, radiogroup10, radiogroup11, radiogroup12, radiogroup13;

    EditText userweight, userheight;

    CheckBox userfemale, usermale;

    String bmievaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // These lines are used to customize ActionBar with a subtitle and an image.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.app_logo);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.END
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setSubtitle(getString(R.string.appbar_subtitle));
        setContentView(R.layout.activity_main);

        // These lines are necessary to avoid focus stays on the two EditTexts
        // after they are edited with user datas.
        mainScrollView = (ScrollView) findViewById(R.id.activity_main);
        mainScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mainScrollView.setFocusable(true);
        mainScrollView.setFocusableInTouchMode(true);
        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        radiogroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radiogroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radiogroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        radiogroup5 = (RadioGroup) findViewById(R.id.radioGroup5);
        radiogroup6 = (RadioGroup) findViewById(R.id.radioGroup6);
        radiogroup7 = (RadioGroup) findViewById(R.id.radioGroup7);
        radiogroup8 = (RadioGroup) findViewById(R.id.radioGroup8);
        radiogroup9 = (RadioGroup) findViewById(R.id.radioGroup9);
        radiogroup10 = (RadioGroup) findViewById(R.id.radioGroup10);
        radiogroup11 = (RadioGroup) findViewById(R.id.radioGroup11);
        radiogroup12 = (RadioGroup) findViewById(R.id.radioGroup12);
        radiogroup13 = (RadioGroup) findViewById(R.id.radioGroup13);
        userweight = (EditText) findViewById(R.id.weight_data);
        userheight = (EditText) findViewById(R.id.height_data);
        userfemale = (CheckBox) findViewById(R.id.radioButton3_1);
        usermale = (CheckBox) findViewById(R.id.radioButton3_2);

        Button submit = (Button) findViewById(R.id.buttonResults);

        //if savedInstanceState is not null, values of the total score and clicked are loaded
        if (savedInstanceState != null) {
            clicked = savedInstanceState.getBoolean("isShowResultsClicked");
            score = savedInstanceState.getInt("scoreForEvaluation");
            //if the Finish button was clicked before, it will stay disabled
            if (clicked) {
                submit.setEnabled(true);
            }
        }
    }

    //This method saves values of button clicked even during screen rotation
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("scoreForEvaluation", score);
        savedInstanceState.putBoolean("isShowResultsClicked", clicked);

        super.onSaveInstanceState(savedInstanceState);
    }

    // This method calculates score for the first radiogroup
    private void scoreFirstQuestion() {

        int checkedRadioButtonId = radiogroup1.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton1_2:
                score = score + 4;
                break;
            case R.id.radioButton1_3:
                score = score + 6;
        }
    }

    // This method calculates score for the second radiogroup
    private void scoreSecondQuestion() {

        int checkedRadioButtonId = radiogroup2.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton2_2:
                score = score + 6;
                break;
            case R.id.radioButton2_3:
                score = score + 8;
                break;
            case R.id.radioButton2_4:
                score = score + 10;
        }
    }


    // This method calculates score for the third radiogroup and calculates
    // and rates with string bmievaluation bmi to show in the final evaluation
    private void scoreThirdQuestion() {
        CheckBox answer3a = (CheckBox) findViewById(R.id.radioButton3_1);
        CheckBox answer3b = (CheckBox) findViewById(R.id.radioButton3_2);
        EditText userWeight = (EditText) findViewById(R.id.weight_data);
        String weight = userWeight.getText().toString();
        int newWeight = Integer.parseInt(weight);
        EditText userHeight = (EditText) findViewById(R.id.height_data);
        String height = userHeight.getText().toString();
        int newHeight = Integer.parseInt(height);

        bmi = ((newWeight * 10000) / (newHeight * newHeight));


        if ((answer3a.isChecked()) && (bmi >= 25 && bmi <= 30)) {
            score = score + 1;
        } else if (answer3a.isChecked() && bmi > 30) {
            score = score + 2;
        } else if (answer3b.isChecked() && (bmi >= 26 && bmi <= 30)) {
            score = score + 1;
        } else if (answer3b.isChecked() && bmi > 30) {
            score = score + 2;
        }

        // evalution of BMI for women
        if ((answer3a.isChecked()) && bmi < 19) {
            bmievaluation = getString(R.string.bmi_evaluation1);
        } else if ((answer3a.isChecked()) && (bmi >= 19 && bmi <= 24)) {
            bmievaluation = getString(R.string.bmi_evaluation2);
        } else if ((answer3a.isChecked()) && (bmi >= 25 && bmi <= 30)) {
            bmievaluation = getString(R.string.bmi_evaluation3);
        } else if ((answer3a.isChecked()) && bmi > 30) {
            bmievaluation = getString(R.string.bmi_evaluation4);
        }

        // evalution of BMI for men
        if ((answer3b.isChecked()) && bmi < 20) {
            bmievaluation = getString(R.string.bmi_evaluation1);
        } else if ((answer3b.isChecked()) && (bmi >= 20 && bmi <= 25)) {
            bmievaluation = getString(R.string.bmi_evaluation2);
        } else if ((answer3b.isChecked()) && (bmi >= 26 && bmi <= 30)) {
            bmievaluation = getString(R.string.bmi_evaluation3);
        } else if ((answer3b.isChecked()) && bmi > 30) {
            bmievaluation = getString(R.string.bmi_evaluation4);
        }
    }

    // This method calculates score for the fourth radiogroup
    private void scoreFourthQuestion() {
        int checkedRadioButtonId = radiogroup4.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton4_1:
                score = score - 4;
                break;
            case R.id.radioButton4_2:
                score = score - 2;
        }
    }

    // This method calculates score for the fifth radiogroup
    private void scoreFifthQuestion() {
        int checkedRadioButtonId = radiogroup5.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton5_1:
                score = score + 4;
                break;
            case R.id.radioButton5_2:
                score = score + 2;
        }
    }

    // This method calculates score for the sixth radiogroup
    private void scoreSixthQuestion() {
        int checkedRadioButtonId = radiogroup6.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton6_1:
                score = score - 2;
                break;
            case R.id.radioButton6_3:
                score = score + 2;
        }
    }

    // This method calculates score for the seventh radiogroup
    private void scoreSeventhQuestion() {
        int checkedRadioButtonId = radiogroup7.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton7_1:
                score = score + 2;
                break;
            case R.id.radioButton7_2:
                score = score + 6;
                break;
            case R.id.radioButton7_3:
                score = score + 3;
        }
    }

    // This method calculates score for the eighth radiogroup
    private void scoreEighthQuestion() {
        int checkedRadioButtonId = radiogroup8.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton8_1:
                score = score + 2;
                break;
            case R.id.radioButton8_3:
                score = score + 1;
                break;
            case R.id.radioButton8_4:
                score = score + 6;
                break;
            case R.id.radioButton8_6:
                score = score + 2;
                break;
            case R.id.radioButton8_7:
                score = score + 4;

        }
    }

    // This method calculates score for the ninth radiogroup
    private void scoreNinthQuestion() {
        int checkedRadioButtonId = radiogroup9.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton9_1:
                score = score + 2;
                break;
            case R.id.radioButton9_3:
                score = score + 6;
                break;
            case R.id.radioButton9_4:
                score = score + 8;
                break;
            case R.id.radioButton9_5:
                score = score + 8;
        }
    }

    // This method calculates score for the tenth radiogroup
    private void scoreTenthQuestion() {
        int checkedRadioButtonId = radiogroup10.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton10_3:
                score = score + 2;
                break;
            case R.id.radioButton10_4:
                score = score + 4;
        }
    }

    // This method calculates score for the eleventh radiogroup
    private void scoreEleventhQuestion() {
        int checkedRadioButtonId = radiogroup11.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton11_2:
                score = score + 10;
                break;
            case R.id.radioButton11_3:
                score = score + 6;
                break;
            case R.id.radioButton11_4:
                score = score + 4;
        }
    }

    // This method calculates score for the twelfth radiogroup
    private void scoreTwelfthQuestion() {
        int checkedRadioButtonId = radiogroup12.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton12_1:
                score = score + 10;
        }
    }

    // This method calculates score for the thirteenth radiogroup
    private void scoreThirteenthQuestion() {
        int checkedRadioButtonId = radiogroup13.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.radioButton13_1:
                score = score + 10;
        }
    }

    // This method sums the total score
    private int sumScore() {

        scoreFirstQuestion();
        scoreSecondQuestion();
        scoreThirdQuestion();
        scoreFourthQuestion();
        scoreFifthQuestion();
        scoreSixthQuestion();
        scoreSeventhQuestion();
        scoreEighthQuestion();
        scoreNinthQuestion();
        scoreTenthQuestion();
        scoreEleventhQuestion();
        scoreTwelfthQuestion();
        scoreThirteenthQuestion();

        return score;
    }

    // This method shows final evaluation only if user has selected all the radiogroups, one
    // checkbox about his sex and edited the two fields with his weight and height.
    // If both the checkboxes about sex are checked, an AlertDialog shows up an error,
    // asking user to make only one choice between them.
    // Same thing if user hasn't completed all the test: an AlertDialog shows up an error.
    // If the condition is satisfied, final evaluation is shown with an AlertDialog
    // with bmi and its evaluation and a footnote.
    public void showResults(View view) {
        if (userfemale.isChecked() && usermale.isChecked()) {
            String message = getString(R.string.sex_mismatch);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.title_sex_mismatch))
                    .setMessage(message)
                    .setPositiveButton("ok", null)
                    .show();
        } else if ((!userfemale.isChecked() && !usermale.isChecked())
                || radiogroup1.getCheckedRadioButtonId() == -1
                || radiogroup2.getCheckedRadioButtonId() == -1
                || radiogroup4.getCheckedRadioButtonId() == -1
                || radiogroup5.getCheckedRadioButtonId() == -1
                || radiogroup6.getCheckedRadioButtonId() == -1
                || radiogroup7.getCheckedRadioButtonId() == -1
                || radiogroup8.getCheckedRadioButtonId() == -1
                || radiogroup9.getCheckedRadioButtonId() == -1
                || radiogroup10.getCheckedRadioButtonId() == -1
                || radiogroup11.getCheckedRadioButtonId() == -1
                || radiogroup12.getCheckedRadioButtonId() == -1
                || radiogroup13.getCheckedRadioButtonId() == -1
                || userheight.getText().toString().trim().equals("")
                || userweight.getText().toString().trim().equals("")) {
            String message = getString(R.string.complete_test);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.title_complete_test))
                    .setMessage(message)
                    .setPositiveButton("ok", null)
                    .show();
        } else {
            sumScore();

            if (score <= 4) {
                String message = getString(R.string.evaluation1);
                message += " " + getString(R.string.bmi) + bmi;
                message += " " + bmievaluation + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                final String newMessage = message;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .setNeutralButton(getString(R.string.share),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("text/plain");
                                        share.putExtra(Intent.EXTRA_TEXT, newMessage);
                                        startActivity(Intent.createChooser(share, "Select application to share your result"));
                                    }
                                })
                        .show();
            }

            if (score >= 5 && score <= 8) {
                String message = getString(R.string.evaluation2);
                message += " " + getString(R.string.bmi) + bmi;
                message += " " + bmievaluation + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                final String newMessage = message;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .setNeutralButton(getString(R.string.share),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("text/plain");
                                        share.putExtra(Intent.EXTRA_TEXT, newMessage);
                                        startActivity(Intent.createChooser(share, "Select application to share your result"));
                                    }
                                })
                        .show();

            }

            if (score >= 9 && score <= 16) {

                String message = getString(R.string.evaluation3);
                message += " " + getString(R.string.bmi) + bmi;
                message += " " + bmievaluation + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                final String newMessage = message;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .setNeutralButton(getString(R.string.share),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("text/plain");
                                        share.putExtra(Intent.EXTRA_TEXT, newMessage);
                                        startActivity(Intent.createChooser(share, "Select application to share your result"));
                                    }
                                })
                        .show();

            }

            if (score >= 17) {
                String message = getString(R.string.evaluation4);
                message += " " + getString(R.string.bmi) + bmi;
                message += " " + bmievaluation + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                final String newMessage = message;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .setNeutralButton(getString(R.string.share),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("text/plain");
                                        share.putExtra(Intent.EXTRA_TEXT, newMessage);
                                        startActivity(Intent.createChooser(share, "Select application to share your result"));
                                    }
                                })
                        .show();
            }

            Button submit = (Button) findViewById(R.id.buttonResults);
            submit.setEnabled(false);
            submit.setTextColor(Color.GRAY);
            clicked = true;


        }

    }

    // This method clears all radiogroups and fields.
    // It enables again the buttonResults after previous test has greyed it out.
    public void resetClickHandler(View v) {
        radiogroup1.clearCheck();
        radiogroup2.clearCheck();
        radiogroup4.clearCheck();
        radiogroup5.clearCheck();
        radiogroup6.clearCheck();
        radiogroup7.clearCheck();
        radiogroup8.clearCheck();
        radiogroup8.clearCheck();
        radiogroup9.clearCheck();
        radiogroup10.clearCheck();
        radiogroup11.clearCheck();
        radiogroup12.clearCheck();
        radiogroup13.clearCheck();
        userheight.getText().clear();
        userweight.getText().clear();
        userfemale.setChecked(false);
        usermale.setChecked(false);

        score = 0;

        Button submit = (Button) findViewById(R.id.buttonResults);
        submit.setEnabled(true);
        submit.setTextColor(Color.WHITE);
        clicked = false;
        mainScrollView.fullScroll(View.FOCUS_UP);
    }
}


