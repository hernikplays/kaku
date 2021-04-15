package cz.hernikplays.kaku;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KanjiWritingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanji_writing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context c = this.getContext();

        // open kanji list
        boolean foundFile = true;

        FileInputStream fis = null;
        try {
            fis = c.openFileInput("kanjilist");
        } catch (FileNotFoundException e) {
            foundFile = false;
        }

        if(foundFile){
            // when found file, read it
            String content;

            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                content = stringBuilder.toString();
            }
            Log.d("DEBUG", content);
        }
        else{
            // else show dialog
            AlertDialog.Builder d = new AlertDialog.Builder(this.getContext());
            d.setTitle("Missing Kanji List");
            d.setMessage("You need to create a kanji list. Press Ok to move to the kanji list creation screen.");
            d.setCancelable(false);
            d.setPositiveButton("Ok", (dialog, which) -> {
                Fragment f = null;
                try {
                    f = SecondFragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
            });
            d.create().show();
        }
    }
}