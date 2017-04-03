package com.example.android.heartcure;

import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.R.attr.button;
import static android.R.attr.flipInterval;
import static android.R.attr.radioButtonStyle;
import static android.R.attr.state_empty;
import static android.R.attr.state_selected;
import static android.R.id.message;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.heartcure.R.id.radioGroup1;
import static com.example.android.heartcure.R.id.radioGroup2;
import static com.example.android.heartcure.R.string.answer3c;
import static com.example.android.heartcure.R.string.bmi;
import static com.example.android.heartcure.R.style.Widget_AppCompat_CompoundButton_RadioButton;
import static com.example.android.heartcure.R.style.radioButton;
import static com.example.android.heartcure.R.style.radioGroup;

public class MainActivity extends AppCompatActivity {
    int score; //total score for the evaluation
    int bmi; // bmi calculated into scoreThirdQuestion method and used in the final evaluation
    boolean clicked = false; //it becomes true if buttonResults and resetButton are pressed

    RadioGroup radiogroup1, radiogroup2, radiogroup4, radiogroup5, radiogroup6, radiogroup7,
            radiogroup8, radiogroup9, radiogroup10, radiogroup11, radiogroup12, radiogroup13;

    EditText userweight, userheight;

    CheckBox userfemale, usermale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // These lines are used to customize ActionBar with a subtitle and an image.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.app_logo);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setSubtitle(getString(R.string.appbar_subtitle));
        setContentView(R.layout.activity_main);


        radiogroup1 = (RadioGroup) findViewById(radioGroup1);
        radiogroup2 = (RadioGroup) findViewById(radioGroup2);
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
        RadioButton answer1a = (RadioButton) findViewById(R.id.radioButton1_1);
        RadioButton answer1b = (RadioButton) findViewById(R.id.radioButton1_2);
        RadioButton answer1c = (RadioButton) findViewById(R.id.radioButton1_3);
        RadioButton answer1d = (RadioButton) findViewById(R.id.radioButton1_4);

        if (answer1a.isChecked())
            score = score;
        if (answer1b.isChecked())
            score = score + 4;
        if (answer1c.isChecked())
            score = score + 6;
        if (answer1d.isChecked())
            score = score;
    }

    // This method calculates score for the second radiogroup
    private void scoreSecondQuestion() {
        RadioButton answer2a = (RadioButton) findViewById(R.id.radioButton2_1);
        RadioButton answer2b = (RadioButton) findViewById(R.id.radioButton2_2);
        RadioButton answer2c = (RadioButton) findViewById(R.id.radioButton2_3);
        RadioButton answer2d = (RadioButton) findViewById(R.id.radioButton2_4);

        if (answer2a.isChecked())
            score = score;
        if (answer2b.isChecked())
            score = score + 6;
        if (answer2c.isChecked())
            score = score + 8;
        if (answer2d.isChecked())
            score = score + 10;
    }

    // This method calculates score for the third radiogroup and calculates bmi to show in the final evaluation
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

        if (answer3a.isChecked() && bmi <= 24)
            score = score;
        if ((answer3a.isChecked()) && (bmi >= 25 && bmi <= 30))
            score = score + 1;
        if (answer3a.isChecked() && bmi > 30)
            score = score + 2;
        if (answer3b.isChecked() && bmi <= 25)
            score = score;
        if (answer3b.isChecked() && (bmi >= 26 && bmi <= 30))
            score = score + 1;
        if (answer3b.isChecked() && bmi > 30)
            score = score + 2;

    }

    // This method calculates score for the fourth radiogroup
    private void scoreFourthQuestion() {
        RadioButton answer4a = (RadioButton) findViewById(R.id.radioButton4_1);
        RadioButton answer4b = (RadioButton) findViewById(R.id.radioButton4_2);
        RadioButton answer4c = (RadioButton) findViewById(R.id.radioButton4_3);

        if (answer4a.isChecked())
            score = score - 4;
        if (answer4b.isChecked())
            score = score - 2;
        if (answer4c.isChecked())
            score = score;
    }

    // This method calculates score for the fifth radiogroup
    private void scoreFifthQuestion() {
        RadioButton answer5a = (RadioButton) findViewById(R.id.radioButton5_1);
        RadioButton answer5b = (RadioButton) findViewById(R.id.radioButton5_2);
        RadioButton answer5c = (RadioButton) findViewById(R.id.radioButton5_3);

        if (answer5a.isChecked())
            score = score + 4;
        if (answer5b.isChecked())
            score = score + 2;
        if (answer5c.isChecked())
            score = score;
    }

    // This method calculates score for the sixth radiogroup
    private void scoreSixthQuestion() {
        RadioButton answer6a = (RadioButton) findViewById(R.id.radioButton6_1);
        RadioButton answer6b = (RadioButton) findViewById(R.id.radioButton6_2);
        RadioButton answer6c = (RadioButton) findViewById(R.id.radioButton6_3);

        if (answer6a.isChecked())
            score = score - 2;
        if (answer6b.isChecked())
            score = score;
        if (answer6c.isChecked())
            score = score + 2;
    }

    // This method calculates score for the seventh radiogroup
    private void scoreSeventhQuestion() {
        RadioButton answer7a = (RadioButton) findViewById(R.id.radioButton7_1);
        RadioButton answer7b = (RadioButton) findViewById(R.id.radioButton7_2);
        RadioButton answer7c = (RadioButton) findViewById(R.id.radioButton7_3);
        RadioButton answer7d = (RadioButton) findViewById(R.id.radioButton7_4);

        if (answer7a.isChecked())
            score = score + 2;
        if (answer7b.isChecked())
            score = score + 6;
        if (answer7c.isChecked())
            score = score + 3;
        if (answer7d.isChecked())
            score = score;
    }

    // This method calculates score for the eighth radiogroup
    private void scoreEighthQuestion() {
        RadioButton answer8a = (RadioButton) findViewById(R.id.radioButton8_1);
        RadioButton answer8b = (RadioButton) findViewById(R.id.radioButton8_2);
        RadioButton answer8c = (RadioButton) findViewById(R.id.radioButton8_3);
        RadioButton answer8d = (RadioButton) findViewById(R.id.radioButton8_4);
        RadioButton answer8e = (RadioButton) findViewById(R.id.radioButton8_5);
        RadioButton answer8f = (RadioButton) findViewById(R.id.radioButton8_6);
        RadioButton answer8g = (RadioButton) findViewById(R.id.radioButton8_7);

        if (answer8a.isChecked())
            score = score + 2;
        if (answer8b.isChecked())
            score = score;
        if (answer8c.isChecked())
            score = score + 1;
        if (answer8d.isChecked())
            score = score + 6;
        if (answer8e.isChecked())
            score = score;
        if (answer8f.isChecked())
            score = score + 2;
        if (answer8g.isChecked())
            score = score + 4;
    }

    // This method calculates score for the ninth radiogroup
    private void scoreNinthQuestion() {
        RadioButton answer9a = (RadioButton) findViewById(R.id.radioButton9_1);
        RadioButton answer9b = (RadioButton) findViewById(R.id.radioButton9_2);
        RadioButton answer9c = (RadioButton) findViewById(R.id.radioButton9_3);
        RadioButton answer9d = (RadioButton) findViewById(R.id.radioButton9_4);
        RadioButton answer9e = (RadioButton) findViewById(R.id.radioButton9_5);

        if (answer9a.isChecked())
            score = score + 2;
        if (answer9b.isChecked())
            score = score;
        if (answer9c.isChecked())
            score = score + 6;
        if (answer9d.isChecked())
            score = score + 8;
        if (answer9e.isChecked())
            score = score + 8;
    }

    // This method calculates score for the tenth radiogroup
    private void scoreTenthQuestion() {
        RadioButton answer10a = (RadioButton) findViewById(R.id.radioButton10_1);
        RadioButton answer10b = (RadioButton) findViewById(R.id.radioButton10_2);
        RadioButton answer10c = (RadioButton) findViewById(R.id.radioButton10_3);
        RadioButton answer10d = (RadioButton) findViewById(R.id.radioButton10_4);

        if (answer10a.isChecked())
            score = score;
        if (answer10b.isChecked())
            score = score;
        if (answer10c.isChecked())
            score = score + 2;
        if (answer10d.isChecked())
            score = score + 4;
    }

    // This method calculates score for the eleventh radiogroup
    private void scoreEleventhQuestion() {
        RadioButton answer11a = (RadioButton) findViewById(R.id.radioButton11_1);
        RadioButton answer11b = (RadioButton) findViewById(R.id.radioButton11_2);
        RadioButton answer11c = (RadioButton) findViewById(R.id.radioButton11_3);
        RadioButton answer11d = (RadioButton) findViewById(R.id.radioButton11_4);

        if (answer11a.isChecked())
            score = score;
        if (answer11b.isChecked())
            score = score + 10;
        if (answer11c.isChecked())
            score = score + 6;
        if (answer11d.isChecked())
            score = score + 4;
    }

    // This method calculates score for the twelfth radiogroup
    private void scoreTwelfthQuestion() {
        RadioButton answer12a = (RadioButton) findViewById(R.id.radioButton12_1);
        RadioButton answer12b = (RadioButton) findViewById(R.id.radioButton12_2);

        if (answer12a.isChecked())
            score = score + 10;
        if (answer12b.isChecked())
            score = score;
    }

    // This method calculates score for the thirteenth radiogroup
    private void scoreThirteenthQuestion() {
        RadioButton answer13a = (RadioButton) findViewById(R.id.radioButton13_1);
        RadioButton answer13b = (RadioButton) findViewById(R.id.radioButton13_2);

        if (answer13a.isChecked())
            score = score + 10;
        if (answer13b.isChecked())
            score = score;
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
    // with his bmi and a footnote.
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
                message += " " + getString(R.string.bmi) + bmi + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .show();
            }

            if (score >= 5 && score <= 8) {
                String message = getString(R.string.evaluation2);
                message += " " + getString(R.string.bmi) + bmi + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .show();

            }

            if (score >= 9 && score <= 16) {

                String message = getString(R.string.evaluation3);
                message += " " + getString(R.string.bmi) + bmi + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
                        .show();

            }

            if (score >= 17) {
                String message = getString(R.string.evaluation4);
                message += " " + getString(R.string.bmi) + bmi + ".";
                message += "\n" + "\n" + getString(R.string.foot_note_evaluation);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.title_evaluation))
                        .setMessage(message)
                        .setPositiveButton("ok", null)
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

    }
}


