package com.wangzy.exitappdemo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.util.LogUtil
import com.wangzy.flight.FlightHelper
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.wangzy.exitappdemo", appContext.packageName)
    }

    @Test
    fun testLoadFlihtSeat() {

        var seat = FlightHelper.loadFlightSeat(InstrumentationRegistry.getTargetContext(), "data/310.json")

        LogUtil.e(TAG, "This is " + seat?.seatTitle)
        var row = seat.getRowByRowNumber(3)
        LogUtil.e(TAG, row?.seatArrange)

        var row2 = seat.getRowByRowNumber(24)

        LogUtil.e(TAG, row2?.seatArrange)

        var row3 = seat.getRowByRowNumber(25)

        LogUtil.e(TAG, row3?.seatArrange)

        LogUtil.e(TAG, "size:" + seat.allRow.size)


        LogUtil.e(TAG, "size:" + seat.generateRealRow().size)

    }
}
