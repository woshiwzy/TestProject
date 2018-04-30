package com.wangzy.domain

import android.os.Parcel
import android.os.Parcelable

class Human(var name: String, var age: Int) : Parcelable {
    init {

    }

    constructor(sex: Boolean) : this(name = "sand", age = 3) {

    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(age)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Human> = object : Parcelable.Creator<Human> {
            override fun createFromParcel(source: Parcel): Human = Human(source)
            override fun newArray(size: Int): Array<Human?> = arrayOfNulls(size)
        }
    }
}