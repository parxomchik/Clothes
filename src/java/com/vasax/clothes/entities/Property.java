package com.vasax.clothes.entities;

import javax.persistence.*;

/**
 * Created by vasax32 on 09.04.15.
 */
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAttribute")
    private Attribute attribute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property attribute = (Property) o;

        if (id != attribute.id) return false;
//        if (attribute != null ? !attribute.equals(attribute.attribute) : attribute.attribute != null) return false;
        if (title != null ? !title.equals(attribute.title) : attribute.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
//        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
