package com.rrain.calculator4

import android.annotation.SuppressLint
import com.rrain.calculator4.App.Companion.getApp
import java.util.Objects

class ThemeManager {
  
  private val themes: MutableMap<String, Theme>
  fun getThemes() = themes
  
  init {
    themes = LinkedHashMap<String, Theme>() // LinkedHashMap сохраняет порядок добавления
    
    themes.put("Light", Theme("Light", R.style.AppThemeLight, getApp()!!.getString(R.string.theme_light)))
    
    themes.put(
      "LightYellow",
      Theme("LightYellow", R.style.AppThemeLightYellow, getApp()!!.getString(R.string.theme_light_yellow))
    ) // default theme
    themes.put(
      "LightLightPink",
      Theme("LightLightPink", R.style.AppThemeLightLightPink, getApp()!!.getString(R.string.theme_light_light_pink))
    )
    themes.put(
      "LightPink",
      Theme("LightPink", R.style.AppThemeLightPink, getApp()!!.getString(R.string.theme_light_pink))
    )
    themes.put(
      "LightGray",
      Theme("LightGray", R.style.AppThemeLightGrey, getApp()!!.getString(R.string.theme_light_gray))
    )
    themes.put(
      "LightViolet",
      Theme("LightViolet", R.style.AppThemeLightViolet, getApp()!!.getString(R.string.theme_light_violet))
    )
    
    themes.put(
      "LightBlue",
      Theme("LightBlue", R.style.AppThemeLightBlue, getApp()!!.getString(R.string.theme_light_blue))
    )
    themes.put(
      "LightPurple",
      Theme("LightPurple", R.style.AppThemeLightPurple, getApp()!!.getString(R.string.theme_light_purple))
    )
    themes.put(
      "LightLightBlue",
      Theme("LightLightBlue", R.style.AppThemeLightLightBlue, getApp()!!.getString(R.string.theme_light_light_blue))
    )
    themes.put(
      "LightGreen",
      Theme("LightGreen", R.style.AppThemeLightGreen, getApp()!!.getString(R.string.theme_light_green))
    )
    themes.put(
      "LightOrange",
      Theme("LightOrange", R.style.AppThemeLightOrange, getApp()!!.getString(R.string.theme_light_orange))
    )
    themes.put("LightRed", Theme("LightRed", R.style.AppThemeLightRed, getApp()!!.getString(R.string.theme_light_red)))
    
    themes.put("Dark", Theme("Dark", R.style.AppThemeDark, getApp()!!.getString(R.string.theme_dark)))
    themes.put("DarkBlue", Theme("DarkBlue", R.style.AppThemeDarkBlue, getApp()!!.getString(R.string.theme_dark_blue)))
    themes.put(
      "DarkGreen",
      Theme("DarkGreen", R.style.AppThemeDarkGreen, getApp()!!.getString(R.string.theme_dark_green))
    )
    themes.put(
      "DarkPurple",
      Theme("DarkPurple", R.style.AppThemeDarkPurple, getApp()!!.getString(R.string.theme_dark_purple))
    )
    themes.put(
      "DarkYellow",
      Theme("DarkYellow", R.style.AppThemeDarkYellow, getApp()!!.getString(R.string.theme_dark_yellow))
    )
    themes.put("DarkPink", Theme("DarkPink", R.style.AppThemeDarkPink, getApp()!!.getString(R.string.theme_dark_pink)))
    themes.put(
      "DarkOrange",
      Theme("DarkOrange", R.style.AppThemeDarkOrange, getApp()!!.getString(R.string.theme_dark_orange))
    )
    themes.put("DarkRed", Theme("DarkRed", R.style.AppThemeDarkRed, getApp()!!.getString(R.string.theme_dark_red)))
  }
  
  
  fun getStyleIdByName(name: String?): Int {
    return (themes[name] ?: themes[defaultTheme])!!.getThemeStyleId()
  }
  
  companion object {
    const val defaultTheme: String = "LightYellow"
  }
}
