package com.eonliu.apollo.ui.console;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.ui.R;
import com.eonliu.apollo.ui.es.EnvironmentSwitcherActivity;
import com.eonliu.apollo.util.Utils;

import java.util.List;

/**
 * @author Eon Liu
 */
public class ConsoleModuleAdapter extends RecyclerView.Adapter<ConsoleModuleAdapter.VH> {

    private final List<ConsoleItem> mConsoleItems;
    private final Context mContext;

    public ConsoleModuleAdapter(Context context, List<ConsoleItem> consoleItems) {
        this.mContext = context;
        this.mConsoleItems = consoleItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.apollo_console_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if (mConsoleItems == null) {
            return 0;
        } else {
            return mConsoleItems.size();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        ConsoleItem consoleItem = mConsoleItems.get(position);
        holder.icon.setImageResource(consoleItem.getDrawable());
        holder.name.setText(consoleItem.getName());
        holder.itemView.setOnClickListener(view -> {
            switch (consoleItem.getType()) {
                case ConsoleData.TYPE_ENVIRONMENT_SWITCHER: // 环境切换
                    mContext.startActivity(new Intent(mContext, EnvironmentSwitcherActivity.class));
                    break;
                case ConsoleData.TYPE_DEVELOPMENT: // 开发者选项
                    Utils.startDevelopmentActivity(mContext);
                case ConsoleData.TYPE_APP_INFO: // App信息
                    Utils.startAppSettingsActivity(mContext);
                    break;
                case ConsoleData.TYPE_LANGUAGE: // 语言设置
                    Utils.startLanguageActivity(mContext);
                    break;
                case ConsoleData.TYPE_DEVICE_INFO: // 关于手机
                    Utils.startDeviceInfoActivity(mContext);
                    break;
            }
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        public VH(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}
