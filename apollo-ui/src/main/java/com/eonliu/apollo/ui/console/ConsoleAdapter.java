package com.eonliu.apollo.ui.console;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eonliu.apollo.ui.BuildConfig;
import com.eonliu.apollo.ui.R;

import java.util.List;

/**
 * 控制台页面表格适配器
 *
 * @author Eon Liu
 */
public class ConsoleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 网格布局
     */
    public final static int ITEM_TYPE_GRID = 0;
    /**
     * 版本号
     */
    public final static int ITEM_TYPE_VERSION = 1;

    private List<ConsoleModuleItem> mConsoleModuleItems;
    private final Context mContext;

    public ConsoleAdapter(Context context) {
        this.mContext = context;
    }

    public void setConsoleModuleItems(List<ConsoleModuleItem> consoleModuleItems) {
        this.mConsoleModuleItems = consoleModuleItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_GRID:
                return new GridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.apollo_console_item_list, parent, false));
            case ITEM_TYPE_VERSION:
                return new VersionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.apollo_console_item_version, parent, false));
        }
        return new GridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.apollo_console_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConsoleModuleItem consoleModuleItem = mConsoleModuleItems.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TYPE_GRID:
                GridViewHolder gridViewHolder = (GridViewHolder) holder;
                gridViewHolder.tvModuleName.setText(consoleModuleItem.getModuleName());
                initGridView(mContext, gridViewHolder.recyclerView, consoleModuleItem.getConsoleItems());
                break;
            case ITEM_TYPE_VERSION:
                VersionViewHolder versionViewHolder = (VersionViewHolder) holder;
                versionViewHolder.tvVersion.setText(String.format("当前版本：V%s", BuildConfig.VERSION_NAME));
                break;
        }
    }

    private void initGridView(Context context, RecyclerView recyclerView, List<ConsoleItem> consoleItem) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(new ConsoleModuleAdapter(context, consoleItem));
    }

    @Override
    public int getItemViewType(int position) {
        return mConsoleModuleItems.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        if (mConsoleModuleItems == null) {
            return 0;
        } else {
            return mConsoleModuleItems.size();
        }
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {

        TextView tvModuleName;
        RecyclerView recyclerView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModuleName = itemView.findViewById(R.id.tvModuleName);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    static class VersionViewHolder extends RecyclerView.ViewHolder {

        TextView tvVersion;

        public VersionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVersion = itemView.findViewById(R.id.tvVersion);
        }
    }
}
