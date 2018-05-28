package com.wangzy.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wangzy.aidlclient.R
import com.wangzy.login.contract.LoginContract

class LoginActivity : AppCompatActivity(), LoginContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    override fun showProgress() {
    }

    override fun hideProgresss() {
    }

    override fun getName(): String {

        return "Sand"
    }

    override fun getPassWord(): String {
        return "666666"
    }
}
