package com.wangzy.exitappdemo.mvp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.mvp.presenter.LoginPresenter
import com.wangzy.exitappdemo.mvp.presenter.LoginPresenterImpl
import com.wangzy.exitappdemo.mvp.view.LoginView


class MVPActivity : Activity(), LoginView, View.OnClickListener {

    private var progressBar: ProgressBar? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var presenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        findViewById<Button>(R.id.button).setOnClickListener(this)
        presenter = LoginPresenterImpl(this)
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progressBar!!.setVisibility(View.VISIBLE)
    }

    override fun hideProgress() {
        progressBar!!.setVisibility(View.GONE)
    }

    override fun setUsernameError() {
        username!!.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password!!.error = getString(R.string.password_error)
    }

    override fun navigateToHome() {
        Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        presenter!!.validateCredentials(username!!.text.toString(), password!!.text.toString())
    }

}