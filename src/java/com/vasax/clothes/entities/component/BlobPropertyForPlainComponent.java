package com.vasax.clothes.entities.component;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by Vasax on 03.05.2015.
 */
@Entity
@Table(name = "blobPropertyForPlainComponent")
public class BlobPropertyForPlainComponent {
    @Id
    @GeneratedValue
    private int id;

    private String keyR;
    @Lob
    private byte[] valueR;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlainComponent")
    private PlainComponent plainComponent;


    public BlobPropertyForPlainComponent() {
    }

    public BlobPropertyForPlainComponent(String keyR, byte[] valueR) {
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

    public byte[] getValueR() {
        return valueR;
    }

    public void setValueR(byte[] value) {
        this.valueR = value;
    }

    public PlainComponent getPlainComponent() {
        return plainComponent;
    }

    public void setPlainComponent(PlainComponent plainComponent) {
        this.plainComponent = plainComponent;
    }

    @Override
    public String toString() {
        return "BlobPropertyForPlainComponent{" +
                "id=" + id +
                ", keyR='" + keyR + '\'' +
                ", valueR=" + Arrays.toString(valueR) +
                ", plainComponent=" + plainComponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlobPropertyForPlainComponent that = (BlobPropertyForPlainComponent) o;

        if (id != that.id) return false;
        if (keyR != null ? !keyR.equals(that.keyR) : that.keyR != null) return false;
        if (!Arrays.equals(valueR, that.valueR)) return false;
        if (plainComponent != null ? !plainComponent.equals(that.plainComponent) : that.plainComponent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (keyR != null ? keyR.hashCode() : 0);
        result = 31 * result + (valueR != null ? Arrays.hashCode(valueR) : 0);
        result = 31 * result + (plainComponent != null ? plainComponent.hashCode() : 0);
        return result;
    }
}
