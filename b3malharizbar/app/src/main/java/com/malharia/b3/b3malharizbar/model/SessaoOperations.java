package com.malharia.b3.b3malharizbar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.malharia.b3.b3malharizbar.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class SessaoOperations {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBHandler.SESSAO_COLUMN_ID,
            DBHandler.SESSAO_COLUMN_CRACHA,
            DBHandler.SESSAO_COLUMN_ORDEM,
            DBHandler.SESSAO_COLUMN_INICIOOPERACAO,
            DBHandler.SESSAO_COLUMN_FIMOPERACAO,
            DBHandler.SESSAO_COLUMN_ROLOTROCA
    };

    public SessaoOperations(Context context){
        dbhandler = new DBHandler(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }

    public Sessao addSessao(Sessao Sessao){
        ContentValues values  = new ContentValues();
        values.put(DBHandler.SESSAO_COLUMN_CRACHA,Sessao.getCracha());
        values.put(DBHandler.SESSAO_COLUMN_ORDEM,Sessao.getOrdem());
        values.put(DBHandler.SESSAO_COLUMN_INICIOOPERACAO,Sessao.getInicioOperacao());
        values.put(DBHandler.SESSAO_COLUMN_FIMOPERACAO,Sessao.getFimOperacao());
        values.put(DBHandler.SESSAO_COLUMN_ROLOTROCA,Sessao.getRoloTroca());
        long insertid = database.insert(DBHandler.TABLE_SESSAO,null,values);
        Sessao.setId(insertid);
        return Sessao;
    }

    // Getting single Employee
    public Sessao getSessao(long id) {

        Cursor cursor = database.query(DBHandler.TABLE_SESSAO,allColumns,DBHandler.SESSAO_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Sessao e = new Sessao(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),cursor.getString(5));

        cursor.close();
        return e;
    }

    public Sessao getFirstSessao() {

        Cursor cursor = database.query(DBHandler.TABLE_SESSAO,allColumns,null,null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Sessao e = new Sessao(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),cursor.getString(5));

        cursor.close();
        return e;
    }

    public List<Sessao> getAllSessaos() {

        Cursor cursor = database.query(DBHandler.TABLE_SESSAO,allColumns,null,null,null, null, null);

        List<Sessao> rolos = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Sessao rolo = new Sessao();
                rolo.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ID)));
                rolo.setCracha(cursor.getString(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_CRACHA)));
                rolo.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ORDEM)));
                rolo.setInicioOperacao(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_INICIOOPERACAO)));
                rolo.setFimOperacao(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_FIMOPERACAO)));
                rolo.setRoloTroca(cursor.getString(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ROLOTROCA)));
                rolos.add(rolo);
            }
        }

        cursor.close();
        return rolos;
    }

    public int updateSessao(Sessao rolo) {

        ContentValues values = new ContentValues();
        values.put(DBHandler.SESSAO_COLUMN_CRACHA, rolo.getCracha());
        values.put(DBHandler.SESSAO_COLUMN_ORDEM, rolo.getOrdem());
        values.put(DBHandler.SESSAO_COLUMN_INICIOOPERACAO, rolo.getInicioOperacao());
        values.put(DBHandler.SESSAO_COLUMN_FIMOPERACAO, rolo.getFimOperacao());
        values.put(DBHandler.SESSAO_COLUMN_ROLOTROCA, rolo.getRoloTroca());

        return database.update(DBHandler.TABLE_SESSAO, values,
                DBHandler.SESSAO_COLUMN_ID + "=?",new String[] { String.valueOf(rolo.getId())});
    }

    public void removeSessao() {

        database.delete(DBHandler.TABLE_SESSAO, null, null);
    }

    public Sessao getData() {

        Cursor cursor = database.query(DBHandler.TABLE_SESSAO,allColumns,null,null,null, null, null);

        Sessao data = null;
        while(cursor.moveToNext()){
            data = new Sessao();
            data.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ID)));
            data.setCracha(cursor.getString(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_CRACHA)));
            data.setOrdem(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ORDEM)));
            data.setInicioOperacao(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_INICIOOPERACAO)));
            data.setFimOperacao(cursor.getInt(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_FIMOPERACAO)));
            data.setRoloTroca(cursor.getString(cursor.getColumnIndex(DBHandler.SESSAO_COLUMN_ROLOTROCA)));
        }
        cursor.close();

        return data;
    }
}
