package com.rrain.calculator4;

import android.annotation.SuppressLint;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;

public class ThemeManager {
    @Getter private Map<String, Theme> themes;
    public static final String defaultTheme = "LightYellow";

    public ThemeManager() {
        themes = new LinkedHashMap<>(); //LinkedHashMap сохраняет порядок добавления

        themes.put("Light", new Theme("Light", R.style.AppThemeLight, App.getApp().getString(R.string.theme_light)));

        themes.put("LightYellow", new Theme("LightYellow", R.style.AppThemeLightYellow, App.getApp().getString(R.string.theme_light_yellow)));//default theme
        themes.put("LightLightPink", new Theme("LightLightPink", R.style.AppThemeLightLightPink, App.getApp().getString(R.string.theme_light_light_pink)));
        themes.put("LightPink", new Theme("LightPink", R.style.AppThemeLightPink, App.getApp().getString(R.string.theme_light_pink)));
        themes.put("LightGray", new Theme("LightGray", R.style.AppThemeLightGrey, App.getApp().getString(R.string.theme_light_gray)));
        themes.put("LightViolet", new Theme("LightViolet", R.style.AppThemeLightViolet, App.getApp().getString(R.string.theme_light_violet)));

        themes.put("LightBlue", new Theme("LightBlue", R.style.AppThemeLightBlue, App.getApp().getString(R.string.theme_light_blue)));
        themes.put("LightPurple", new Theme("LightPurple", R.style.AppThemeLightPurple, App.getApp().getString(R.string.theme_light_purple)));
        themes.put("LightLightBlue", new Theme("LightLightBlue", R.style.AppThemeLightLightBlue, App.getApp().getString(R.string.theme_light_light_blue)));
        themes.put("LightGreen", new Theme("LightGreen", R.style.AppThemeLightGreen, App.getApp().getString(R.string.theme_light_green)));
        themes.put("LightOrange", new Theme("LightOrange", R.style.AppThemeLightOrange, App.getApp().getString(R.string.theme_light_orange)));
        themes.put("LightRed", new Theme("LightRed", R.style.AppThemeLightRed, App.getApp().getString(R.string.theme_light_red)));

        themes.put("Dark", new Theme("Dark", R.style.AppThemeDark, App.getApp().getString(R.string.theme_dark)));
        themes.put("DarkBlue", new Theme("DarkBlue", R.style.AppThemeDarkBlue, App.getApp().getString(R.string.theme_dark_blue)));
        themes.put("DarkGreen", new Theme("DarkGreen", R.style.AppThemeDarkGreen, App.getApp().getString(R.string.theme_dark_green)));
        themes.put("DarkPurple", new Theme("DarkPurple", R.style.AppThemeDarkPurple, App.getApp().getString(R.string.theme_dark_purple)));
        themes.put("DarkYellow", new Theme("DarkYellow", R.style.AppThemeDarkYellow, App.getApp().getString(R.string.theme_dark_yellow)));
        themes.put("DarkPink", new Theme("DarkPink", R.style.AppThemeDarkPink, App.getApp().getString(R.string.theme_dark_pink)));
        themes.put("DarkOrange", new Theme("DarkOrange", R.style.AppThemeDarkOrange, App.getApp().getString(R.string.theme_dark_orange)));
        themes.put("DarkRed", new Theme("DarkRed", R.style.AppThemeDarkRed, App.getApp().getString(R.string.theme_dark_red)));
    }

    @SuppressLint("NewApi")
    public int getStyleIdByName(String name){
        return Objects.requireNonNullElse(themes.get(name), themes.get(defaultTheme)).getThemeStyleId();
    }
}
