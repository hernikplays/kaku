package cz.hernik.kaku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private static NavigationView nvDrawer;
    public static List<String> kanjiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change theme when starting
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getBoolean("customtheme",false)){
            switch (pref.getString("theme","light")){
                case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        Fragment f = null;
        try {
            f = FirstFragment.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // load kanji list
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
            kanjiList = Arrays.asList(content.split("(?!^)"));
        }
        else{
            kanjiList = new ArrayList<>();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_second_fragment) {
            fragmentClass = SecondFragment.class;
        } else if (itemId == R.id.nav_third_fragment) {
            fragmentClass = KanjiWritingStartFragment.class;
        } else if (itemId == R.id.nav_hiragana_start){
            fragmentClass = HiraganaWritingStartFragment.class;
        }
        else if(itemId == R.id.nav_drawing){
            if(kanjiList.size() < 1){
                Toast.makeText(this, getString(R.string.kanjilist_toast), Toast.LENGTH_SHORT).show();
                menuItem.setChecked(false);
            }
            else {
                setTitle(menuItem.getTitle());
                mDrawer.closeDrawers();
                Intent intent = new Intent(this, DrawActivity.class);
                startActivity(intent);
            }
            return;
        }
        else {
            fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);

        }

        return super.onOptionsItemSelected(item);
    }

    public static void changeChecked(){
        nvDrawer.setCheckedItem(R.id.nav_second_fragment);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}