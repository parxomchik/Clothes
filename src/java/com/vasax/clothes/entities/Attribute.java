package com.vasax.clothes.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vasax32 on 09.04.15.
 */
@Entity
@Table(name = "Attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategory")
    private Category category;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
    private List<Property> properties;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (id != attribute.id) return false;
//        if (category != null ? !category.equals(attribute.category) : attribute.category != null) return false;
        if (title != null ? !title.equals(attribute.title) : attribute.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
//        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
