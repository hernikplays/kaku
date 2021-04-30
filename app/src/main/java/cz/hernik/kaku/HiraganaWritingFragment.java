package cz.hernik.kaku;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HiraganaWritingFragment extends Fragment {
    private int currIndex = 0;
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
        String[] hiraganaArr = this.getArguments().getStringArray("hiraganalist");
        List<String> hiragana = new ArrayList<>(Arrays.asList(hiraganaArr));
        Collections.shuffle(hiragana);

        //TODO: hiragana quiz
    }
}