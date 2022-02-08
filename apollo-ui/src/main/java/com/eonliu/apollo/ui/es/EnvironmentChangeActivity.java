package com.eonliu.apollo.ui.es;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.Environment;
import com.eonliu.apollo.Registry;
import com.eonliu.apollo.ui.AbstractActivity;
import com.eonliu.apollo.ui.R;

import java.util.List;

/**
 * 切换单个功能模块环境
 *
 * @author Eon Liu
 */
public class EnvironmentChangeActivity extends AbstractActivity {

    public static final int REQUEST_CODE = 10001;
    protected static final String INTENT_ENVIRONMENT = "environment";
    private static final String TAG = "Apollo";
    private Environment mEnvironment;

    public static void starterForResult(Activity activity, Environment environment) {
        final Intent intent = new Intent(activity, EnvironmentChangeActivity.class);
        intent.putExtra(INTENT_ENVIRONMENT, environment);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apollo_environment_change_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView titleText = findViewById(R.id.titleText);

        mEnvironment = (Environment) getIntent().getSerializableExtra(INTENT_ENVIRONMENT);
        if (mEnvironment == null) {
            Log.e(TAG, "Environment is null.");
            return;
        }
        titleText.setText(String.format("%s-%s", mEnvironment.getModuleName(), mEnvironment.getDesc()));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EnvironmentSelectorAdaptor adaptor = new EnvironmentSelectorAdaptor(this);
        List<Environment> environments = Registry.getEnvironments(mEnvironment.getClassName(), mEnvironment.getFieldName());
        adaptor.setEnvironments(environments);
        recyclerView.setAdapter(adaptor);
    }

}