package com.wangzy.myactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.App
import com.wangzy.domain.User
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.util.LogUtil
import kotlinx.android.synthetic.main.activity_green_dao.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.wangzy.exitappdemo.consts.TAG
class GreenDaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_dao)

        buttonSave.onClick {

            for (x in 1..10) {

                var user = User()
                user.name = "sand$x"
                user.age = x
                user.intro = "my name is sand$x"

                //如果Id为空才能save 否则只能insert,id不为空的话，save没有效果

                App.instance.mdaoSession.userDao.save(user)

                LogUtil.e(TAG, user.toString())


            }
        }


        buttonLoad.onClick {

            var builder = App.instance.mdaoSession.userDao.queryBuilder()

//            builder.where(UserDao.Properties.Id.eq(1))

//            builder.and(UserDao.Properties.Id.eq(1),UserDao.Properties.Id.eq(2))


//            builder.whereOr(UserDao.Properties.Id.eq(1),UserDao.Properties.Id.eq(2))
//            builder.where(UserDao.Properties.Name.eq("sand1"))

            var datas = builder.list()


            LogUtil.e(TAG, "data size:" + datas.size)

            datas.filter {

                LogUtil.e(TAG, "HA:" + it.toString())

                true
            }


            var students = App.instance.mdaoSession.studentDao.queryBuilder().list()
            LogUtil.e(TAG, "data size:" + students.size)
        }
    }


}
