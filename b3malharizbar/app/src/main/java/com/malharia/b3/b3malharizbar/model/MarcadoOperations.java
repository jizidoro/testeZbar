package com.malharia.b3.b3malharizbar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.malharia.b3.b3malharizbar.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MarcadoOperations {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBHandler.MARCADO_COLUMN_ID,
            DBHandler.MARCADO_COLUMN_CODITEM,
            DBHandler.MARCADO_COLUMN_NUMLOTE,
            DBHandler.MARCADO_COLUMN_NUMPECA,
            DBHandler.MARCADO_COLUMN_ORDEM,
            DBHandler.MARCADO_COLUMN_POSICAO
    };

    public MarcadoOperations(Context context){
        dbhandler = new DBHandler(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }

    public Marcado addMarcado(Marcado Marcado){
        ContentValues values  = new ContentValues();
        values.put(DBHandler.MARCADO_COLUMN_CODITEM,Marcado.getCodItem());
        values.put(DBHandler.MARCADO_COLUMN_NUMLOTE,Marcado.getNumLote());
        values.put(DBHandler.MARCADO_COLUMN_NUMPECA, Marcado.getNumPeca());
        values.put(DBHandler.MARCADO_COLUMN_ORDEM, Marcado.getOrdem());
        values.put(DBHandler.MARCADO_COLUMN_POSICAO, Marcado.getPosicao());
        long insertid = database.insert(DBHandler.TABLE_MARCADO,null,values);
        Marcado.setId(insertid);
        return Marcado;
    }

    // Getting single Employee
    public Marcado getMarcado(long id) {

        Cursor cursor = database.query(DBHandler.TABLE_MARCADO,allColumns,DBHandler.MARCADO_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Marcado e = new Marcado(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)));

        cursor.close();
        return e;
    }

    public Marcado getMarcadoByDados(String NumLote,String NumPeca) {

        Cursor cursor = database.query(DBHandler.TABLE_MARCADO, new String[] {DBHandler.MARCADO_COLUMN_ID, DBHandler.MARCADO_COLUMN_CODITEM
                , DBHandler.MARCADO_COLUMN_ORDEM, DBHandler.MARCADO_COLUMN_POSICAO
                , DBHandler.MARCADO_COLUMN_NUMLOTE, DBHandler.MARCADO_COLUMN_NUMPECA},  DBHandler.MARCADO_COLUMN_NUMPECA + " like " + "'%" + NumPeca + "%'" + " and " + DBHandler.MARCADO_COLUMN_NUMLOTE + " like " + "'%" + NumLote + "%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Marcado marcados = new Marcado();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Marcado marcado = new Marcado();
                marcado.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ID)));
                marcado.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_CODITEM)));
                marcado.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMLOTE)));
                marcado.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMPECA)));
                marcado.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ORDEM)));
                marcado.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_POSICAO)));
                marcados = marcado;
            }
        }
        else
        {
            return null;
        }

        //Marcado e = new Marcado(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6).charAt(0),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)));

        cursor.close();
        return marcados;
    }

    public List<Marcado> getAllMarcado() {

        Cursor cursor = database.query(DBHandler.TABLE_MARCADO,allColumns,null,null,null, null, null);

        List<Marcado> marcados = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Marcado marcado = new Marcado();
                marcado.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ID)));
                marcado.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_CODITEM)));
                marcado.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMLOTE)));
                marcado.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMPECA)));
                marcado.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ORDEM)));
                marcado.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_POSICAO)));
                marcados.add(marcado);
            }
        }

        cursor.close();
        return marcados;
    }

    public int updateMarcado(Marcado marcado) {

        ContentValues values = new ContentValues();
        values.put(DBHandler.MARCADO_COLUMN_CODITEM, marcado.getCodItem());
        values.put(DBHandler.MARCADO_COLUMN_NUMLOTE, marcado.getNumLote());
        values.put(DBHandler.MARCADO_COLUMN_NUMPECA, marcado.getNumPeca());
        values.put(DBHandler.MARCADO_COLUMN_ORDEM, marcado.getOrdem());
        values.put(DBHandler.MARCADO_COLUMN_POSICAO, marcado.getPosicao());

        return database.update(DBHandler.TABLE_MARCADO, values,
                DBHandler.MARCADO_COLUMN_ID + "=?",new String[] { String.valueOf(marcado.getId())});
    }

    public void removeMarcado() {

        database.delete(DBHandler.TABLE_MARCADO, null, null);
    }

    public Marcado getData() {

        Cursor cursor = database.query(DBHandler.TABLE_MARCADO,allColumns,null,null,null, null, null);

        Marcado data = null;
        while(cursor.moveToNext()){
            data = new Marcado();
            data.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ID)));
            data.setCodItem(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_CODITEM)));
            data.setNumLote(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMLOTE)));
            data.setNumPeca(cursor.getString(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_NUMPECA)));
            data.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_ORDEM)));
            data.setPosicao(cursor.getInt(cursor.getColumnIndex(DBHandler.MARCADO_COLUMN_POSICAO)));
        }
        cursor.close();

        return data;
    }
}
