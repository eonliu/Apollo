package com.eonliu.apollo.ui.es;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.Environment;
import com.eonliu.apollo.Environments;
import com.eonliu.apollo.Registry;
import com.eonliu.apollo.ui.AbstractActivity;
import com.eonliu.apollo.ui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.eonliu.apollo.Apollo.APOLLO_ENVIRONMENT_FILE;
import static com.eonliu.apollo.Apollo.APOLLO_ENVIRONMENT_KEY;
import static com.eonliu.apollo.Apollo.APOLLO_LOG_SWITCH_FILE;
import static com.eonliu.apollo.Apollo.APOLLO_LOG_SWITCH_KEY;

/**
 * 环境切换页面
 *
 * @author Eon Liu
 */
public class EnvironmentSwitcherActivity extends AbstractActivity {

    private List<String> mEnvironmentGroups = new ArrayList<>();
    private List<Environment> mCurrentEnvironments = new ArrayList<>();
    private SharedPreferences mLogPreferences;
    private SharedPreferences mEnvPreferences;
    private DefaultEnvironmentAdapter mDefaultEnvironmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apollo_environment_switcher_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView titleText = findViewById(R.id.titleText);

        titleText.setText(R.string.apollo_console_environment_switcher);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mLogPreferences = getSharedPreferences(APOLLO_LOG_SWITCH_FILE, Context.MODE_PRIVATE);
        mEnvPreferences = getSharedPreferences(APOLLO_ENVIRONMENT_FILE, Context.MODE_PRIVATE);


        initLogSwitch();

        String currentGroup = mEnvPreferences.getString(APOLLO_ENVIRONMENT_KEY, "");

        List<Environment> allEnvironment = Registry.getAllEnvironment();

        for (Environment env : allEnvironment) {
            if (env == null) continue;
            // groups
            if (!mEnvironmentGroups.contains(env.getGroup())) {
                mEnvironmentGroups.add(env.getGroup());
            }

            // 当前环境集合
            if (currentGroup == null || currentGroup.isEmpty()) {
                if (env.isDefault()) {
                    mCurrentEnvironments.add(new Environment(env));
                }
            } else {
                if (env.getGroup().equals(currentGroup)) {
                    mCurrentEnvironments.add(new Environment(env));
                }
            }
        }

        initSpinner(currentGroup);
        initDefaultEnvironments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EnvironmentChangeActivity.REQUEST_CODE) {
            if (data != null) {
                Environment resultEnvironment = (Environment) data.getSerializableExtra(EnvironmentChangeActivity.INTENT_ENVIRONMENT);
                if (resultEnvironment != null) {
                    Map<String, List<Environment>> moduleMap = Registry.getRegistryMap().get(resultEnvironment.getClassName());
                    if (moduleMap != null) {
                        List<Environment> environments = moduleMap.get(resultEnvironment.getFieldName());
                        if (environments != null) {
                            for (Environment e : environments) {
                                if (e.getUrl().equals(resultEnvironment.getUrl())) {
                                    // 更新
                                    Registry.updateDefaultEnvironment(e.getClassName(), e.getFieldName(), e);
                                    e.setDefault(true);
                                } else {
                                    e.setDefault(false);
                                }
                            }
                        }
                    }
                    for (Environment e : mCurrentEnvironments) {
                        if (e.getClassName().equals(resultEnvironment.getClassName()) && e.getFieldName().equals(resultEnvironment.getFieldName())) {
                            e.setUrl(resultEnvironment.getUrl());
                            e.setDesc(resultEnvironment.getDesc());
                            e.setGroup(resultEnvironment.getGroup());
                            e.setModuleName(resultEnvironment.getModuleName());
                            e.setDefault(resultEnvironment.isDefault());
                        }
                    }
                    mDefaultEnvironmentAdapter.setEnvironments(mCurrentEnvironments);
                    mDefaultEnvironmentAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void initDefaultEnvironments() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mDefaultEnvironmentAdapter = new DefaultEnvironmentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mDefaultEnvironmentAdapter);
        mDefaultEnvironmentAdapter.setEnvironments(mCurrentEnvironments);
        mDefaultEnvironmentAdapter.notifyDataSetChanged();
    }

    private void initSpinner(String currentGroup) {
        final Spinner spinner = findViewById(R.id.environment_spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.apollo_environment_spinner_item, mEnvironmentGroups));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String group = mEnvironmentGroups.get(position);
                mEnvPreferences.edit()
                        .putString(APOLLO_ENVIRONMENT_KEY, group)
                        .apply();

                mCurrentEnvironments.clear();
                Map<String, Map<String, List<Environment>>> sRegistryMap = Registry.getRegistryMap();
                for (String module : sRegistryMap.keySet()) {
                    Map<String, List<Environment>> moduleMap = sRegistryMap.get(module);
                    if (moduleMap != null) {
                        for (String field : moduleMap.keySet()) {
                            List<Environment> environments = moduleMap.get(field);
                            if (environments != null && !environments.isEmpty()) {
                                Environment defaultEnv = null;
                                Environment currentEnv = null;
                                for (Environment env : environments) {
                                    // 通过isDefault区分当前环境,优先级小于于group
                                    if (env.isDefault()) {
                                        defaultEnv = env;
                                    }
                                    // 通过group区分当前环境
                                    if (env.getGroup().equals(group)) {
                                        currentEnv = env;
                                    }
                                    env.setDefault(env.getGroup().equals(group));
                                }
                                if (currentEnv != null && !mCurrentEnvironments.contains(currentEnv)) {
                                    mCurrentEnvironments.add(new Environment(currentEnv));
                                    Registry.updateDefaultEnvironment(module, field, currentEnv);
                                } else {
                                    if (defaultEnv != null) {
                                        mCurrentEnvironments.add(new Environment(defaultEnv));
                                        Registry.updateDefaultEnvironment(module, field, defaultEnv);
                                    }
                                }
                            }
                        }
                    }
                }

                mDefaultEnvironmentAdapter.setEnvironments(mCurrentEnvironments);
                mDefaultEnvironmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (currentGroup != null && !currentGroup.isEmpty()) {
            for (int i = 0; i < mEnvironmentGroups.size(); i++) {
                if (mEnvironmentGroups.get(i).equals(currentGroup)) {
                    spinner.setSelection(i);
                }
            }
        }
    }

    private void initLogSwitch() {
        final SwitchCompat logSwitch = findViewById(R.id.log_switch);
        logSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> Environments.getInstance().setLogSwitch(isChecked));
        boolean logIsChecked = mLogPreferences.getBoolean(APOLLO_LOG_SWITCH_KEY, false);
        Environments.getInstance().setLogSwitch(logIsChecked);
        logSwitch.setChecked(logIsChecked);
    }
}