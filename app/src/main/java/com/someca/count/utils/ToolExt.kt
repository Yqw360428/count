package com.someca.count.utils

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.DecimalFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.lxj.xpopup.XPopup
import com.someca.count.App
import com.someca.count.bean.ScheduleBean
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.math.pow
import kotlin.math.round

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

fun Double.fixNum() : Double{
    return DecimalFormat("#.##").format(this).toDouble()
}

fun calculateTotalInterest(principal: Double, annualRate: Double, months : Int): Double {
    val monthlyPayment = principal * annualRate * (1 + annualRate).pow(months.toDouble()) /
            ((1 + annualRate).pow(months.toDouble()) - 1)
    val totalRepayment = monthlyPayment * months
    return totalRepayment - principal
}

fun calculateEMIArrears(loanAmount: Double, rate: Double, months: Int): Double {
    val emiArrears = (loanAmount * rate * (1 + rate).pow(months.toDouble())) / ((1 + rate).pow(
        months.toDouble()
    ) - 1)
    return emiArrears
}

fun calculateAdvance(loanAmount: Double, rate: Double, n: Int): Double {
    val rateFactor = (1 + rate).pow((n - 1).toDouble())
    val emiAdvance = (loanAmount * rate * rateFactor) / ((1 + rate).pow(n.toDouble()) - 1)
    return emiAdvance
}

fun calculateEMIRepaymentSchedule(
    loanAmount: Double,
    rate: Double,
    months: Int,
    emiType: Int
): MutableList<ScheduleBean> {
    val repaymentSchedule = mutableListOf<ScheduleBean>()

    val emi: Double = if (emiType == 0) {
        (loanAmount * rate * (1 + rate).pow(months.toDouble())) /
                ((1 + rate).pow(months.toDouble()) - 1)
    } else {
        (loanAmount * rate * (1 + rate).pow((months - 1).toDouble())) /
                ((1 + rate).pow(months.toDouble()) - 1)
    }

    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
    var remainingLoan = loanAmount

    for (i in 0 until months) {
        val interest: Double = if (emiType == 1 && i == 0) {
            0.0
        } else {
            remainingLoan * rate
        }

        val principal = emi - interest
        val total = principal + interest
        repaymentSchedule.add(ScheduleBean(formatter.format(calendar.time),
            round(principal).toInt(),
            round(interest).toInt(),
            round(total).toInt()))

        remainingLoan -= principal
        calendar.add(Calendar.MONTH, 1)
    }

    return repaymentSchedule
}


suspend fun getNetData(): Response?{
    return suspendCancellableCoroutine {
        OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
            .build()
            .newCall(
                Request.Builder()
                    .url("")
                    .addHeader("Content-Type", "application/json")
                    .post(
                        JSONObject().apply {

                        }.toString().toRequestBody("application/json".toMediaTypeOrNull())
                    ).build()
            )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (it.isActive){
                        it.resume(null)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (it.isActive){
                        if (response.code == 200){
                            it.resume(response)
                        }else{
                            it.resume(null)
                        }
                    }
                }

            })
    }
}
