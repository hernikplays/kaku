package cz.hernik.kaku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import cz.hernik.kaku.helper.DrawView;

public class DrawActivity extends AppCompatActivity {
    private Toolbar mToolbar_bottom;
    private DrawView drawV;
    private List<String> kanji;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        loadList();

        mToolbar_bottom = findViewById(R.id.bottom_toolbar);
        mToolbar_bottom.inflateMenu(R.menu.menu_drawing);
        mToolbar_bottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleDrawingIconTouched(item.getItemId());
                return false;
            }
        });
        drawV = findViewById(R.id.drawView);
        index = 0;
        drawV.setCurrent(kanji.get(index));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void handleDrawingIconTouched(int itemId) {
        switch (itemId){
            case R.id.action_erase:
                drawV.eraseAll();
                break;
            case R.id.action_next:
                index+=1;
                if(index == kanji.size()-1){
                    AlertDialog.Builder d = new AlertDialog.Builder(this);
                    d.setTitle(getString(R.string.end));
                    d.setMessage(getString(R.string.ok_return));
                    d.setCancelable(false);
                    d.setPositiveButton("Ok", (dialog, which) -> {
                        finish();
                    });
                    d.create().show();
                }
                else {
                    drawV.setCurrent(kanji.get(index));
                    drawV.eraseAll();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawing, menu);
        return true;
    }

    private void loadList(){ // loads kanji list to private var
        boolean foundFile = true;

        FileInputStream fis = null;
        try {
            fis = this.openFileInput("kanjilist");
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
            kanji = Arrays.asList(content.split("(?!^)"));
        }
        else{
            // else show dialog
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(getString(R.string.missing_list_title));
            d.setMessage(getString(R.string.missing_list_message_alt));
            d.setCancelable(false);
            d.setPositiveButton("Ok", (dialog, which) -> {
                finish();
            });
            d.create().show();
        }
    }

}