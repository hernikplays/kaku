package cz.hernik.kaku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondFragment extends Fragment {
    public View mainView;
    private Context c;
    private Dialog dialog;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = view;
        EditText kanjiList = view.findViewById(R.id.kanjiList);

        // load kanji list to edittext if it exists
        boolean foundFile = true;
        c = this.getContext();
        FileInputStream fis = null;
        try {
            fis = c.openFileInput("kanjilist");
        } catch (FileNotFoundException e) {
            foundFile = false;
        }
        if (foundFile) {
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
            kanjiList.setText(content);
        }

        // set save button listener
        view.findViewById(R.id.saveKanji).setOnClickListener(v -> {
            String text = kanjiList.getText().toString();

            final Pattern pattern = Pattern.compile("[\u3005\u3400-\u4DB5\u4E00-\u9FCB\uF900-\uFA6A]", Pattern.MULTILINE); // <3 https://github.com/cubetastic33/sakubun/blob/a87d6cf9c686173663955ef93e82baaec5cbc0ec/static/scripts/known_kanji.js#L57
            final Matcher matcher = pattern.matcher(text);

            // save all kanji to a string
            StringBuilder kanji = new StringBuilder();
            while (matcher.find()) {
                kanji.append(matcher.group(0));
            }

            // save to file
            SaveKanji(kanji.toString().getBytes());

            EditText kanjiEdit = mainView.findViewById(R.id.kanjiList);
            kanjiEdit.setText(kanji.toString());
        });

        // set WK button
        view.findViewById(R.id.importwk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(() -> {
                    try {
                        WKImport();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                th.start();
            }
        });
    }

    private void WKImport() throws IOException, InterruptedException {
        EditText wk = mainView.findViewById(R.id.wkApiKey);
        String apiKey = wk.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setView(R.layout.progress);
        builder.setCancelable(false);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = builder.create();
                dialog.show();
            }
        });

        String url = "https://api.wanikani.com/v2/assignments?subject_types=kanji";
        OkHttpClient client = new OkHttpClient();
        // get assignment info
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + apiKey)
                .build();
        List<String> assignments = null;
        while (!url.equals("null")) {
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 401) {
                    this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.hide();
                            AlertDialog.Builder e = new AlertDialog.Builder(c);
                            e.setTitle("Wrong API Key");
                            e.setMessage("This API key does not seem valid. :(");
                            e.setCancelable(true);
                            e.setPositiveButton("Ok", null);
                            e.create().show();
                        }
                    });
                    url = "null";
                    return;
                } else {
                    JSONObject res = new JSONObject(response.body().string());
                    Log.d("DEBUG", res.toString());
                    JSONArray data = (JSONArray) res.get("data");
                    assignments = new ArrayList<>();
                    // add assignments with SRS higher than 5
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject o = data.getJSONObject(i);
                        JSONObject info = o.getJSONObject("data");
                        int srs = info.getInt("srs_stage");
                        if (srs > 5) {
                            assignments.add("https://api.wanikani.com/v2/subjects/" + info.getInt("subject_id"));
                        }
                    }
                    JSONObject pages = res.getJSONObject("pages");
                    url = pages.getString("next_url");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // get the kanji
        StringBuilder kanji = new StringBuilder();
        for (String link :
                assignments) {
            request = new Request.Builder()
                    .url(link)
                    .header("Authorization", "Bearer " + apiKey)
                    .build();
            TimeUnit.SECONDS.sleep(1);
            try (Response response = client.newCall(request).execute()) {
                JSONObject res = new JSONObject(response.body().string());
                JSONObject data = res.getJSONObject("data");
                String ch = data.getString("characters");
                kanji.append(ch);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SaveKanji(kanji.toString().getBytes());
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.hide();
            }
        });
    }

    private void SaveKanji(byte[] text){
        try (FileOutputStream fos = c.openFileOutput("kanjilist", Context.MODE_PRIVATE)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}