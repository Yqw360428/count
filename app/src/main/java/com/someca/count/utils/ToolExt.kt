package com.someca.count.utils

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.DecimalFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.lxj.xpopup.XPopup
import com.someca.count.App
import java.math.BigDecimal

var lastClickTime = 0L
const val internalTime = 500L

fun View.setOnSingleClick(onClick: (View) -> Unit) {
    this.setOnClickListener {
        if (lastClickTime == 0L || (System.currentTimeMillis()- lastClickTime)>= internalTime) {
            onClick.invoke(it)
        }
        lastClickTime = System.currentTimeMillis()
    }
}

fun setOnSingleClick(vararg views: View, onClick: (View) -> Unit) {
    views.forEach {
        it.setOnClickListener { _ ->
            if (lastClickTime == 0L || (System.currentTimeMillis()- lastClickTime)>= internalTime) {
                onClick.invoke(it)
            }
            lastClickTime = System.currentTimeMillis()
        }
    }
}

val String.toastShort
    get() = Toast.makeText(App.instance, this, Toast.LENGTH_SHORT).show()

fun AppCompatTextView.copyTextToClipboard() {
    val clipDescription = ClipDescription("text", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN))
    val clip = ClipData(clipDescription, ClipData.Item(text.toString()))
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(clip)
}

fun View.initPopup(data : Array<String>,onSelectListener : (Int,String)->Unit){
    XPopup.Builder(context)
        .atView(this)
        .hasShadowBg(false)
        .asAttachList(data,null){index,str->
            onSelectListener.invoke(index,str)
        }
        .show()
}

fun Float.fixNum() : Float{
    return DecimalFormat("#.##").format(this).toFloat()
}
