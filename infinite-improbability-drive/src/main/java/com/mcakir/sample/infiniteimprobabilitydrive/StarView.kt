package com.mcakir.sample.infiniteimprobabilitydrive

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.mcakir.sample.infiniteimprobabilitydrive.data.db.FortyTwoDB
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Size
import com.mcakir.sample.infiniteimprobabilitydrive.data.repository.StarRepository

/**
 * A custom [WebView] implementation that shows a star image
 * and manages operations like adding, removing stars.
 *
 * @param context A Context object used to access application assets.
 * @param attrs An AttributeSet passed to our parent.
 * @param defStyle The default style resource ID.
 *
 * @see WebView
 */
class StarView(context: Context, attrs: AttributeSet?, defStyle: Int) : WebView(context, attrs, defStyle) {

    /**
     * A custom [WebView] implementation that shows a star image
     * and manages operations like adding, removing stars.
     *
     * @param context A Context object used to access application assets.
     *
     * @see WebView
     */
    constructor(context: Context): this(context, null)

    /**
     * A custom [WebView] implementation that shows a star image
     * and manages operations like adding, removing stars.
     *
     * @param context A Context object used to access application assets.
     * @param attrs An AttributeSet passed to our parent.
     *
     * @see WebView
     */
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, androidx.appcompat.R.style.Base_Theme_AppCompat)

    private val resourceUrl = "https://img.etimg.com/thumb/msid-72948091,width-650,imgsize-95069,,resizemode-4,quality-100/star_istock.jpg"
    private val javascriptInterfaceName = "Android"

    /**
     * Adds the star interface to the UI.
     *
     * @param lifecycleOwner [LifecycleOwner] object required to perform the necessary
     * operations according to the application lifecycle.
     */
    fun addStarInterface(lifecycleOwner: LifecycleOwner) {
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        setWebContentsDebuggingEnabled(true)

        // This javascript interface listens
        addJavascriptInterface(EventReceiver(context, lifecycleOwner), javascriptInterfaceName)

        // This observer listens lifecycle changes
        lifecycleOwner.lifecycle.addObserver(LifecycleObserver())

        // This script manipulates the loaded HTML page and adds three buttons.
        val manupilate_script = resources.openRawResource(R.raw.manupilate).bufferedReader().use { it.readText() }

        val client = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // When the related page is loaded, manipulate the HTML of the page.
                loadUrl("javascript:$manupilate_script")
            }
        }

        webViewClient = client

        loadUrl(resourceUrl)
    }

    // This receiver listens events of web page and operates related business logic.
    private class EventReceiver(private val context: Context, private val lifecycleOwner: LifecycleOwner) {

        private val starDao = FortyTwoDB.getInstance(context).starDao()
        private val starRepository = StarRepository(starDao, lifecycleOwner)

        @JavascriptInterface
        fun addSmallStar() {
            val isSuccess = starRepository.addStar(Size.SMALL)

            if (!isSuccess)
                Toast.makeText(context, "Sky is full.", Toast.LENGTH_LONG).show()
        }

        @JavascriptInterface
        fun addBigStar() {
            val isSuccess = starRepository.addStar(Size.BIG)

            if (!isSuccess)
                Toast.makeText(context, "Sky is full.", Toast.LENGTH_LONG).show()
        }

        @JavascriptInterface
        fun reset() {
            starRepository.reset()
        }
    }
}