package cz.hernik.kaku;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class KanjiWritingStartFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanji_writing_start, container, false);
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
            String mes = String.format(c.getResources().getString(R.string.wrs_kanjicount),String.valueOf(content.length()));
            TextView tw = view.findViewById(R.id.messview);
            tw.setText(mes);

            view.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWriting(content);
                }
            });
        }
        else{
            // else show dialog
            AlertDialog.Builder d = new AlertDialog.Builder(this.getContext());
            d.setTitle(getString(R.string.missing_list_title));
            d.setMessage(getString(R.string.missing_list_message));
            d.setCancelable(false);
            d.setPositiveButton("Ok", (dialog, which) -> {
                Fragment f = null;
                try {
                    f = SecondFragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.changeChecked();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
            });
            d.create().show();
        }


    }

    private void startWriting(String kanji){
        Fragment f = null;
        try {
            f = KanjiWritingFragment.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // set kanji list as argument and pass it to the KanjiWriting Fragment and transition to it
        Bundle args = new Bundle();
        args.putString("kanjilist",kanji);
        f.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
    }
}