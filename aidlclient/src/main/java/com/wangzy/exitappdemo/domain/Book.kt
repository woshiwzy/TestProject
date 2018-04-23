package com.wangzy.exitappdemo.domain

import android.os.Parcel
import android.os.Parcelable

class Book() : Parcelable {

     var bookId:Int=0
     var bookName:String=""


    constructor(parcel: Parcel):this(){
        bookId = parcel.readInt()
        bookName = parcel.readString()
    }

    constructor(bookId:Int,bookName:String):this(){
        this.bookId=bookId
        this.bookName=bookName
    }



    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(bookId)
        dest?.writeString(bookName)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Book(bookId=$bookId, bookName='$bookName')"
    }

    companion object CREATOR : Parcelable.Creator<Book> {

        override fun createFromParcel(parcel: Parcel): Book {

            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {

            return arrayOfNulls<Book?>(size)
        }
    }


}