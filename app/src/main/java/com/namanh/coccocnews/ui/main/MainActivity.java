package com.namanh.coccocnews.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.namanh.coccocnews.R;
import com.namanh.coccocnews.helper.DarkModePreference;
import com.namanh.coccocnews.ui.common.FragmentFactory;
import com.namanh.coccocnews.ui.common.NavigationController;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final String ACTION_OPEN_MAIN = "coccocnews.intent.action.MAIN";

    private NavigationController navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int nightMode = DarkModePreference.getInstance(getApplicationContext()).getDarkMode();
        AppCompatDelegate.setDefaultNightMode(nightMode);
        setTheme(nightMode == AppCompatDelegate.MODE_NIGHT_YES ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_main);

        navigation = NavigationController.getInstance();
        navigation.init(this);
        getLifecycle().addObserver(navigation);

        ScreenModel screen = ScreenModel.createScreen(FragmentFactory.SCREEN.CATEGORY, null, false, false, false);
        navigation.setScreen(screen);
    }

}
