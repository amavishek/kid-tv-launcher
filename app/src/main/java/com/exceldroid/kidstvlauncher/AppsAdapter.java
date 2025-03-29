package com.exceldroid.kidstvlauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppsAdapter extends ArrayAdapter<AppInfo> {
    public AppsAdapter(Context context, List<AppInfo> apps) {
        super(context, 0, apps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo app = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_app, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.appIcon);
        TextView name = convertView.findViewById(R.id.appName);

        icon.setImageDrawable(app.icon);
        name.setText(app.name);

        return convertView;
    }
}
