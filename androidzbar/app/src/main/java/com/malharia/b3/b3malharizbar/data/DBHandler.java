package com.malharia.b3.b3malharizbar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "b3malharia.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SESSAO = "sessao";
    public static final String SESSAO_COLUMN_ID = "id";
    public static final String SESSAO_COLUMN_CRACHA = "cracha";
    public static final String SESSAO_COLUMN_ORDEM = "ordem";
    public static final String SESSAO_COLUMN_INICIOOPERACAO = "inicioOperacao";
    public static final String SESSAO_COLUMN_FIMOPERACAO = "fimOperacao";
    public static final String SESSAO_COLUMN_ROLOTROCA = "roloTroca";


    private static final String SESSAO_TABLE_CREATE =
            "CREATE TABLE " + TABLE_SESSAO + " (" +
                    SESSAO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SESSAO_COLUMN_CRACHA + " TEXT, " +
                    SESSAO_COLUMN_ORDEM + " INTEGER, " +
                    SESSAO_COLUMN_INICIOOPERACAO + " INTEGER, " +
                    SESSAO_COLUMN_FIMOPERACAO + " INTEGER, " +
                    SESSAO_COLUMN_ROLOTROCA + " TEXT " +
                    ")";

    public static final String TABLE_ORDEM = "ordem";
    public static final String ORDEM_COLUMN_ID = "id";
    public static final String ORDEM_COLUMN_ORDEM = "ordem";


    private static final String ORDEM_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ORDEM + " (" +
                    ORDEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDEM_COLUMN_ORDEM + " INTEGER " +
                    ")";

    public static final String TABLE_ROLO  = "rolo";
    public static final String ROLO_COLUMN_ID = "id";
    public static final String ROLO_COLUMN_CODITEM = "codItem";
    public static final String ROLO_COLUMN_NUMLOTE = "numLote";
    public static final String ROLO_COLUMN_NUMPECA = "numPeca";
    public static final String ROLO_COLUMN_LOCAL = "local";
    public static final String ROLO_COLUMN_ENDERECO = "endereco";
    public static final String ROLO_COLUMN_PERMITESUBSTITUIR = "permiteSubstituir";
    public static final String ROLO_COLUMN_ORDEM = "ordem";
    public static final String ROLO_COLUMN_POSICAO = "posicao";

    private static final String ROLO_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ROLO + " (" +
                    ROLO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ROLO_COLUMN_CODITEM + " TEXT, " +
                    ROLO_COLUMN_NUMLOTE + " TEXT, " +
                    ROLO_COLUMN_NUMPECA + " TEXT, " +
                    ROLO_COLUMN_LOCAL + " TEXT, " +
                    ROLO_COLUMN_ENDERECO + " TEXT, " +
                    ROLO_COLUMN_PERMITESUBSTITUIR + " TEXT, " +
                    ROLO_COLUMN_ORDEM + " TEXT, " +
                    ROLO_COLUMN_POSICAO  + " TEXT " +
                    ")";

    public static final String TABLE_MARCADO = "marcado";
    public static final String MARCADO_COLUMN_ID = "id";
    public static final String MARCADO_COLUMN_CODITEM = "codItem";
    public static final String MARCADO_COLUMN_NUMLOTE = "numLote";
    public static final String MARCADO_COLUMN_NUMPECA = "numPeca";
    public static final String MARCADO_COLUMN_ORDEM   = "ordem";
    public static final String MARCADO_COLUMN_POSICAO = "posicao";


    private static final String MARCADO_TABLE_CREATE =
            "CREATE TABLE " + TABLE_MARCADO + " (" +
                    MARCADO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MARCADO_COLUMN_CODITEM  + " TEXT, " +
                    MARCADO_COLUMN_NUMLOTE  + " TEXT, " +
                    MARCADO_COLUMN_NUMPECA  + " TEXT, " +
                    MARCADO_COLUMN_ORDEM + " TEXT, " +
                    MARCADO_COLUMN_POSICAO + " TEXT " +
                    ")";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SESSAO_TABLE_CREATE);
        db.execSQL(ORDEM_TABLE_CREATE);
        db.execSQL(ROLO_TABLE_CREATE);
        db.execSQL(MARCADO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSAO);
        db.execSQL(SESSAO_TABLE_CREATE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDEM);
        db.execSQL(ORDEM_TABLE_CREATE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLO);
        db.execSQL(ROLO_TABLE_CREATE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCADO);
        db.execSQL(MARCADO_TABLE_CREATE);
    }
}

