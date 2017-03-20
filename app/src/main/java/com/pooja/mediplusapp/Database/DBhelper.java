package com.pooja.mediplusapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.pooja.mediplusapp.Model.Alarm_Information;

import com.pooja.mediplusapp.Model.MediData;
import com.pooja.mediplusapp.Model.Pharma_model;

import java.util.ArrayList;

/**
 * Created by Pooja on 2/24/2017.
 */
public class DBhelper {

    private static DBhelper dbhelper;
    private static Helper myhelper;
    Context mctx;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<MediData> mediarrayList;
    ArrayList<String> names;
    ArrayList<MediData> mainarraylist;
    ArrayList<Pharma_model> arraypharma;


    public DBhelper(Context context)
    {
        this.mctx=context;
        myhelper=Helper.getHelper(context);
        mediarrayList=new ArrayList<>();
        mainarraylist=new ArrayList<>();
        names=new ArrayList<String>();
        arraypharma=new ArrayList<Pharma_model>();
      //  sqLiteDatabase=myhelper.getWritableDatabase();
    }

    public static DBhelper getInstance(Context context)
     {
         if(dbhelper==null)
         {
             dbhelper=new DBhelper(context);
         }
             return dbhelper;
     }

     /////inserting into table///////////////////////////////////////////////////////////
    public long insertintotable(String name,String description,String price,String cat,String medi_form,String instructions)throws SQLException
    {
       SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(myhelper.COL_NAME,name);
        contentValues.put(myhelper.COL_DESCRIPTION,description);
        contentValues.put(myhelper.COL_PRICE,price);
        contentValues.put(myhelper.COL_CATEGORY,cat);
        contentValues.put(myhelper.COL_FORM_MEDI,medi_form);
        contentValues.put(myhelper.COL_INSTRUCTIONS,instructions);
        long res=sqLiteDatabase.insertOrThrow(myhelper.TABLE_NAME,null,contentValues);
       sqLiteDatabase.close();
        return res;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    //update the edited data into database////////////////////////////////////////////////
    public long updateediteddrugdata(String name,String desc,String price,String catogory,String medi_form,String instructions,int id)
    {
        SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(myhelper.COL_NAME,name);
        contentValues.put(myhelper.COL_DESCRIPTION,desc);
        contentValues.put(myhelper.COL_PRICE,price);
        contentValues.put(myhelper.COL_CATEGORY,catogory);
        contentValues.put(myhelper.COL_FORM_MEDI,medi_form);
        contentValues.put(myhelper.COL_INSTRUCTIONS,instructions);
        long updateresult=sqLiteDatabase.update(myhelper.TABLE_NAME,contentValues,myhelper.COL_ID +"=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return updateresult;
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public int getidfromDB(String name)
    {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        String[]columns={myhelper.COL_ID};
        int idofdrug=0;
        Cursor cursor=sqLiteDatabase.query(myhelper.TABLE_NAME,columns,myhelper.COL_NAME +"=?",new String[]{name},null,null,null);
        while (cursor.moveToNext())
        {
            idofdrug=cursor.getInt(cursor.getColumnIndex(myhelper.COL_ID));
        }
        sqLiteDatabase.close();
        return idofdrug;
    }

//new search to drug data//////////////////////////////////////////////////////////////////////
   public ArrayList<MediData> getallDrugnames()
    {
        SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        ArrayList<MediData> searchalldata=new ArrayList<>();
        String[]columns={myhelper.COL_NAME,myhelper.COL_DESCRIPTION,myhelper.COL_PRICE,myhelper.COL_CATEGORY,myhelper.COL_FORM_MEDI,myhelper.COL_INSTRUCTIONS,myhelper.COL_ID};
        Cursor cursor=sqLiteDatabase.query(myhelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(myhelper.COL_NAME));
            String desc=cursor.getString(cursor.getColumnIndex(myhelper.COL_DESCRIPTION));
            String price=cursor.getString(cursor.getColumnIndex(myhelper.COL_PRICE));
            int id=cursor.getInt(cursor.getColumnIndex(myhelper.COL_ID));
            String category=cursor.getString(cursor.getColumnIndex(myhelper.COL_CATEGORY));
            String medi_form=cursor.getString(cursor.getColumnIndex(myhelper.COL_FORM_MEDI));
            String instruction=cursor.getString(cursor.getColumnIndex(myhelper.COL_INSTRUCTIONS));
            MediData medidata=new MediData(name,desc,price,medi_form,instruction,id,category);
            searchalldata.add(medidata);
        }
        sqLiteDatabase.close();
        return searchalldata;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////



    //Search for Autocompletelist
    public ArrayList<String> getdrugdatasearch()
    {
        SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        String[]columns={myhelper.COL_NAME};
        Cursor cursor=sqLiteDatabase.query(myhelper.TABLE_NAME,columns,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(myhelper.COL_NAME));
            names.add(name);
        }
        sqLiteDatabase.close();
        return names;
    }

    //Display drug details from search
    public ArrayList<MediData> displayDrugdetails(String name)
    {
        SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        String[]columns={myhelper.COL_ID,myhelper.COL_NAME,myhelper.COL_DESCRIPTION,myhelper.COL_PRICE,myhelper.COL_CATEGORY,myhelper.COL_FORM_MEDI,myhelper.COL_INSTRUCTIONS};
        Cursor cursor=sqLiteDatabase.query(myhelper.TABLE_NAME,columns,myhelper.COL_NAME + "=?",new String[]{name},null,null,null);
        while (cursor.moveToNext())
        {
            int sid=cursor.getInt(cursor.getColumnIndex(myhelper.COL_ID));
            String sname=cursor.getString(cursor.getColumnIndex(myhelper.COL_NAME));
            String sdesc=cursor.getString(cursor.getColumnIndex(myhelper.COL_DESCRIPTION));
            String sprice=cursor.getString(cursor.getColumnIndex(myhelper.COL_PRICE));
            String scategory=cursor.getString(cursor.getColumnIndex(myhelper.COL_CATEGORY));
            String smedi_form=cursor.getString(cursor.getColumnIndex(myhelper.COL_FORM_MEDI));
            String sinstructions=cursor.getString(cursor.getColumnIndex(myhelper.COL_INSTRUCTIONS));
            MediData mediData=new MediData(sname,sdesc,sprice,smedi_form,sinstructions,sid,scategory);
            mediarrayList.add(mediData);
        }
        sqLiteDatabase.close();
        return mediarrayList;
    }

    public long deletedrugfromTable(int id)
    {
        SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
        long delres=sqLiteDatabase.delete(myhelper.TABLE_NAME,myhelper.COL_ID +"=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return delres;
    }

///////////////////////////////////**************************************************///////////////////////////////////////////////////

    /*****************************************************************************/

/////////////////////////////////////Alarm_table
        public long insertintoalarmtable(String al_name,String al_messasge,String date,String time,String Pi_no,String finished)
        {
          SQLiteDatabase dbs=myhelper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(myhelper.AL_COL_ALARMNAME,al_name);
            contentValues.put(myhelper.AL_MESSAGE,al_messasge);
            contentValues.put(myhelper.AL_DATE,date);
            contentValues.put(myhelper.AL_TIME,time);
            contentValues.put(myhelper.AL_PENDING_NO,Pi_no);
            contentValues.put(myhelper.FINISHED,finished);
            long resultinsert=dbs.insert(myhelper.MED_TABLE_NAME,null,contentValues);
            return resultinsert;
        }

     public ArrayList<Alarm_Information> getallalarminfo()
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         ArrayList<Alarm_Information> alinfo=new ArrayList<>();
         String[]columns={myhelper.AL_COL_ALARMNAME,myhelper.AL_MESSAGE,myhelper.AL_DATE,myhelper.AL_TIME,myhelper.AL_PENDING_NO,myhelper.AL_COL_ID,myhelper.FINISHED};
         Cursor cursor=sqLiteDatabase.query(myhelper.MED_TABLE_NAME,columns,null,null,null,null,null,null);
         while(cursor.moveToNext())
         {
             String alname=cursor.getString(cursor.getColumnIndex(myhelper.AL_COL_ALARMNAME));
             String almsg=cursor.getString(cursor.getColumnIndex(myhelper.AL_MESSAGE));
             String aldate=cursor.getString(cursor.getColumnIndex(myhelper.AL_DATE));
             String altime=cursor.getString(cursor.getColumnIndex(myhelper.AL_TIME));
             String alpi_no=cursor.getString(cursor.getColumnIndex(myhelper.AL_PENDING_NO));
             String alfin=cursor.getString(cursor.getColumnIndex(myhelper.FINISHED));
             int al_id=cursor.getInt(cursor.getColumnIndex(myhelper.AL_COL_ID));
             Alarm_Information alarmInformation=new Alarm_Information(alname,almsg,aldate,altime,alpi_no,alfin,al_id);
             alinfo.add(alarmInformation);
         }
         sqLiteDatabase.close();
         return alinfo;
     }

     public long deletealarm(int id)
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         long delres=sqLiteDatabase.delete(myhelper.MED_TABLE_NAME,myhelper.AL_COL_ID + "=?",new  String[]{String.valueOf(id)});
         sqLiteDatabase.close();
         return delres;
     }

     /////////////////////////////Pharma_table
     public long insertintopharma(String name,String number,String address)throws SQLException
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         ContentValues contentValues=new ContentValues();
         contentValues.put(myhelper.PHARMA_NAME,name);
         contentValues.put(myhelper.PHARMA_PHONE_NUMBER,number);
         contentValues.put(myhelper.PHARMA_ADDRESS,address);
         long insresult=sqLiteDatabase.insert(myhelper.PHARMA_TABLE_NAME,null,contentValues);
         sqLiteDatabase.close();
         return insresult;
     }

     public ArrayList<Pharma_model> getallpharmadetails()
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         String[]columns={myhelper.PHARMA_NAME,myhelper.PHARMA_PHONE_NUMBER,myhelper.PHARMA_ADDRESS,myhelper.PHARMA_COL_ID};
         Cursor cursor=sqLiteDatabase.query(myhelper.PHARMA_TABLE_NAME,columns,null,null,null,null,null,null);
         while(cursor.moveToNext())
         {
             String name=cursor.getString(cursor.getColumnIndex(myhelper.PHARMA_NAME));
             String number=cursor.getString(cursor.getColumnIndex(myhelper.PHARMA_PHONE_NUMBER));
             String address=cursor.getString(cursor.getColumnIndex(myhelper.PHARMA_ADDRESS));
             int p_id=cursor.getInt(cursor.getColumnIndex(myhelper.PHARMA_COL_ID));
             Pharma_model pharma_model=new Pharma_model(p_id,name,number,address);
             arraypharma.add(pharma_model);
         }
         sqLiteDatabase.close();
         return arraypharma;
     }

     public long deletepharmadetail(int id)
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         long delres=sqLiteDatabase.delete(myhelper.PHARMA_TABLE_NAME,myhelper.PHARMA_COL_ID + "=?",new String[]{String.valueOf(id)});
         sqLiteDatabase.close();
         return delres;
     }
     public long deleteallpharma()
     {
         SQLiteDatabase sqLiteDatabase=myhelper.getWritableDatabase();
         long delall=sqLiteDatabase.delete(myhelper.PHARMA_TABLE_NAME,null,null);
         sqLiteDatabase.close();
         return delall;
     }
        ///////////////////////////////
    public static class Helper extends SQLiteOpenHelper{
        private static final String DB_NAME = "MEDICINEPLUS.db";
        private static final String TABLE_NAME = "DRUG_DETAILS";
        private static final int VERSION = 1;
        private static final String COL_ID ="ID";
        private static final String COL_NAME ="DRUG_NAME";
        private static final String COL_DESCRIPTION ="DESCRIPTION";
        private static final String COL_PRICE ="PRICE";
        private static final String COL_CATEGORY="CATEGORY";
        private static final String COL_FORM_MEDI = "FORM_MEDICINE";
        private static final String COL_INSTRUCTIONS = "INSTRUCTIONS";


        //
        private static final String MED_TABLE_NAME="MED_ALARM";
        private static final String AL_COL_ID="ID";
        private static final String AL_COL_ALARMNAME="AL_NAME";
        private static final String AL_MESSAGE="AL_MESSAGE";
        private static final String AL_DATE="AL_DATE";
        private static final String AL_TIME="AL_TIME";
        private static final String AL_PENDING_NO="PENDING_NO";
        private static final String FINISHED="FINISHED";
        ////////

         //////////////////////
            private static final String PHARMA_TABLE_NAME="PHARMA_TABLE";
            private static final String PHARMA_COL_ID="PHARMA_ID";
            private static final String PHARMA_NAME="PHARMA_NAME";
            private static final String PHARMA_ADDRESS="PHARMA_ADDRESS";
            private static final String PHARMA_PHONE_NUMBER="PHONE_NUMBER";
            //////////////////

        private static Helper helperinstance;

        private Helper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        private static Helper getHelper(Context context)
        {
            if(helperinstance==null)
            {
                helperinstance=new Helper(context);
            }
            return helperinstance;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)throws SQLException {
            String create_table = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_NAME+" TEXT,"+COL_DESCRIPTION+" TEXT,"+COL_PRICE+" TEXT,"
                    +COL_CATEGORY+" TEXT,"+COL_FORM_MEDI+" TEXT,"+COL_INSTRUCTIONS+" TEXT);";


            String create_table_pharma="CREATE TABLE IF NOT EXISTS "+PHARMA_TABLE_NAME+"("+PHARMA_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +PHARMA_NAME+" TEXT,"+PHARMA_ADDRESS+" TEXT,"+PHARMA_PHONE_NUMBER+" TEXT);";

            String create_al="CREATE TABLE IF NOT EXISTS "+MED_TABLE_NAME+"("+AL_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +AL_COL_ALARMNAME+" TEXT,"+AL_MESSAGE+" TEXT,"+AL_DATE+" TEXT,"
                    +AL_TIME+" TEXT,"+AL_PENDING_NO+" TEXT,"+FINISHED+" TEXT);";

            sqLiteDatabase.execSQL(create_table);
            sqLiteDatabase.execSQL(create_table_pharma);
            sqLiteDatabase.execSQL(create_al);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PHARMA_TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+MED_TABLE_NAME);

            onCreate(sqLiteDatabase);
        }
    }
}