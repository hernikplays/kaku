package cz.hernik.kaku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button b=findViewById(R.id.github);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hernikplays/kaku"));
                startActivity(browserIntent);
            }
        });
        findViewById(R.id.licenses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bi = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/hernikplays/kaku/blob/main/LICENSES.md"));
                startActivity(bi);
            }
        });
    }

}