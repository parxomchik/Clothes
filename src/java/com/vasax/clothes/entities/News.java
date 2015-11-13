package com.vasax.clothes.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Vasax on 11.05.2015.
 */
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId;

    private String title;
    private String header;
    private String preview;
    private String description;

    @Lob
    private byte[] image;
    private String imageName;

    private Timestamp lastModified;
    private boolean showDate;
    private boolean active;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (orderId != news.orderId) return false;
        if (showDate != news.showDate) return false;
        if (active != news.active) return false;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (header != null ? !header.equals(news.header) : news.header != null) return false;
        if (preview != null ? !preview.equals(news.preview) : news.preview != null) return false;
        if (description != null ? !description.equals(news.description) : news.description != null) return false;
        if (!Arrays.equals(image, news.image)) return false;
        if (imageName != null ? !imageName.equals(news.imageName) : news.imageName != null) return false;
        if (lastModified != null ? !lastModified.equals(news.lastModified) : news.lastModified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + (preview != null ? preview.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (image != null ? Arrays.hashCode(image) : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (showDate ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", title='" + title + '\'' +
                ", header='" + header + '\'' +
                ", preview='" + preview + '\'' +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                ", lastModified=" + lastModified +
                ", showDate=" + showDate +
                ", active=" + active +
                '}';
    }
}
