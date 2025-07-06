package com.rrain.calculator4

class Size {
  private var w = 0
  fun getW() = w
  fun setW(w: Int) { this.w = w }
  
  private var h = 0
  fun getH() = h
  fun setH(h: Int) { this.h = h }
  
  constructor()
  
  constructor(w: Int, h: Int) {
    this.w = w
    this.h = h
  }
}
