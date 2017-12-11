package com.patrickfeltes.graphingprogram.userinterface.graph_menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.patrickfeltes.graphingprogram.R;

public class NewGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_graph);

        Button createGraphButton = findViewById(R.id.b_create_graph);
        final EditText graphName = findViewById(R.id.et_graph_name);
        createGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (graphName.getText().toString().trim().length() != 0) {
                    Intent result = new Intent();
                    result.putExtra("graphName", graphName.getText().toString());
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });
    }

}
