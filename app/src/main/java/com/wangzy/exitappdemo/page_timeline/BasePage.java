package com.wangzy.exitappdemo.page_timeline;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wangzy on 15/9/16.
 */
public class BasePage {


    protected View rootView;
    protected int showCount = 0;
    protected int hideCount = 0;

    private String title;

    public boolean needRefresh = false;

    //==============
    protected Activity mainActivity;

    public void onShow() {

        showCount++;
    }

    public void onHide() {
        hideCount++;

    }

    public void onDestroy(){


    }

    public void closeSoftKeyBorad(InputMethodManager imm) {

    }

    public View findViewById(int id) {

        return rootView.findViewById(id);
    }

    public String getInput(EditText input) {
        return input.getText().toString();
    }

    public TextView findTextViewById(int id) {
        return (TextView) rootView.findViewById(id);
    }

    public EditText findEditTextById(int id) {
        return (EditText) rootView.findViewById(id);
    }

    public Button findButtonById(int id) {
        return (Button) rootView.findViewById(id);
    }

    public ImageView findImageViewById(int id) {
        return (ImageView) rootView.findViewById(id);
    }

    public ImageButton findImageButtonById(int id) {
        return (ImageButton) rootView.findViewById(id);
    }

    public ListView findListViewById(int id) {
        return (ListView) rootView.findViewById(id);
    }

    public RelativeLayout findRelativeLayout(int id) {
        return (RelativeLayout) rootView.findViewById(id);
    }

    public LinearLayout findLinearLayout(int id) {
        return (LinearLayout) rootView.findViewById(id);
    }

    public AutoCompleteTextView findAutoCompleteTextById(int id) {
        return (AutoCompleteTextView) rootView.findViewById(id);
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
