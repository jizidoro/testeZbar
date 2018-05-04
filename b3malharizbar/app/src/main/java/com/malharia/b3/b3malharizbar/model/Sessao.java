package com.malharia.b3.b3malharizbar.model;

import java.util.Date;

public class Sessao {
    public long Id;

    public int Ordem;

    public String Cracha;

    public String RoloTroca;

    public long FimOperacao;

    public long InicioOperacao;

    public Sessao(long id, String cracha, int ordem ,long inicioOperacao,long fimOperacao ,String roloTroca){
        this.Id = id;
        this.Cracha = cracha;
        this.Ordem = ordem;
        this.InicioOperacao = inicioOperacao;
        this.FimOperacao = fimOperacao;
        this.RoloTroca = roloTroca;
    }

    public Sessao(){
    }

    public long getId(){
        return this.Id;
    }

    public void setId(long id){
        this.Id = id;
    }

    public String getCracha(){
        return this.Cracha;
    }

    public void setCracha(String cracha){
        this.Cracha = cracha;
    }

    public int getOrdem(){return this.Ordem;}

    public void setOrdem(int ordem){this.Ordem = ordem;}

    public long getInicioOperacao(){return this.InicioOperacao;}

    public void setInicioOperacao(long inicioOperacao){this.InicioOperacao = inicioOperacao;}

    public long getFimOperacao(){return this.FimOperacao;}

    public void setFimOperacao(long fimOperacao){this.FimOperacao = fimOperacao;}

    public String getRoloTroca(){
        return this.RoloTroca;
    }

    public void setRoloTroca(String roloTroca){
        this.RoloTroca = roloTroca;
    }

}



