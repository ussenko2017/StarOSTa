package ru.mylx.starosta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import static android.content.Context.MODE_WORLD_READABLE;
import static android.support.constraint.Constraints.TAG;


public class DBHelper  extends SQLiteOpenHelper {

    static Context Contxt;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main";

    //названия таблиц
    public static final String T1 = "ozhenka";
    public static final String T2 = "stud";
    public static final String T3 = "predmet";
    public static final String T4 = "otdel";

    //айдишники
    public static final String T1_OZHENKA_ID = "ozhenka_id";
    public static final String T2_STUD_ID = "stud_id";
    public static final String T3_PREDMET_ID = "predmet_id";
    public static final String T4_OTDEL_ID = "otdel_id";

    //поля
    public static final String T1_BALL = "ball";
    public static final String T1_DATE_OZH = "date_ozh";


    public static final String T2_FIO = "fio";
    public static final String T2_BIRTHDAY = "birthday";
    public static final String T2_GROUP= "mygroup";
    public static final String T2_NUMBER= "number";




    public static final String T3_PREDMET_NAME = "predmet_name";
    public static final String T3_KOLVO_CHASOV = "kolvo_chasov";


    public static final String T4_OTDELNAME = "otdelname";





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        db.execSQL("create table " + T1 + "(" +
                T1_OZHENKA_ID + " integer primary key autoincrement ," +
                T3_PREDMET_ID + " integer," +
                T1_BALL + " integer," +
                T2_STUD_ID + " integer," +
                T1_DATE_OZH + " datetime" +  ")");
        db.execSQL("create table " + T2 + "(" +
                T2_STUD_ID + " integer primary key autoincrement ," +
                T2_FIO + " text," +
                T2_BIRTHDAY + " text," +
                T2_NUMBER+ " text, " +
                T4_OTDEL_ID+ " integer, " +
                T2_GROUP + " integer" + ")");

        db.execSQL("create table " + T3 + "(" +
                T3_PREDMET_ID + " integer primary key autoincrement ," +
                T3_PREDMET_NAME + " text," +
                T3_KOLVO_CHASOV + " integer"+ ")");

        db.execSQL("create table " + T4 + "(" +
                T4_OTDEL_ID + " integer primary key autoincrement ," +
                T4_OTDELNAME + " text" + ")");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + T1);
        db.execSQL("drop table if exists " + T2);
        db.execSQL("drop table if exists " + T3);
        db.execSQL("drop table if exists " + T4);

        onCreate(db);

    }
    //table1    ozhenka
    public static void add_to_t1(View view, int ball, int predmet_id,int stud_id){
        ContentValues contentValues = new ContentValues();
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.clear();
        contentValues.put(T1_BALL, ball);
        contentValues.put(T3_PREDMET_ID, predmet_id);
        contentValues.put(T1_DATE_OZH, getDate());
        contentValues.put(T2_STUD_ID, stud_id);

        db.insert(T1, null, contentValues);

    }
    //table2    stud
    public static void add_to_t2(View view, String fio, String birthday, int group, String number, int otdel){
        ContentValues contentValues = new ContentValues();
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.clear();
        contentValues.put(T2_FIO, fio);
        contentValues.put(T2_GROUP, group);
        contentValues.put(T2_BIRTHDAY,birthday);
        contentValues.put(T4_OTDEL_ID, otdel);
        contentValues.put(T2_NUMBER, number);
        db.insert(T2, null, contentValues);

    }

    //table3    predmet
    public static void add_to_t3(View view, String predmet_name, int kolvo_chasov){
        ContentValues contentValues = new ContentValues();
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.clear();
        contentValues.put(T3_PREDMET_NAME, predmet_name);
        contentValues.put(T3_KOLVO_CHASOV, kolvo_chasov);
        db.insert(T3, null, contentValues);

    }
    //table4 otdel
    public static void add_to_t4(View view, String otdelname){
        ContentValues contentValues = new ContentValues();
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.clear();
        contentValues.put(T4_OTDELNAME, otdelname);
        db.insert(T4, null, contentValues);

    }


    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(new Date());
    }

    public void delete(View view, int id, int Table) {
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String T = null;
        String P = null;
            if (Table ==  1){
                T = T1;
                P = T1_OZHENKA_ID;
            }
            else if (Table == 2){
                T = T2;
                P = T2_STUD_ID;
            }
            else if (Table ==  3){
                T = T3;
                P = T3_PREDMET_ID;
            }
            else if (Table == 4){
                T = T4;
                P = T4_OTDEL_ID;
            }

        Cursor cursor = db.query(T, null, null,
                null, null, null, null);

        db.delete(T, P + " = " + String.valueOf(id) , null);


        db.close();
        cursor.close();
    }

    public static  ArrayList getDataFromTableOtdel(View view){
        DBHelper dbHelper;
        Context context = view.getContext();
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(T4, null, null,
                null, null, null, null);
        int Count = 1;

        ArrayList<Integer> itemsID = new ArrayList<>();
        ArrayList<String> itemsTEXT = new ArrayList<>();
        ArrayList<String> itemsTEXT_PLUS_COUNT = new ArrayList<>();
        ArrayList<ArrayList> itemsArr = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(T4_OTDEL_ID);
            int nameIndex = cursor.getColumnIndex(T4_OTDELNAME);
            do {
                itemsID.add(cursor.getInt(idIndex));
                itemsTEXT.add(cursor.getString(nameIndex));
                itemsTEXT_PLUS_COUNT.add(String.valueOf(Count)+". "+cursor.getString(nameIndex));
                Count++;
            } while (cursor.moveToNext());
        }

        itemsArr.add(itemsID);
        itemsArr.add(itemsTEXT);
        itemsArr.add(itemsTEXT_PLUS_COUNT);
        database.close();
        cursor.close();
        return itemsArr;

    }
    public static  ArrayList getDataFromTablePredmet(View view){
        DBHelper dbHelper;
        Context context = view.getContext();
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(T3, null, null,
                null, null, null, null);
        int Count = 1;

        ArrayList<Integer> itemsID = new ArrayList<>();
        ArrayList<Integer> itemsKOLVO = new ArrayList<>();
        ArrayList<String> itemsTEXT = new ArrayList<>();
        ArrayList<String> itemsTEXT_PLUS_COUNT = new ArrayList<>();
        ArrayList<ArrayList> itemsArr = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(T3_PREDMET_ID);
            int kolvoIndex = cursor.getColumnIndex(T3_KOLVO_CHASOV);
            int nameIndex = cursor.getColumnIndex(T3_PREDMET_NAME);
            do {
                itemsID.add(cursor.getInt(idIndex));
                itemsKOLVO.add(cursor.getInt(kolvoIndex));
                itemsTEXT_PLUS_COUNT.add(String.valueOf(Count)+". "+cursor.getString(nameIndex));
                itemsTEXT.add(cursor.getString(nameIndex));
                Count++;
            } while (cursor.moveToNext());
        }
        itemsArr.add(itemsID);
        itemsArr.add(itemsTEXT);
        itemsArr.add(itemsKOLVO);
        itemsArr.add(itemsTEXT_PLUS_COUNT);
        database.close();
        cursor.close();
        return itemsArr;

    }
    public static  ArrayList getDataFromTableStud(Context context){
        DBHelper dbHelper;
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(T2, null, null,
                null, null, null, null);
        int Count = 1;
        ArrayList<Integer> itemsSTUD_ID = new ArrayList<>();
        ArrayList<String> itemsFIO = new ArrayList<>();
        ArrayList<String> itemsFIO_PLUS_COUNT = new ArrayList<>();
        ArrayList<String> itemsBIRTHDAY = new ArrayList<>();
        ArrayList<String> itemsNUMBER = new ArrayList<>();
        ArrayList<Integer> itemsOTDEL_ID = new ArrayList<>();
        ArrayList<Integer> itemsGROUP = new ArrayList<>();

        ArrayList<ArrayList> itemsArr = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int stud_idIndex = cursor.getColumnIndex(T2_STUD_ID);
            int fioIndex = cursor.getColumnIndex(T2_FIO);
            int birthdayIndex = cursor.getColumnIndex(T2_BIRTHDAY);
            int numberIndex = cursor.getColumnIndex(T2_NUMBER);
            int otdel_idIndex = cursor.getColumnIndex(T4_OTDEL_ID);
            int groupIndex = cursor.getColumnIndex(T2_GROUP);

            do {
                itemsSTUD_ID.add(cursor.getInt(stud_idIndex));
                itemsFIO.add(cursor.getString(fioIndex));
                itemsFIO_PLUS_COUNT.add(String.valueOf(Count)+". "+cursor.getString(fioIndex));
                itemsBIRTHDAY.add(cursor.getString(birthdayIndex));
                itemsNUMBER.add(cursor.getString(numberIndex));
                itemsOTDEL_ID.add(cursor.getInt(otdel_idIndex));
                itemsGROUP.add(cursor.getInt(groupIndex));
                Count++;
            } while (cursor.moveToNext());
        }
        itemsArr.add(itemsSTUD_ID);
        itemsArr.add(itemsFIO);
        itemsArr.add(itemsBIRTHDAY);
        itemsArr.add(itemsNUMBER);
        itemsArr.add(itemsOTDEL_ID);
        itemsArr.add(itemsGROUP);
        itemsArr.add(itemsFIO_PLUS_COUNT);
        database.close();
        cursor.close();
        return itemsArr;

    }

    public static void autoadd(View view){
        add_to_t4(view,"Вычислительная техника и Программное обеспечение");
        add_to_t4(view,"Информационные системы");
        add_to_t4(view,"Дизайн");
        add_to_t4(view,"Туризм");
        add_to_t4(view,"Организация гостиничного хозяйства");
        add_to_t3(view,"Высшая математика",80);
        add_to_t3(view,"Основы статистики",46);
        add_to_t3(view,"Филосовия",76);
        add_to_t3(view,"Политология",36);
        add_to_t3(view,"Основы права",24);
        add_to_t2(view,"Усенко Андрей Валерьевич","2000.07.15",1,"+77471201326",1);
        add_to_t2(view,"Коломиец Руслан Анатольевич","2000.07.09",1,"+77083707033",1);
        add_to_t2(view,"Носов Владимир Федорович","2000.08.19",0,"+77083707033",1);
        add_to_t2(view,"Азоркин Константин Алексеевич","2000.05.21",0,"+77773837378",2);
        add_to_t2(view,"Шелег Вячеслав Егорович","2000.10.6",1,   "+77773747373",1);
        add_to_t2(view,"Закревский Максим Викторович","2000.12.1",0,"+77735353445",1);
        add_to_t2(view,"Карпова Елена Владимировна","2000.05.1",1,"+77346346466",3);
        add_to_t2(view,"Поздняков Владислав Владиславович","2001.11.21",1,"+77346346466",3);
        add_to_t2(view,"Зубенко Михаил Петрович","2000.05.1",1,"+77346346466",3);

        for (int i = 0; i < 1000; i++){

            add_to_t1(view,Integer.valueOf(2 + (int)(Math.random() * ((5-2)+1))),
                    Integer.valueOf(1 + (int)(Math.random() * ((5-1)+1))),
                    Integer.valueOf(1 + (int)(Math.random() * ((9-1)+1))));
        }
    }

    public static  void createExcelTables(Context context){
        SQLiteDatabase db;
        Cursor userCursorPredmet;
        Cursor userCursorList;
        DBHelper dbhelper = new DBHelper(context);
        db = dbhelper.getReadableDatabase();
        HSSFWorkbook workbook = new HSSFWorkbook();
        ArrayList list = getDataFromTableStud(context);

        userCursorPredmet =  db.query(T3,null, null,null,null,null,null);
        if (userCursorPredmet.moveToFirst()) {
            int idIndex = userCursorPredmet.getColumnIndex(T3_PREDMET_ID);
            int nameIndex = userCursorPredmet.getColumnIndex(T3_PREDMET_NAME);

            do {
                //pred








                HSSFSheet sheet = workbook.createSheet(userCursorPredmet.getString(nameIndex));
                Row row = sheet.createRow(0);
                ArrayList<Integer> list1 = new ArrayList();
                ArrayList<String> list2 = new ArrayList();

                userCursorList = db.rawQuery("select ball,fio,date_ozh " +
                        "from stud " +
                        "Inner join ozhenka ON " +
                        "ozhenka.stud_id = stud.stud_id " +
                        "INNER JOIN predmet ON " +
                        "predmet.predmet_id = ozhenka.predmet_id " +
                        "WHERE predmet.predmet_id = "+String.valueOf(userCursorPredmet.getInt(idIndex)),
                        null,null);



                if (userCursorList.moveToFirst()) {

                    int fio = userCursorList.getColumnIndex("fio");
                    int ballIndex = userCursorList.getColumnIndex("ball");
                    int dateIndex = userCursorList.getColumnIndex("date_ozh");
                    int Count1 = 3;
                    do {
                        /*
                        Formatter f = new Formatter();
                        f.format("%.10s%n",userCursorList.getString(dateIndex));
                        row.createCell(Count1).setCellValue(f.toString());
                        Row row1 = sheet.createRow(Count1-2);
                        Row row2 = sheet.createRow(Count1-3);
                        Row row3 = sheet.createRow(Count1-4);

                        row1.createCell(0).setCellValue(userCursorList.getString(fio));
                        row1.createCell(Count1).setCellValue(userCursorList.getString(ballIndex));
*/                      Row row1 = sheet.createRow(Count1-2);


                        row1.createCell(0).setCellValue(userCursorList.getString(fio));
                        row1.createCell(3).setCellValue(userCursorList.getString(ballIndex));
                        row1.createCell(4).setCellValue(userCursorList.getString(dateIndex));














                        try{

                            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath();
                            FileOutputStream fOut = context.openFileOutput("tables.xls", MODE_WORLD_READABLE);
                            OutputStreamWriter osw = new OutputStreamWriter(fOut);
                            workbook.write(fOut);



/*
                            FileOutputStream ftt  = context.openFileOutput("tables.xls", Context.MODE_WORLD_WRITEABLE);
                            workbook.write(ftt);
                           */

                        }catch (Exception e){
                            Log.d(TAG, "createExcelTables: fuck, it is error");
                        }

                        Count1+=1;
                        //stud
                    } while (userCursorList.moveToNext());
                }








                //pred

            } while (userCursorPredmet.moveToNext());
        }
        db.close();
        userCursorPredmet.close();


    }

    public static ArrayList returnJournalToStudent(View view,int id_predmet,int id_stud ){
        int Count  = 0;
        Cursor userCursor;
        SimpleAdapter adapter;
        ArrayList<ArrayList> arr = new ArrayList<>();
        ArrayList<String> fio = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();
        ArrayList<Integer> ball = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<List> listarr = new ArrayList<>();
        List<Ozhenka> ozh = new ArrayList<Ozhenka>();
        SQLiteDatabase db;
        DBHelper dbhelper = new DBHelper(view.getContext());
        db = dbhelper.getReadableDatabase();
       userCursor =  db.rawQuery("select ozhenka_id as id,ball,date_ozh as date " +
                       "from stud " +
                        "Inner join ozhenka ON " +
                        "ozhenka.stud_id = stud.stud_id " +
                        "INNER JOIN predmet ON " +
                        "predmet.predmet_id = ozhenka.predmet_id " +
                        "WHERE predmet.predmet_id = "+String.valueOf(id_predmet)+ " and stud.stud_id = "
                       +String.valueOf(id_stud),
                        null,null);

        if (userCursor.moveToFirst()) {

            int idIndex = userCursor.getColumnIndex("id");
            int ballIndex = userCursor.getColumnIndex("ball");
            int dateIndex = userCursor.getColumnIndex("date");


            do {
                ball.add(userCursor.getInt(ballIndex));
                id.add(userCursor.getInt(idIndex));
                date.add(userCursor.getString(dateIndex));
                text.add(String.valueOf(userCursor.getInt(ballIndex))+" | "+userCursor.getString(dateIndex));
                ozh.add(new Ozhenka(userCursor.getInt(ballIndex),userCursor.getString(dateIndex)));
                Count++;
            } while (userCursor.moveToNext());
        }
       db.close();
       userCursor.close();
       arr.add(id);
       arr.add(fio);
       arr.add(ball);
       arr.add(date);
       arr.add(text);
       listarr.add(ozh);
       arr.add(listarr);

     return arr;
    }


    public static class Ozhenka {


        public int ball;
        public String date;

        public Ozhenka(int ball, String date) {

            this.ball = ball;
            this.date = date;
        }



    }

    public static class OzhenkaAdapter extends BaseAdapter {

        private Context context;
        private List<Ozhenka> ozhenkas;

        public OzhenkaAdapter(Context context, List<Ozhenka> ozhenkas) {
            this.context = context;

            this.ozhenkas = ozhenkas;
        }

        @Override
        public int getCount() {
            return ozhenkas.size();
        }

        @Override
        public Object getItem(int position) {
            return ozhenkas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            Button button2;
            Button button3;
            LinearLayout linearLayout;



            if (convertView == null) {
                button = new Button(context);
                button2 = new Button(context);
                button3 = new Button(context);
                button.setBackgroundResource(R.color.colorAccent);

                linearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                button.setPadding(5,5,5,5);
                button2.setPadding(0,5,5,5);
                linearLayout.setLayoutParams(params);

                button2.setLayoutParams(params);

                button.setText(ozhenkas.get(position).ball+"");
                button2.setText(ozhenkas.get(position).date+"");
                linearLayout.setOrientation(linearLayout.HORIZONTAL);
                linearLayout.addView(button);
                linearLayout.addView(button2);


            } else {

               linearLayout = (LinearLayout) convertView;

            }

            linearLayout.setId(position);


            return linearLayout;
        }
    }





public static boolean isConnected(Context context) {
        android.net.ConnectivityManager manager = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null) {
            if (manager.getActiveNetworkInfo().isAvailable()
                    && manager.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }








    static class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            //DBHelper.createExcelTables();

            return null;
        }
            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);

            }


        }

}



