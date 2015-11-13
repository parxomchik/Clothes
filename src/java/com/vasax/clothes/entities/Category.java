package com.vasax.clothes.entities;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 07.10.14.
 */
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String imageName;
    @Lob
    private byte[] image;
    private boolean active;
    private String dropbox;

    private int orderId = -1;

    @OneToMany(mappedBy = "category")
    private List<Item> items;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategory")
    private Category parent;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Category category = (Category) o;
//
//        if (active != category.active) return false;
//        if (id != category.id) return false;
//        if (description != null ? !description.equals(category.description) : category.description != null)
//            return false;
////        if (dishes != null ? !dishes.equals(category.dishes) : category.dishes != null) return false;
//        if (!Arrays.equals(image, category.image)) return false;
//        if (imageName != null ? !imageName.equals(category.imageName) : category.imageName != null) return false;
////        if (parent != null ? !parent.equals(category.parent) : category.parent != null) return false;
//        if (title != null ? !title.equals(category.title) : category.title != null) return false;
//
//        return true;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + (title != null ? title.hashCode() : 0);
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
//        result = 31 * result + (image != null ? Arrays.hashCode(image) : 0);
//        result = 31 * result + (active ? 1 : 0);
////        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
////        result = 31 * result + (parent != null ? parent.hashCode() : 0);
//        return result;
//    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                "orderId=" + orderId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", active=" + active +
                '}';
    }

    public String getDropbox() {
        return dropbox;
    }

    public void setDropbox(String dropbox) {
        this.dropbox = dropbox;
    }
}
