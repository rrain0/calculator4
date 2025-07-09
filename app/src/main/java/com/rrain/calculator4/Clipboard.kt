package com.rrain.calculator4

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.rrain.calculator4.App.Companion.getApp

class Clipboard {
  private val clipboardManager: ClipboardManager
  
  init {
    clipboardManager = getApp()!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  }
  
  fun add(s: String?) {
    clipboardManager.setPrimaryClip(ClipData.newPlainText("text", s))
  }
  
  val last: String get() = (
    clipboardManager.primaryClip?.getItemAt(0)?.text.toString() ?: ""
  )
}
