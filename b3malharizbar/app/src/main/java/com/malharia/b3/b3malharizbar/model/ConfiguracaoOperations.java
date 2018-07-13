package com.malharia.b3.b3malharizbar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.malharia.b3.b3malharizbar.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracaoOperations {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBHandler.CONFIGURACAO_COLUMN_ID,
            DBHandler.CONFIGURACAO_COLUMN_ENDERECO_SERVIDOR
    };

    public ConfiguracaoOperations(Context context){
        dbhandler = new DBHandler(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }

    public Configuracao addConfiguracao(Configuracao Configuracao){
        ContentValues values  = new ContentValues();
        values.put(DBHandler.CONFIGURACAO_COLUMN_ENDERECO_SERVIDOR,Configuracao.getEnderecoServidor());
        long insertid = database.insert(DBHandler.TABLE_CONFIGURACAO,null,values);
        Configuracao.setId(insertid);
        return Configuracao;
    }

    // Getting single Employee
    public Configuracao getConfiguracao(long id) {

        Cursor cursor = database.query(DBHandler.TABLE_CONFIGURACAO,allColumns,DBHandler.CONFIGURACAO_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Configuracao e = new Configuracao(Integer.parseInt(cursor.getString(0)),cursor.getString(1));

        cursor.close();
        return e;
    }

    public List<Configuracao> getAllConfiguracao() {

        Cursor cursor = database.query(DBHandler.TABLE_CONFIGURACAO,allColumns,null,null,null, null, null);

        List<Configuracao> configuracaos = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Configuracao configuracao = new Configuracao();
                configuracao.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.CONFIGURACAO_COLUMN_ID)));
                configuracao.setEnderecoServidor(cursor.getString(cursor.getColumnIndex(DBHandler.CONFIGURACAO_COLUMN_ENDERECO_SERVIDOR)));
                configuracaos.add(configuracao);
            }
        }

        cursor.close();
        return configuracaos;
    }

    public int updateConfiguracao(Configuracao configuracao) {

        ContentValues values = new ContentValues();
        values.put(DBHandler.CONFIGURACAO_COLUMN_ENDERECO_SERVIDOR, configuracao.getEnderecoServidor());

        return database.update(DBHandler.TABLE_CONFIGURACAO, values,
                DBHandler.CONFIGURACAO_COLUMN_ID + "=?",new String[] { String.valueOf(configuracao.getId())});
    }

    public void removeConfiguracao() {

        database.delete(DBHandler.TABLE_CONFIGURACAO, null, null);
    }

    public Configuracao getData() {

        Cursor cursor = database.query(DBHandler.TABLE_CONFIGURACAO,allColumns,null,null,null, null, null);

        Configuracao data = null;
        while(cursor.moveToNext()){
            data = new Configuracao();
            data.setId(cursor.getLong(cursor.getColumnIndex(DBHandler.CONFIGURACAO_COLUMN_ID)));
            data.setEnderecoServidor(cursor.getString(cursor.getColumnIndex(DBHandler.CONFIGURACAO_COLUMN_ENDERECO_SERVIDOR)));
        }
        cursor.close();

        return data;
    }
}
