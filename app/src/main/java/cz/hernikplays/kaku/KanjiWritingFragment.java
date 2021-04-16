package cz.hernikplays.kaku;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KanjiWritingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanji_writing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        String knownKanji = this.getArguments().getString("kanjilist");
        AssetManager assetManager = getActivity().getAssets();
        String text = null;

        // load tatoeba strings
        //! Probably place this on a new thread!
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

        // get strings with known kanji
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
                    Log.d("debugg","YES");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}