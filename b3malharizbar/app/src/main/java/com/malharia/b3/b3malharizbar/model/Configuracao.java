package com.malharia.b3.b3malharizbar.model;

public class Configuracao {
    public long Id;

    public String EnderecoServidor;

    public Configuracao(long id, String enderecoServidor){
        this.Id = id;
        this.EnderecoServidor = enderecoServidor;
    }

    public Configuracao(){
    }

    public long getId(){
        return this.Id;
    }

    public void setId(long id){
        this.Id = id;
    }

    public String getEnderecoServidor(){
        return this.EnderecoServidor;
    }

    public void setEnderecoServidor(String enderecoServidor){
        this.EnderecoServidor = enderecoServidor;
    }

}
