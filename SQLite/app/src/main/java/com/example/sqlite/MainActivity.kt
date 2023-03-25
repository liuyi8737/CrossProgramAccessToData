package com.example.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import androidx.core.content.edit
import androidx.core.database.sqlite.transaction
import androidx.core.view.GravityCompat.apply
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val dbHelper = MyDatabaseHelper(this,"book.db",2)

        createButton.setOnClickListener {
            dbHelper.writableDatabase
        }//创建

        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value1 = ContentValues().apply{
                put("name","The Da Vinci Code")
                put("author","Tom")
                put("pages",454)
                put("price",16.96)
            }
            db.insert("Book",null,value1)
            val value2 = ContentValues().apply{
                put("name","jack")
                put("author","thiasa")
                put("pages",510)
                put("price",19.95)
            }
            db.insert("Book",null,value2)

        }//添加

        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price",10.99)
            db.update("Book",values,"name = ?", arrayOf("The Da Vinci Code"))
        }//更新

        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book","pages >?",arrayOf("500"))
        }//删除

        querydata.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Book",null,null,null,null,null,null)
            if (cursor.moveToFirst()){
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))
                    Log.d("MainActivity","book name is $name")
                    Log.d("MainActivity","book author is $author")
                    Log.d("MainActivity","book pages is $pages")
                    Log.d("MainActivity","price name is $price")
                }while(cursor.moveToNext())
            }
            cursor.close()
        }
        replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book", null, null)
//                if (true) {
//                    throw NullPointerException()
//                }
                val values = ContentValues().apply {
                    put("name", "Game of Thrones")
                    put("author", "George Martin")
                    put("pages", 720)
                    put("price", 20.85)
                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful()
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                db.endTransaction()
            }
        }

        //查询


    }
}