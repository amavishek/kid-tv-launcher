package com.exceldroid.kidstvlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView appsGrid;
    private PackageManager packageManager;
    private List<AppInfo> approvedApps = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();
        appsGrid = findViewById(R.id.appsGrid);

        loadApprovedApps();
        setupGridView();
        isAppApproved("com.exceldroid.kidstvlauncher");
    }

    private void loadApprovedApps() {
        try {
            // Pre-approved apps with proper resource handling
            approvedApps.add(new AppInfo(
                    "Kids YouTube",
                    "com.exceldroid.kidstvlauncher.youtube",
                    getDrawableCompat(R.drawable.youtube)
            ));

            approvedApps.add(new AppInfo(
                    "kids Netflix",
                    "com.exceldroid.kidstvlauncher.netflix",
                    getDrawableCompat(R.drawable.netflix)
            ));



        } catch (Exception e) {
            Toast.makeText(this, "Error loading apps", Toast.LENGTH_SHORT).show();
        }
    }

    private Drawable getDrawableCompat(int resId) {
        return getResources().getDrawable(resId, getTheme());
    }

    private void setupGridView() {
        if (approvedApps.isEmpty()) {
            Toast.makeText(this, "No approved apps found", Toast.LENGTH_SHORT).show();
            return;
        }

        AppsAdapter adapter = new AppsAdapter(this, approvedApps);
        appsGrid.setAdapter(adapter);

        appsGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < approvedApps.size()) {
                AppInfo app = approvedApps.get(position);
                try {
                    Intent launchIntent = packageManager.getLaunchIntentForPackage(app.packageName);
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {
                        Toast.makeText(this, "App not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (SecurityException e) {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, PinActivity.class));
        // Don't call super to prevent default back behavior
    }

    private boolean isAppApproved(String packageName) {
        if (packageName == null) return false;

        for (AppInfo app : approvedApps) {
            if (packageName.equals(app.packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        blockRecentApps();
    }

    private void blockRecentApps() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
                return true; // Block recent apps button
            }
            return false;
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_MENU:
                case KeyEvent.KEYCODE_SETTINGS:
                    // Block menu and settings buttons
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}