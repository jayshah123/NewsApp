package com.jayshah.newsapp.HeadlinesUI;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jayshah.newsapp.R;

public class HeadlineDetailFragment extends Fragment {

    WebView detailWebView;
    ProgressBar activityIndicator;
    private static final String TAG = "HeadlineDetailFragment";

    public static HeadlineDetailFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        HeadlineDetailFragment fragment = new HeadlineDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.setWebContentsDebuggingEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.headline_detail_fragment, container, false);
        detailWebView = (WebView) v.findViewById(R.id.detail_webview);
        activityIndicator = (ProgressBar)v.findViewById(R.id.webview_progress);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getArguments().getString("url");
        if(url != null) {
            detailWebView.getSettings().setJavaScriptEnabled(true);
            detailWebView.getSettings().setAllowFileAccess(false);
            detailWebView.getSettings().setAllowContentAccess(false);
            detailWebView.setWebChromeClient(new WebChromeClient());

            detailWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    activityIndicator.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    activityIndicator.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    activityIndicator.setVisibility(View.GONE);
                }
            });

            detailWebView.setOnKeyListener(new View.OnKeyListener(){
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            && event.getAction() == MotionEvent.ACTION_UP
                            && detailWebView.canGoBack()) {
                        detailWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            detailWebView.loadUrl(url);


        }
    }
}
