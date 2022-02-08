package com.eonliu.apollo.ui.es;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.Environment;
import com.eonliu.apollo.ui.R;

import java.util.List;

/**
 * 默认环境列表适配器
 *
 * @author Eon Liu
 */
public class DefaultEnvironmentAdapter extends RecyclerView.Adapter<DefaultEnvironmentAdapter.VH> {

    private List<Environment> mEnvironments;
    private EnvironmentSwitcherActivity mActivity;

    public DefaultEnvironmentAdapter(EnvironmentSwitcherActivity activity) {
        mActivity = activity;
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
            EnvironmentChangeActivity.starterForResult(mActivity, environment);
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
