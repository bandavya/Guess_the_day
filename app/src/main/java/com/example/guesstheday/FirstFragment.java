package com.example.guesstheday;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;


public class FirstFragment extends Fragment  {
    //implements View.OnClickListener
    TextView guess_date;
    public static String ansvalue;
    public static Integer count = 0;
    //,OptionA,OptionB,OptionC,OptionD;
    //RadioButton OptionA,OptionB,OptionC,OptionD;
    RadioButton rdA,rdB,rdC,rdD;
    RadioGroup opt;
    //RadioButton answer=(RadioButton)findViewById(opt.getCheckedRadioButtonId());
    //private Question question = new Question();
    TextView showCountTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        // Get the count text view
        showCountTextView = fragmentFirstLayout.findViewById(R.id.q_number);

        return fragmentFirstLayout;
    }
    private void countMe(View view) {
        // Get the value of the text view
        String countString = showCountTextView.getText().toString();
        // Convert value to a number and increment it
        count = Integer.parseInt(countString);
        count++;
        // Display the new value in the text view.
        showCountTextView.setText(count.toString());
    }

    private void bandavya(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2030);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        String randate = gc.get(gc.DAY_OF_MONTH) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.YEAR) ;
        Log.d("info","date value is: " + randate);
        //((TextView) view.findViewById(R.id.guess_date)).setText(randate);
        guess_date.setText(randate);

        String[] weekdays = new DateFormatSymbols().getWeekdays();
        ansvalue = weekdays[gc.get(Calendar.DAY_OF_WEEK)];

        Random rand = new Random();
        List<String> givenList = new ArrayList<>(Arrays.asList("Monday", "Tuesday","Wednesday", "Thursday", "Friday","Saturday", "Sunday"));
        int numberOfElements = 3;
        List<String> finalrandomlist = new ArrayList<>();
        givenList.remove(weekdays[gc.get(Calendar.DAY_OF_WEEK)]);
        finalrandomlist.add(weekdays[gc.get(Calendar.DAY_OF_WEEK)]);

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String randomElement = givenList.get(randomIndex);
            finalrandomlist.add(randomElement);
            //System.out.println("\n"+ "random day::" + randomElement );
            givenList.remove(randomIndex);
            //System.out.println("\n"+ "given list::" + givenList );
        }
        //System.out.println("\n"+ "temp OPtions list(randomize again)::" + finalrandomlist );
        Collections.shuffle(finalrandomlist);

        //((TextView) view.findViewById(R.id.OptionA)).setText(finalrandomlist.get(0));
        rdA.setText(finalrandomlist.get(0));
        rdB.setText(finalrandomlist.get(1));
        rdC.setText(finalrandomlist.get(2));
        rdD.setText(finalrandomlist.get(3));
        //((TextView) view.findViewById(R.id.OptionB)).setText(finalrandomlist.get(1));
        //((TextView) view.findViewById(R.id.OptionC)).setText(finalrandomlist.get(2));
        //((TextView) view.findViewById(R.id.OptionD)).setText(finalrandomlist.get(3));
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guess_date = ((TextView) view.findViewById(R.id.guess_date));
        rdA = ((RadioButton) view.findViewById(R.id.OptionA));
        rdB = ((RadioButton) view.findViewById(R.id.OptionB));
        rdC = ((RadioButton) view.findViewById(R.id.OptionC));
        rdD = ((RadioButton) view.findViewById(R.id.OptionD));
        opt = ((RadioGroup) view.findViewById(R.id.answer_options));
        opt.clearCheck();
        opt.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    // The flow will come here when any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group, int checkedId)  {

                        // Get the selected Radio Button
                        RadioButton ans1 = ((RadioButton) group.findViewById(checkedId));
                        //RadioButton ans1 =(RadioButton) view.findViewById(opt.getCheckedRadioButtonId());
                    }
                });

        //OptionA.setOnClickListener(this);
        bandavya(view, savedInstanceState);


        // RadioGroup clicked_answer_options = (RadioGroup)findViewById(R.id.answer_options);
        /*
        RadioGroup clicked_answer_options = null;
        clicked_answer_options.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    // The flow will come here when  any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,int checkedId)
                    {
                        // Get the selected Radio Button
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        Log.d("info", String.valueOf(checkedId));
                    }
                });
        */
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //RadioGroup grp=(RadioGroup) view.findViewById(R.id.answer_options);
                //RadioButton ans1 =(RadioButton) view.findViewById(opt.getCheckedRadioButtonId());

                //rdA.setText(finalrandomlist.get(0));
                int selectedId = opt.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast myToast = Toast.makeText(getActivity(),
                            "OK,I can understand you dont want to try this question, Skipping and showing next Question",
                            Toast.LENGTH_LONG);
                    myToast.show();
                    opt.clearCheck();
                    //countMe(view);
                    bandavya(view, savedInstanceState);
                }
                else {
                    RadioButton myans = (RadioButton) opt.findViewById(selectedId);
                    // Now display the value of selected item by the Toast message
                    Toast myToast = Toast.makeText(getActivity(),
                            "Your Answer: " + myans.getText() + "                  Correct Answer: " + ansvalue,
                            Toast.LENGTH_SHORT);
                    myToast.show();
                    if (ansvalue == String.valueOf(myans.getText())) {
                        opt.clearCheck();
                        countMe(view);
                        bandavya(view, savedInstanceState);
                    }
                    else {
                         String countString1 = showCountTextView.getText().toString();
                         // Convert value to a number and increment it
                         count = Integer.parseInt(countString1);
                         count = count-1;
                         Toast myToastans = Toast.makeText(getActivity(), "Your score:" + count, Toast.LENGTH_SHORT);
                         myToastans.show();
                         //String strcnt;
                         //strcnt = String.valueOf(count);
                         FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(count);
                         NavHostFragment.findNavController(FirstFragment.this).navigate(action);
                         //   NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                    }
                }
                //@SuppressLint("ResourceType") Toast myToast = Toast.makeText(getActivity(), "value:" + ans1, Toast.LENGTH_SHORT);
                //myToast.show();
                /* below 3 lines are commented - bandavya
                opt.clearCheck();
                countMe(view);
                bandavya(view, savedInstanceState);
                */
                /* RadioGroup grp = (RadioGroup) view.findViewById(R.id.answer_options);
                RadioButton answer = (RadioButton) view.findViewById(grp.getCheckedRadioButtonId());
                //int score;
                if (weekdays[gc.get(Calendar.DAY_OF_WEEK)] == String.valueOf(answer.getText())) {
                 //   score = 0;
                    score++;
                }

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                 */

            }
        });
    }


    private void NextQuestion(int num) {
       // guess_date.setText();
    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
