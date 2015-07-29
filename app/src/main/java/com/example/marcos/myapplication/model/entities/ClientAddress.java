package com.example.marcos.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Marcos on 27/07/2015.
 */
public class ClientAddress implements Serializable, Parcelable {

    private String cep;
    private String tipoDeLogradouro;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

    public ClientAddress() {
        super();
    }

    public ClientAddress(Parcel in) {
        readToParcel(in);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientAddress that = (ClientAddress) o;

        if (cep != null ? !cep.equals(that.cep) : that.cep != null) return false;
        if (tipoDeLogradouro != null ? !tipoDeLogradouro.equals(that.tipoDeLogradouro) : that.tipoDeLogradouro != null)
            return false;
        if (logradouro != null ? !logradouro.equals(that.logradouro) : that.logradouro != null)
            return false;
        if (bairro != null ? !bairro.equals(that.bairro) : that.bairro != null) return false;
        if (cidade != null ? !cidade.equals(that.cidade) : that.cidade != null) return false;
        return !(estado != null ? !estado.equals(that.estado) : that.estado != null);
    }

    @Override
    public int hashCode() {
        int result = cep != null ? cep.hashCode() : 0;
        result = 31 * result + (tipoDeLogradouro != null ? tipoDeLogradouro.hashCode() : 0);
        result = 31 * result + (logradouro != null ? logradouro.hashCode() : 0);
        result = 31 * result + (bairro != null ? bairro.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientAddress{" +
                "cep='" + cep + '\'' +
                ", tipoDeLogradouro='" + tipoDeLogradouro + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cep == null ? "" : cep);
        dest.writeString(tipoDeLogradouro == null ? "" : tipoDeLogradouro);
        dest.writeString(logradouro == null ? "" : logradouro);
        dest.writeString(bairro == null ? "" : bairro);
        dest.writeString(cidade == null ? "" : cidade);
        dest.writeString(estado == null ? "" : estado);
    }

    public void readToParcel(Parcel in) {
        cep = in.readString();
        tipoDeLogradouro = in.readString();
        logradouro = in.readString();
        bairro = in.readString();
        cidade = in.readString();
        estado = in.readString();
    }

    public static final Parcelable.Creator<ClientAddress> CREATOR = new Parcelable.Creator<ClientAddress>() {

        @Override
        public ClientAddress createFromParcel(Parcel source) {
            return new ClientAddress(source);
        }

        @Override
        public ClientAddress[] newArray(int size) {
            return new ClientAddress[size];
        }
    };


}
