package com.malharia.b3.b3malharizbar.model;

public class Rolo {
    public long Id;
    public String CodItem;
    public String NumLote;
    public String NumPeca;
    public String Local;
    public String Endereco;
    public String PermiteSubstituir;
    public int Ordem;
    public int Posicao;


    public Rolo(long id, String codItem, String numLote, String numPeca, String local, String endereco, String permiteSubstituir, int ordem, int posicao){
        this.Id = id;
        this.CodItem = codItem;
        this.NumLote = numLote;
        this.NumPeca = numPeca;
        this.Local = local;
        this.Endereco = endereco;
        this.PermiteSubstituir = permiteSubstituir;
        this.Ordem = ordem;
        this.Posicao = posicao;
    }

    public Rolo(){
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

    public String getLocal(){
        return this.Local;
    }

    public void setLocal(String local){
        this.Local = local;
    }

    public String getEndereco(){
        return this.Endereco;
    }

    public void setEndereco(String endereco){
        this.Endereco = endereco;
    }

    public String getPermiteSubstituir(){
        return this.PermiteSubstituir;
    }

    public void setPermiteSubstituir(String permiteSubstituir){
        this.PermiteSubstituir = permiteSubstituir;
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
