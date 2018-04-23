package com.wangzy.exitappdemo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.wangzy.exitappdemo.IImmocAIDL
import com.wangzy.exitappdemo.domain.Book

class AIDLService : Service() {


    var iBinder = object : IImmocAIDL.Stub() {

        override fun add(num1: Int, num2: Int): Int {
            return num1+num2
        }

        override fun getBook(name: String?): Book {

            return Book(bookId = 123,bookName = "Think in Java")
        }

    }


    override fun onBind(intent: Intent): IBinder {


        return iBinder
    }


}
