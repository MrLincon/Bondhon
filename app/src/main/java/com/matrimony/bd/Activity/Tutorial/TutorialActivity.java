package com.matrimony.bd.Activity.Tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.matrimony.bd.R;

public class TutorialActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        String myYouTubeVideoUrl = "https://www.youtube.com/embed/XUTXU6fy94E";
        String myYouTubeVideoUrl1 = "https://www.youtube.com/embed/XUTXU6fy94E";

        String dataUrl =
                "<html>" +
                        "<body>" +
                        "<h2>Biodata upload from mobile</h2>" +
                        "<br>" +
                        "<iframe width=\"960\" height=\"415\" src=\""+myYouTubeVideoUrl+"\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        String dataUrl2 =
                "<html>" +
                        "<body>" +
                        "<h2>How to send interest</h2>" +
                        "<br>" +
                        "<iframe width=\"960\" height=\"415\" src=\""+myYouTubeVideoUrl1+"\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        WebView myWebView = findViewById(R.id.mWebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadData(dataUrl, "text/html", "utf-8");
        //
        WebView myWebView2 = findViewById(R.id.mWebView1);
        WebSettings webSettings2 = myWebView2.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        myWebView2.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView2.getSettings().setLoadWithOverviewMode(true);
        myWebView2.getSettings().setUseWideViewPort(true);
        myWebView2.loadData(dataUrl2, "text/html", "utf-8");
    }
}