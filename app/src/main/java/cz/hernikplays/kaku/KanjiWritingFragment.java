package cz.hernikplays.kaku;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KanjiWritingFragment extends Fragment {

    private Dialog dialog;
    private Context c;
    private Dialog loadDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanji_writing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        c = getContext();
        String knownKanji = this.getArguments().getString("kanjilist");
        AssetManager assetManager = getActivity().getAssets();

        // load tatoeba strings
        Thread th = new Thread(()->{
            Handler handler = new Handler(getActivity().getMainLooper());
            String text = null;

            // build and show loading sentences
            View alertView = getLayoutInflater().inflate(R.layout.progress,null);
            TextView info = alertView.findViewById(R.id.loading_msg);
            info.setText(getActivity().getString(R.string.msg_loading_sentences));
            AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
            builder1.setView(alertView);
            builder1.setCancelable(false);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadDialog = builder1.create();
                    loadDialog.show();
                }
            });
            JSONArray sentences = null;
            try {
                InputStream stream = assetManager.open("tatoeba_16_4_21.json");

                try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
                    text = scanner.useDelimiter("\\A").next();
                }
                sentences = new JSONArray(text);
                Log.d("DEBUG",sentences.getJSONObject(0).getString("FIELD1"));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            // hide loading dialog
            getActivity().runOnUiThread(() -> loadDialog.hide());

            // show filtering
            View aV = getLayoutInflater().inflate(R.layout.progress,null);
            TextView loading = aV.findViewById(R.id.loading_msg);
            loading.setText(String.format(getActivity().getString(R.string.msg_filtering),String.valueOf(sentences.length())));
            AlertDialog.Builder builder2 = new AlertDialog.Builder(c);
            builder2.setView(aV);
            builder2.setCancelable(false);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = builder2.create();
                    dialog.show();
                }
            });
            // get strings with known kanji
            List<String> knownSentences = new ArrayList<>();
            for(int i = 0;i<sentences.length();i++){
                String sentence = null;
                try {
                    sentence = sentences.getJSONObject(i).getString("FIELD1");
                    final Pattern pattern = Pattern.compile("[\u3005\u3400-\u4DB5\u4E00-\u9FCB\uF900-\uFA6A]", Pattern.MULTILINE); // <3 https://github.com/cubetastic33/sakubun/blob/a87d6cf9c686173663955ef93e82baaec5cbc0ec/static/scripts/known_kanji.js#L57
                    final Matcher matcher = pattern.matcher(sentence);
                    StringBuilder foundKanji = new StringBuilder();
                    while(matcher.find()){
                        foundKanji.append(matcher.group(0));
                    }

                    if(knownKanji.contains(foundKanji.toString())){
                        knownSentences.add(sentence);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Runnable runnable = () -> displayKanji(knownSentences);
            // call back to main threadQ
            handler.post(runnable);
        });
        th.start();
    }

    private void displayKanji(List<String> sentenceList){
        Log.d("debug",sentenceList.get(0));
        dialog.hide();
    }
}