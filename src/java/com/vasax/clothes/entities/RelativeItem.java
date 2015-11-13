package com.vasax.clothes.entities;

import javax.persistence.*;

/**
 * Created by vasax32 on 18.04.15.
 */
@Entity
@Table(name = "relativeItem")
public class RelativeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idItem")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "idRelative")
    private Item relative;

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

    public Item getRelative() {
        return relative;
    }

    public void setRelative(Item relative) {
        this.relative = relative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelativeItem that = (RelativeItem) o;

        if (id != that.id) return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;
        if (relative != null ? !relative.equals(that.relative) : that.relative != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (relative != null ? relative.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RelativeItem{" +
                "id=" + id +
                ", item=" + item +
                ", relative=" + relative +
                '}';
    }
}
