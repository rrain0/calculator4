package com.rrain.calculator4

import android.app.Application
import com.rrain.calculator4.db.DBManager

class App : Application() {
  
  companion object {
    private var app: App? = null
    fun getApp() = app
  }
  
  private var dbManager: DBManager? = null
  fun getDbManager() = dbManager
  
  private var settingsManager: SettingsManager? = null
  fun getSettingsManager() = settingsManager
  
  private var historyManager: HistoryManager? = null
  fun getHistoryManager() = historyManager
  
  private var themeManager: ThemeManager? = null
  fun getThemeManager() = themeManager
  
  private var clipboard: Clipboard? = null
  fun getClipboard() = clipboard
  
  //private lateinit var displayManager: DisplayManager
  override fun onCreate() {
    super.onCreate()
    app = this
    
    dbManager = DBManager()
    settingsManager = SettingsManager()
    themeManager = ThemeManager()
    historyManager = HistoryManager(dbManager!!.historyEntryDao)
    clipboard = Clipboard()
    
    //displayManager = DisplayManager()
    AppElevator.upgrade()
  }
}
