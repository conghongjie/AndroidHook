package com.elvis.android.hook.demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.elvis.android.hook.demo.example.TargetClass;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e() should be hooked
                Log.e("origin", "call Log.e()");
                // String.startsWith() should be hooked
                Log.w("origin", "foo startsWith f is "+"foo".startsWith("f"));
                // ClassWithVirtualMethod.tac() should be hooked
                Log.w("origin", "virtual tac a,b,c,d, got "+new TargetClass().normalMethod("a"));
                // ClassWithStaticMethod.tac() should be hooked
                Log.w("origin", "static tac a,b,c,d, got "+TargetClass.staticMethod("a"));
                Log.w("origin", "JNI method return string: "+TargetClass.nativeMethod());
            }
        });



        MySQLiteHelper mySQLiteHelper;
        mySQLiteHelper = new MySQLiteHelper(this.getApplicationContext());
        mySQLiteHelper.insertData("1","elvis");
    }




    public class MySQLiteHelper extends SQLiteOpenHelper {

        public static final String DBName = "hero_info";

        //调用父类构造器
        public MySQLiteHelper(Context context) {
            super(context, DBName, null, 1);
        }

        /**
         * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
         * 重写onCreate方法，调用execSQL方法创建表
         * */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists "+DBName+"("
                    + "id integer primary key,"
                    + "name varchar)");

        }

        //当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }


        /**
         * 增
         */
        public void insertData(String id, String name) {
            try{
                //设置属性：
                ContentValues dataValue = new ContentValues();
                dataValue.put("id", id);
                dataValue.put("name", name);
                //插入数据库：
                SQLiteDatabase databaseWrite = this.getWritableDatabase();
                databaseWrite.insert(DBName, null, dataValue);
                databaseWrite.close();
            }catch (Throwable e){
                e.printStackTrace();
            }
        }

        /**
         * 删
         */
        public void deleteData(String id, String name) {
            SQLiteDatabase databaseWrite = this.getWritableDatabase();
            databaseWrite.delete(DBName, "id = ? and name = ?", new String[]{id, name});
            databaseWrite.close();
        }


        void updateData(String id , String name){
            SQLiteDatabase databaseWrite = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);//key为字段名，value为值
            databaseWrite.update(DBName, values, "id=?", new String[]{id});
            databaseWrite.close();
        }


        /**
         * 查
         */
        public ArrayList<String> quaryNameList() {
            ArrayList<String> nameList = new ArrayList<String>();
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.query(DBName, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    nameList.add(name);
                }
            }
            return nameList;
        }



    }

}
