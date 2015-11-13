package com.vasax.clothes.entities.component;

import javax.persistence.*;

/**
 * Created by Vasax on 03.05.2015.
 */
@Entity
@Table(name = "propertyForPlainComponent")
public class PropertyForPlainComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String keyR;
    private String valueR;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlainComponent")
    private PlainComponent plainComponent;


    public PropertyForPlainComponent() {
    }

    public PropertyForPlainComponent(String keyR, String valueR) {
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

    public PlainComponent getPlainComponent() {
        return plainComponent;
    }

    public void setPlainComponent(PlainComponent plainComponent) {
        this.plainComponent = plainComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyForPlainComponent that = (PropertyForPlainComponent) o;

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
        return "PropertyForPlainComponent{" +
                "id=" + id +
                ", key='" + keyR + '\'' +
                ", value='" + valueR + '\'' +
                '}';
    }
}
