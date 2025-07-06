package com.rrain.calculator4

class Theme(
  private val name: String?,
  private val themeStyleId: Int,
  private val displayedName: String?
) {
  fun getName() = name
  fun getThemeStyleId() = themeStyleId
  fun getDisplayedName() = displayedName
}
