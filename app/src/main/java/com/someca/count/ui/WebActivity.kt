package com.someca.count.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.google.gson.Gson
import com.someca.count.R
import org.json.JSONObject
import java.util.*

class WebActivity : AppCompatActivity() {

    private lateinit var webView: BridgeWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        webView = findViewById(R.id.webView)

        initWeb()

        intent.extras?.getString("url")?.let {

            webView.loadUrl(it)
        }

    }

    @SuppressLint("SetJavaScriptEnabled", "HardwareIds")
    private fun initWeb() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webView.webViewClient = BridgeWebViewClient(webView)

        webView.registerHandler("oddTxhafCbd") { _, _ ->
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        webView.registerHandler("crsxnGdz") { _, _ ->
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    addCategory(Intent.CATEGORY_BROWSABLE)
                    setData(Uri.parse("https://play.google.com/store/apps/details?id=$packageName&hl=hi&gl=in"))
                })
            }
        }

        webView.registerHandler("gmlMsd") { data, _ ->
            val afMap = Gson().fromJson<HashMap<String, String>>(
                JSONObject(data).optString("uooDvt"),
                HashMap::class.java
            )
            AdjustEvent("jghzox").let {
                it.addCallbackParameter("data", afMap.toString())
                Adjust.trackEvent(it)
            }
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    addCategory(Intent.CATEGORY_BROWSABLE)
                    setData(Uri.parse("${afMap["utmgNnxXzs"]}"))
                })
            }
        }

        webView.registerHandler("dqqsCadh") { _, function ->
            HashMap<String, String>().apply {
                put("zphFqeVaag", "")
                put(
                    "igtnByv",
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                )
            }.let {
                function.onCallBack(Gson().toJson(it))
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack()) {
                webView.goBack()
            }else{
                finish()
            }
            return true
        }
        return false
    }
}