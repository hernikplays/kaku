package cz.hernik.kaku;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.hernik.kaku.helper.HiraganaTable;

public class HiraganaWritingFragment extends Fragment {
    private int currIndex;
    private List<String> hiragana;
    private View v;
    private int points;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hiragana_writing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        currIndex = 0;
        points=0;
        v=view;
        String[] hiraganaArr = this.getArguments().getStringArray("hiraganalist");
        hiragana = new ArrayList<>(Arrays.asList(hiraganaArr));
        Collections.shuffle(hiragana);
        Button submit = v.findViewById(R.id.submit);
        submit.setOnClickListener(vi -> {
            submit();
        });

        Button next = v.findViewById(R.id.next);
        next.setOnClickListener(v -> {
            next();
        });

        showHiragana();
    }

    private void showHiragana(){
        // set "X left"
        TextView leftView = v.findViewById(R.id.left);
        leftView.setText(String.format(getString(R.string.left),hiragana.size()-currIndex-1));

        TextView hiraganaView = v.findViewById(R.id.hiragana);
        hiraganaView.setText(hiragana.get(currIndex));
        Button submit = v.findViewById(R.id.submit);
        submit.setClickable(true);
    }

    private void submit(){
        MainActivity.hideKeyboardFrom(getContext(),v);
        Button submit = v.findViewById(R.id.submit);
        submit.setClickable(false);
        String current = hiragana.get(currIndex);
        String currentCorrect = HiraganaTable.toRomajiMap.get(current);
        String userAnswer = ((EditText)v.findViewById(R.id.answer)).getText().toString();

        Log.d("current",currentCorrect);
        boolean correct = false;

        // check if equals the answer in map + check for exceptions
        if(userAnswer.equals(currentCorrect)) correct = true;
        else if(current.equals("つ") && userAnswer.equals("tu")) correct = true;
        else if(current.equals("ち")&&userAnswer.equals("ti")) correct = true;
        else if(current.equals("ふ")&&userAnswer.equals("hu")) correct = true;
        else if(current.equals("し")&&userAnswer.equals("si")) correct = true;

        TextView cw_view = v.findViewById(R.id.correct_wrong);
        Context c = getContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        if(correct){
            cw_view.setText(Html.fromHtml(getString(R.string.correct)));
            if(pref.getBoolean("count",false)){
                points++;
                TextView pointView = v.findViewById(R.id.points_h);
                pointView.setText(String.format(getResources().getQuantityString(R.plurals.points,points),points,hiragana.size()));
            }
        }
        else{
            cw_view.setText(Html.fromHtml(String.format(getString(R.string.wrong),currentCorrect)));
        }

        Button next = v.findViewById(R.id.next);
        next.setVisibility(View.VISIBLE);
    }

    private void next(){
        currIndex++;
        // if was last, show box
        if(currIndex == hiragana.size()){
            AlertDialog.Builder d = new AlertDialog.Builder(this.getContext());
            d.setTitle(getString(R.string.end));
            d.setMessage(getString(R.string.ok_return));
            d.setCancelable(false);
            d.setPositiveButton("Ok", (dialog, which) -> {
                Fragment f = null;
                try {
                    f = HiraganaWritingStartFragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
            });
            d.create().show();
            return;
        }
        Button next = v.findViewById(R.id.next);
        next.setVisibility(View.GONE);

        EditText ui = v.findViewById(R.id.answer);
        ui.setText("");

        TextView correct = v.findViewById(R.id.correct_wrong);
        correct.setText("");

        showHiragana();
    }
}