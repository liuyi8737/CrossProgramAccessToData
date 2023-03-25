package com.example.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name:String,version:Int):SQLiteOpenHelper(context,name,null,version) {
    val createBook = "create table Book ("+"id integer primary key autoincrement,"+
            "author text,"+
            "name text,"+
            "price real,"+
            "category_id integer,"+
            "pages integer)"

    val createCategory = "create table Category ("+
            "id integer primary key autoincrement,"+
            "category_name text,"+
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createBook)
        db!!.execSQL(createCategory)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion<=1){
            db!!.execSQL(createCategory)
        }
        if (oldVersion<=2){
            db!!.execSQL("alter table Book add column category_id integer")
        }


    }
}


