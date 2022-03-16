package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.model.User

class UserDatabase(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE ${Constant.TABLE_NAME}(${Constant.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.NAME} TEXT NOT NULL, ${Constant.PHONE} TEXT NOT NULL,${Constant.COUNTRY} TEXT NOT NULL,${Constant.ADDRESS} TEXT NOT NULL,${Constant.PASSWORD} TEXT NOT NULL,${Constant.IMAGE} TEXT NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun insertUser(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.ID, user.id)
        contentValues.put(Constant.NAME, user.name)
        contentValues.put(Constant.PHONE, user.phoneNumber)
        contentValues.put(Constant.COUNTRY, user.country)
        contentValues.put(Constant.ADDRESS, user.address)
        contentValues.put(Constant.PASSWORD, user.password)
        contentValues.put(Constant.IMAGE, user.imagePath)
        database.insert(Constant.TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun getAllUser(): ArrayList<User> {
        var userList = ArrayList<User>()
        val query = "select *from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val user = User(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6))
                userList.add(user)
            } while (cursor.moveToNext())
        }

        return userList
    }
}