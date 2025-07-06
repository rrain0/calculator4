package com.rrain.calculator4

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
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
  
  //@Getter private DisplayManager displayManager;
  override fun onCreate() {
    super.onCreate()
    app = this
    AndroidThreeTen.init(this)
    
    dbManager = DBManager()
    settingsManager = SettingsManager()
    themeManager = ThemeManager()
    historyManager = HistoryManager(dbManager!!.getHistoryEntryDao())
    clipboard = Clipboard()
    
    // displayManager = new DisplayManager();
    AppElevator.upgrade()
  }
}
