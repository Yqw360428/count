package com.someca.count.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.google.gson.Gson
import com.someca.count.R
import org.json.JSONObject

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
    private fun initWeb(){
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        /**
         *  afName: mmbhlPnpPctt
         *  closeName: oddTxhafCbd
         *  apkName: gmlMsd
         *  apkAppsFlyerJson: uooDvt
         *  productCategorySubName: xlqgDoiKhwpf
         *  campaign: aouZhhebGkif
         *  getafsub1fun: dqqsCadh
         *  afSub1: qpebMeltz
         */

        webView.registerHandler("oddTxhafCbd") { _, _ ->
            finish()
        }

        webView.registerHandler("gmlMsd") { data, _ ->
            val afMap = Gson().fromJson<HashMap<String,String>>(JSONObject(data).optString("crescentRay"),HashMap::class.java)
            AdjustEvent("veld3c").let {
                it.addCallbackParameter("data",afMap.toString())
                Adjust.trackEvent(it)
            }
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    addCategory(Intent.CATEGORY_BROWSABLE)
                    setData(Uri.parse( "${afMap["af_r"]}"))
                })
            }
        }

        webView.registerHandler("dqqsCadh") { _, function ->
            HashMap<String,String>().apply{
                put("deviceid",Settings.Secure.getString(contentResolver,Settings.Secure.ANDROID_ID))
            }.let {
                function.onCallBack(Gson().toJson(it))
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN){
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            }
        }
        return true
    }
}