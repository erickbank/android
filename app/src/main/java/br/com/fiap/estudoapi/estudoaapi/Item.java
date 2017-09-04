package br.com.fiap.estudoapi.estudoaapi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    private int codigoFuncionario;
    private String Descricao;
    private Boolean Finalizado;
    private Date data;


    public Item(int codigoFuncionario, String descricao, Date data,Boolean finalizado) {
        this.codigoFuncionario = codigoFuncionario;
        Descricao = descricao;
        this.data = data;
        Finalizado = finalizado;

    }

    public int getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(int codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Boolean getFinalizado() {
        return Finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        Finalizado = finalizado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigoFuncionario + "Descricao: " + Descricao + "Data: " + data + "Finalizado:"  + Finalizado;
    }
}
