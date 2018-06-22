package com.malharia.b3.b3malharizbar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.malharia.b3.b3malharizbar.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class RoloOperations {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBHandler.ROLO_COLUMN_ID,
            DBHandler.ROLO_COLUMN_CODITEM,
            DBHandler.ROLO_COLUMN_ENDERECO,
            DBHandler.ROLO_COLUMN_LOCAL,
            DBHandler.ROLO_COLUMN_NUMLOTE,
            DBHandler.ROLO_COLUMN_NUMPECA,
            DBHandler.ROLO_COLUMN_ORDEM,
            DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR,
            DBHandler.ROLO_COLUMN_POSICAO
    };

    public RoloOperations(Context context){
        dbhandler = new DBHandler(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }

    public Rolo addRolo(Rolo Rolo){
        ContentValues values  = new ContentValues();
        values.put(DBHandler.ROLO_COLUMN_CODITEM,Rolo.getCodItem());
        values.put(DBHandler.ROLO_COLUMN_ENDERECO,Rolo.getEndereco());
        values.put(DBHandler.ROLO_COLUMN_LOCAL, Rolo.getLocal());
        values.put(DBHandler.ROLO_COLUMN_NUMLOTE, Rolo.getNumLote());
        values.put(DBHandler.ROLO_COLUMN_NUMPECA, Rolo.getNumPeca());
        values.put(DBHandler.ROLO_COLUMN_ORDEM, Rolo.getOrdem());
        values.put(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR, Rolo.getPermiteSubstituir());
        values.put(DBHandler.ROLO_COLUMN_POSICAO, Rolo.getPosicao());
        long insertid = database.insert(DBHandler.TABLE_ROLO,null,values);
        Rolo.setId(insertid);
        return Rolo;
    }

    // Getting single Employee
    public Rolo getRolo(long id) {

        Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,DBHandler.ROLO_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Rolo e = new Rolo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)));

        cursor.close();
        return e;
    }

    public Rolo getMarcarRolo(String cItem, String nLote, String nPeca) {

        //Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,DBHandler.ROLO_COLUMN_CODITEM + "=?" + cItem  + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nLote + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nPeca ,null,null, null, null);

        Cursor cursor = database.query(DBHandler.TABLE_ROLO, new String[] {DBHandler.ROLO_COLUMN_ID, DBHandler.ROLO_COLUMN_CODITEM, DBHandler.ROLO_COLUMN_ENDERECO
                , DBHandler.ROLO_COLUMN_LOCAL, DBHandler.ROLO_COLUMN_ORDEM, DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR, DBHandler.ROLO_COLUMN_POSICAO
                , DBHandler.ROLO_COLUMN_NUMLOTE, DBHandler.ROLO_COLUMN_NUMPECA},  DBHandler.ROLO_COLUMN_NUMPECA + " like " + "'%" + nPeca + "%'" + " and " + DBHandler.ROLO_COLUMN_NUMLOTE  + " like " + "'%" + nLote + "%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Rolo rolos = new Rolo();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Rolo rolo = new Rolo();
                rolo.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ID)));
                rolo.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_CODITEM)));
                rolo.setEndereco(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ENDERECO)));
                rolo.setLocal(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_LOCAL)));
                rolo.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMLOTE)));
                rolo.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMPECA)));
                rolo.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ORDEM)));
                rolo.setPermiteSubstituir(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR)));;
                rolo.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_POSICAO)));
                rolos = rolo;
            }
        }
        else
        {
            return null;
        }

        //Rolo e = new Rolo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6).charAt(0),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)));

        cursor.close();
        return rolos;
    }

    public boolean getSubstituirMarcarRolo(String cItem, String nLote, String nPeca) {

        //Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,DBHandler.ROLO_COLUMN_CODITEM + "=?" + cItem  + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nLote + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nPeca ,null,null, null, null);

        Cursor cursor = database.query(DBHandler.TABLE_ROLO, new String[] {DBHandler.ROLO_COLUMN_ID, DBHandler.ROLO_COLUMN_CODITEM, DBHandler.ROLO_COLUMN_ENDERECO
                , DBHandler.ROLO_COLUMN_LOCAL, DBHandler.ROLO_COLUMN_ORDEM, DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR, DBHandler.ROLO_COLUMN_POSICAO
                , DBHandler.ROLO_COLUMN_NUMLOTE, DBHandler.ROLO_COLUMN_NUMPECA},  DBHandler.ROLO_COLUMN_NUMLOTE  + " like " + "'%" + nLote + "%'" + " and " + DBHandler.ROLO_COLUMN_POSICAO  + " = " + "0", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Rolo rolos = new Rolo();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Rolo rolo = new Rolo();
            rolo.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ID)));
            rolo.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_CODITEM)));
            rolo.setEndereco(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ENDERECO)));
            rolo.setLocal(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_LOCAL)));
            rolo.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMLOTE)));
            rolo.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMPECA)));
            rolo.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ORDEM)));
            rolo.setPermiteSubstituir(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR)));;
            rolo.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_POSICAO)));
            rolos = rolo;
        }
        else
        {
            return false;
        }

        //Rolo e = new Rolo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6).charAt(0),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)));

        cursor.close();
        return true;
    }

    public Rolo getFirstNaoMarcaroRolo(String cItem, String nLote, String nPeca) {

        //Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,DBHandler.ROLO_COLUMN_CODITEM + "=?" + cItem  + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nLote + DBHandler.ROLO_COLUMN_CODITEM + "=?" + nPeca ,null,null, null, null);

        Cursor cursor = database.query(DBHandler.TABLE_ROLO, new String[] {DBHandler.ROLO_COLUMN_ID, DBHandler.ROLO_COLUMN_CODITEM, DBHandler.ROLO_COLUMN_ENDERECO
                , DBHandler.ROLO_COLUMN_LOCAL, DBHandler.ROLO_COLUMN_ORDEM, DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR, DBHandler.ROLO_COLUMN_POSICAO
                , DBHandler.ROLO_COLUMN_NUMLOTE, DBHandler.ROLO_COLUMN_NUMPECA},  DBHandler.ROLO_COLUMN_NUMLOTE  + " like " + "'%" + nLote + "%'" + " and " + DBHandler.ROLO_COLUMN_POSICAO  + " = " + "0", null, null, null, DBHandler.ROLO_COLUMN_NUMPECA);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Rolo rolos = new Rolo();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Rolo rolo = new Rolo();
            rolo.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ID)));
            rolo.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_CODITEM)));
            rolo.setEndereco(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ENDERECO)));
            rolo.setLocal(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_LOCAL)));
            rolo.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMLOTE)));
            rolo.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMPECA)));
            rolo.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ORDEM)));
            rolo.setPermiteSubstituir(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR)));;
            rolo.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_POSICAO)));
            rolos = rolo;

        }
        else
        {
            return null;
        }

        //Rolo e = new Rolo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6).charAt(0),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)));

        cursor.close();
        return rolos;
    }

    public List<Rolo> getAllRolos() {

        Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,null,null,null, null,  DBHandler.ROLO_COLUMN_NUMPECA);

        List<Rolo> rolos = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Rolo rolo = new Rolo();
                rolo.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ID)));
                rolo.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_CODITEM)));
                rolo.setEndereco(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ENDERECO)));
                rolo.setLocal(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_LOCAL)));
                rolo.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMLOTE)));
                rolo.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMPECA)));
                rolo.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ORDEM)));
                rolo.setPermiteSubstituir(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR)));;
                rolo.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_POSICAO)));
                rolos.add(rolo);
            }
        }

        cursor.close();
        return rolos;
    }

    public int updateRolo(Rolo rolo) {

        ContentValues values = new ContentValues();
        values.put(DBHandler.ROLO_COLUMN_CODITEM, rolo.getCodItem());
        values.put(DBHandler.ROLO_COLUMN_ENDERECO, rolo.getEndereco());
        values.put(DBHandler.ROLO_COLUMN_LOCAL, rolo.getLocal());
        values.put(DBHandler.ROLO_COLUMN_NUMLOTE, rolo.getNumLote());
        values.put(DBHandler.ROLO_COLUMN_NUMPECA, rolo.getNumPeca());
        values.put(DBHandler.ROLO_COLUMN_ORDEM, rolo.getOrdem());
        values.put(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR, rolo.getPermiteSubstituir());
        values.put(DBHandler.ROLO_COLUMN_POSICAO, rolo.getPosicao());

        return database.update(DBHandler.TABLE_ROLO, values,
                DBHandler.ROLO_COLUMN_ID + "=?",new String[] { String.valueOf(rolo.getId())});
    }

    public void removeRolo() {

        database.delete(DBHandler.TABLE_ROLO, null, null);
    }

    public Rolo getData() {

        Cursor cursor = database.query(DBHandler.TABLE_ROLO,allColumns,null,null,null, null, null);

        Rolo data = null;
        while(cursor.moveToNext()){
            data = new Rolo();
            data.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ID)));
            data.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_CODITEM)));
            data.setEndereco(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ENDERECO)));
            data.setLocal(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_LOCAL)));
            data.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMLOTE)));
            data.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_NUMPECA)));
            data.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_ORDEM)));
            data.setPermiteSubstituir(cursor.getString(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_PERMITESUBSTITUIR)));
            data.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.ROLO_COLUMN_POSICAO)));
        }
        cursor.close();

        return data;
    }
}
