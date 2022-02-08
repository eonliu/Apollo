package com.eonliu.apollo.ui.es;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.Environment;
import com.eonliu.apollo.ui.R;

import java.util.List;

import static com.eonliu.apollo.ui.es.EnvironmentChangeActivity.INTENT_ENVIRONMENT;

/**
 * 默认环境列表适配器
 *
 * @author Eon Liu
 */
public class EnvironmentSelectorAdaptor extends RecyclerView.Adapter<EnvironmentSelectorAdaptor.VH> {

    private List<Environment> mEnvironments;
    private EnvironmentChangeActivity mEnvironmentChangeActivity;

    public EnvironmentSelectorAdaptor(EnvironmentChangeActivity environmentChangeActivity) {
        mEnvironmentChangeActivity = environmentChangeActivity;
    }

    public void setEnvironments(List<Environment> environments) {
        this.mEnvironments = environments;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.apollo_environment_default_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if (mEnvironments == null) {
            return 0;
        } else {
            return mEnvironments.size();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Environment environment = mEnvironments.get(position);
        holder.moduleNameText.setText(environment.getModuleName());
        holder.descText.setText(environment.getDesc());
        holder.urlText.setText(environment.getUrl());
        holder.groupText.setText(environment.getGroup());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra(INTENT_ENVIRONMENT, environment);
            mEnvironmentChangeActivity.setResult(Activity.RESULT_OK, intent);
            mEnvironmentChangeActivity.finish();
        });
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView moduleNameText;
        TextView descText;
        TextView urlText;
        TextView groupText;

        public VH(@NonNull View itemView) {
            super(itemView);
            moduleNameText = itemView.findViewById(R.id.moduleText);
            descText = itemView.findViewById(R.id.descText);
            urlText = itemView.findViewById(R.id.urlText);
            groupText = itemView.findViewById(R.id.groupText);
        }
    }
}
