package com.vasax.clothes.entities;

import javax.persistence.*;

/**
 * Created by Vasax on 03.05.2015.
 */
@Entity
@Table(name = "globalSetting")
public class GlobalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String keyR;
    private String valueR;

    public GlobalSetting() {
    }

    public GlobalSetting(String keyR, String valueR) {
        this.keyR = keyR;
        this.valueR = valueR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyR() {
        return keyR;
    }

    public void setKeyR(String key) {
        this.keyR = key;
    }

    public String getValueR() {
        return valueR;
    }

    public void setValueR(String value) {
        this.valueR = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalSetting that = (GlobalSetting) o;

        if (id != that.id) return false;
        if (keyR != null ? !keyR.equals(that.keyR) : that.keyR != null) return false;
        return !(valueR != null ? !valueR.equals(that.valueR) : that.valueR != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (keyR != null ? keyR.hashCode() : 0);
        result = 31 * result + (valueR != null ? valueR.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GlobalSetting{" +
                "id=" + id +
                ", key='" + keyR + '\'' +
                ", value='" + valueR + '\'' +
                '}';
    }
}
