package com.vasax.clothes.entities;

import javax.persistence.*;

/**
 * Created by vasax32 on 11.04.15.
 */
@Entity
@Table(name = "itemAttributeProperty")
public class ItemAttributeProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idItem")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAttribute")
    private Attribute attribute;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProperty")
    private Property property;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemAttributeProperty that = (ItemAttributeProperty) o;

        if (id != that.id) return false;
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;
        if (property != null ? !property.equals(that.property) : that.property != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemAttributeProperty{" +
                "id=" + id +
                ", item=" + item +
                ", attribute=" + attribute +
                ", property=" + property +
                '}';
    }
}
