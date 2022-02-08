package com.eonliu.apollo.ui.console;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.ui.AbstractActivity;
import com.eonliu.apollo.ui.R;

/**
 * Apollo主页面
 *
 * @author Eon Liu
 */
public class LauncherActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apollo_launcher_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(this);
        consoleAdapter.setConsoleModuleItems(ConsoleData.getConsoleData());
        recyclerView.setAdapter(consoleAdapter);
    }
}