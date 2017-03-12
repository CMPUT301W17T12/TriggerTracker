package com.projectattitude.projectattitude.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.projectattitude.projectattitude.Abstracts.MoodActivity;
import com.projectattitude.projectattitude.Objects.DatePickerEditText;
import com.projectattitude.projectattitude.Objects.Mood;
import com.projectattitude.projectattitude.R;

/**
 * The CreateMood handles the creation of mood objects for the user. The only field that is
 * mandatory is the mood state field. Entering other data will display it upon creation but is
 * not needed. Moods will be updated in the database if connected to the internet. Otherwise, they
 * will remain stored on the application where they will be pushed to the database as soon as a
 * connection is reestablished.
 */
public class CreateMoodActivity extends MoodActivity {

    private Mood newMood;   // initializing the mood object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);  // setting the view

        Button completeButton = (Button) findViewById(R.id.saveButton); // initialization of the buttons
        final DatePickerEditText date = new DatePickerEditText(this, R.id.dateField);
        final Spinner emotionSpinner = (Spinner) findViewById(R.id.emotionSpinner);
        final EditText etTrigger = (EditText) findViewById(R.id.triggerField);
        final Spinner socialSituationSpinner = (Spinner) findViewById(R.id.spinner);
        final CheckBox saveLocation = (CheckBox) findViewById(R.id.saveLocation); // geoPoint location saving

        /**
         * The complete button checks if there are any errors and then sets all the values of the
         * mood to the appropriate properties based off the data input.
         */
        completeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Spinner class will return a textview when you use getSelectedView(), allows for easy setError
                TextView errorText = (TextView) emotionSpinner.getSelectedView();

                if(errorCheck(errorText, etTrigger)){   //checking the
                    newMood = new Mood();
                    newMood.setEmotionState(emotionSpinner.getSelectedItem().toString());
                    newMood.setMoodDate(date.getDate());
                    newMood.setTrigger(etTrigger.getText().toString());
                    newMood.setSocialSituation(socialSituationSpinner.getSelectedItem().toString());

                    /*if(saveLocation.isChecked()){ //TODO check location
                        GeoPoint myLocation = LocationServices.FusedLocationApi.getLastLocation()
                        newMood.setGeoLocation(myLocation);
                    }*/


                    Intent returnCreateMoodIntent = new Intent();
                    returnCreateMoodIntent.putExtra("addMoodIntent", newMood);
                    setResult(RESULT_OK, returnCreateMoodIntent);
                    finish();
                }
            }
        });
    }

    /*error checks Emotional State spinner to make sure an emotional state was chosen
    also error checks trigger input field for character length*/
    public boolean errorCheck(TextView emotionStateText, EditText etTriggerText) {

        String etTriggerString = etTriggerText.getText().toString();

        //count whitespace of trigger string
        int spaces = etTriggerString.length() - etTriggerString.replace(" ", "").length();

        if(emotionStateText.getText().toString().equals("Select an emotional state")) {
            emotionStateText.setTextColor(Color.RED);
            emotionStateText.setText(R.string.emotion_state_error);

            return false;
        }

        //trigger is longer then 20 characters
        else if(etTriggerString.length() > 20){
            etTriggerText.setError("Reason must be no more than 20 characters");
            return false;
        }

        //trigger is more than 3 words
        else if(spaces > 2){
            etTriggerText.setError("Reason must be no more than 3 words");
            return false;
        }
        return true;
    }

    /**
     * Not sure what this does tbh
     * @return A location? Should probably be the Location Type
     */
    private String createLocation(){
        return "";
    }

    /**
     * I don't know what this does either
     * @return an Image most likely
     */
    private void createPicture(){
        return;
    }
}
