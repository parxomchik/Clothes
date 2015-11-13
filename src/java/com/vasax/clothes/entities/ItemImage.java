package com.vasax.clothes.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vasax32 on 12.04.15.
 */
@Entity
@Table(name = "itemImage")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId;

    private String title;
    @Lob
    private byte[] data;
    @Lob
    private byte[] smallData;
    @Lob
    private byte[] withWatermarkData;
    private boolean needToBeUpdated;
    private String color;
    private int colorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idItem")
    private Item item;


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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte[] getSmallData() {
        return smallData;
    }

    public void setSmallData(byte[] smallData) {
        this.smallData = smallData;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public byte[] getWithWatermarkData() {
        return withWatermarkData;
    }

    public void setWithWatermarkData(byte[] withWatermarkData) {
        this.withWatermarkData = withWatermarkData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemImage image = (ItemImage) o;

        if (id != image.id) return false;
        if (orderId != image.orderId) return false;
        if (needToBeUpdated != image.needToBeUpdated) return false;
        if (colorId != image.colorId) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (!Arrays.equals(data, image.data)) return false;
        if (!Arrays.equals(smallData, image.smallData)) return false;
        if (!Arrays.equals(withWatermarkData, image.withWatermarkData)) return false;
        if (color != null ? !color.equals(image.color) : image.color != null) return false;
        return !(item != null ? !item.equals(image.item) : image.item != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (data != null ? Arrays.hashCode(data) : 0);
        result = 31 * result + (smallData != null ? Arrays.hashCode(smallData) : 0);
        result = 31 * result + (withWatermarkData != null ? Arrays.hashCode(withWatermarkData) : 0);
        result = 31 * result + (needToBeUpdated ? 1 : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + colorId;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemImage{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", title='" + title + '\'' +
                ", data=" + Arrays.toString(data) +
                ", smallData=" + Arrays.toString(smallData) +
                ", withWatermarkData=" + Arrays.toString(withWatermarkData) +
                ", needToBeUpdated=" + needToBeUpdated +
                ", color='" + color + '\'' +
                ", colorId=" + colorId +
                ", item=" + item +
                '}';
    }

    public boolean isNeedToBeUpdated() {
        return needToBeUpdated;
    }

    public void setNeedToBeUpdated(boolean needToBeUpdated) {
        this.needToBeUpdated = needToBeUpdated;
    }
}
