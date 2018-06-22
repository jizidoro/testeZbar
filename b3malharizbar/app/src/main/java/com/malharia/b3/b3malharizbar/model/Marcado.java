package com.malharia.b3.b3malharizbar.model;

public class Marcado {
    public long Id;

    public String CodItem;

    public String NumLote;

    public String NumPeca;

    public int Ordem;

    public int Posicao;



    public Marcado(long id, String codItem, String numLote, String numPeca, int ordem, int posicao){
        this.Id = id;
        this.CodItem = codItem;
        this.NumLote = numLote;
        this.NumPeca = numPeca;
        this.Ordem = ordem;
        this.Posicao = posicao;
    }

    public Marcado(){
    }

    public long getId(){
        return this.Id;
    }

    public void setId(long id){
        this.Id = id;
    }

    public String getCodItem(){
        return this.CodItem;
    }

    public void setCodItem(String codItem){
        this.CodItem = codItem;
    }

    public String getNumLote(){
        return this.NumLote;
    }

    public void setNumLote(String numLote){
        this.NumLote = numLote;
    }

    public String getNumPeca(){
        return this.NumPeca;
    }

    public void setNumPeca(String numPeca){
        this.NumPeca = numPeca;
    }

    public int getOrdem(){
        return this.Ordem;
    }

    public void setOrdem(int ordem){
        this.Ordem = ordem;
    }

    public int getPosicao(){
        return this.Posicao;
    }

    public void setPosicao(int posicao){
        this.Posicao = posicao;
    }
}
