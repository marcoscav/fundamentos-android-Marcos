package com.example.marcos.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.marcos.myapplication.model.persistence.SQLiteClientRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marcos on 20/07/2015.
 */
public class Client implements Serializable, Parcelable {

    private Integer id;
    private String name;
    private Integer age;
    private String phone;

    private ClientAddress address;

    public Client() {
        super();
    }

    public Client(Parcel in) {
        readToParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClientAddress getAddress() {
        return address;
    }

    public void setAddress(ClientAddress address) {
        this.address = address;
    }

    public void save() {
        SQLiteClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll() {
        return SQLiteClientRepository.getInstance().getAll();
    }

    public static List<Client> search(String parameter){
        return SQLiteClientRepository.getInstance().search(parameter);
    }


    public void delete() {
        SQLiteClientRepository.getInstance().delete(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        return !(address != null ? !address.equals(client.address) : client.address != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(phone == null ? "" : phone);
        dest.writeSerializable(address);
    }

    public void readToParcel(Parcel in) {
        int partialId = in.readInt();
        id = partialId == -1 ? null : partialId;
        name = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1 ? null : partialAge;
        phone = in.readString();
        address = (ClientAddress) in.readSerializable();
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {

        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };


}
