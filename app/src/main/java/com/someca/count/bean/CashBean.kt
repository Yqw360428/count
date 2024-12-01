package com.someca.count.bean

data class CashBean(
    val id : Long = System.currentTimeMillis(),
    val isCustom : Boolean = false,
    var num1 : Int = 0,
    var num2 : Int = 0,
    var result : Int = 0
)
