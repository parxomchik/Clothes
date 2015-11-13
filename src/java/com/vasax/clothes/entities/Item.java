package com.vasax.clothes.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 07.10.14.
 */
@Entity
@Table(name = "item")
//@Indexed
//@AnalyzerDef(name = "autoComplete",
//// Split input into tokens according to tokenizer
//        tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), //
//        filters = { //
//// Normalize token text to lowercase, as the user is unlikely to care about casing when searching for matches
//                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//// Index partial words starting at the front, so we can provide Autocomplete functionality
//                @TokenFilterDef(factory = NGramFilterFactory.class, params = { @Parameter(name = "maxGramSize", value = "1024") }),
//// Close filters & Analyzerdef
//        })
//@Analyzer(definition = "autoComplete")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId; //for ordering
//    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String title;
//    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String firm;
//    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String description;
    @Column(unique = true)
    private String productCode;
    private String packSize;
    private double price;
    private double packPrice;

    private Timestamp dateAdd;
    private Timestamp dateModified;
    private boolean active;
    private boolean hit;
    private boolean reservation;

    //for sale
    private Timestamp saleStart;
    private Timestamp saleEnd;
    private int saleValue; //percent

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @ManyToMany(mappedBy = "items")
    //@LazyCollection(LazyCollectionOption.FALSE)
    List<Order> orders;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ItemAttributeProperty> itemAttributeProperties;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemImage> images;

    public Item() {
//        dateAdd = new Timestamp(new Date().getTime());
//        dateModified =
    }
     public Item (Item prototype) {
         this.id = prototype.getId();
         this.title = prototype.getTitle();
         this.firm = prototype.getFirm();
         this.description = prototype.getDescription();
         this.packSize = prototype.getPackSize();
         this.price = prototype.getPrice();
         this.packPrice = prototype.getPackPrice();
         this.active = prototype.isActive();
         this.hit = prototype.isHit();
         this.category = prototype.getCategory();
         this.saleStart=prototype.getSaleStart();
         this.saleEnd=prototype.getSaleEnd();
         this.saleValue=prototype.getSaleValue();
         this.reservation=prototype.reservation;
     }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Timestamp dateAdd) {
        this.dateAdd = dateAdd;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ItemAttributeProperty> getItemAttributeProperties() {
        return itemAttributeProperties;
    }

    public void setItemAttributeProperties(List<ItemAttributeProperty> itemAttributeProperties) {
        this.itemAttributeProperties = itemAttributeProperties;
    }

    public List<ItemImage> getImages() {
        return images;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public double getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(double packPrice) {
        this.packPrice = packPrice;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getSaleStart() {
        return saleStart;
    }

    public void setSaleStart(Timestamp saleStart) {
        this.saleStart = saleStart;
    }

    public Timestamp getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(Timestamp saleEnd) {
        this.saleEnd = saleEnd;
    }

    public int getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(int saleValue) {
        this.saleValue = saleValue;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (orderId != item.orderId) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (Double.compare(item.packPrice, packPrice) != 0) return false;
        if (active != item.active) return false;
        if (hit != item.hit) return false;
        if (reservation != item.reservation) return false;
        if (saleValue != item.saleValue) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (firm != null ? !firm.equals(item.firm) : item.firm != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (productCode != null ? !productCode.equals(item.productCode) : item.productCode != null) return false;
        if (packSize != null ? !packSize.equals(item.packSize) : item.packSize != null) return false;
        if (dateAdd != null ? !dateAdd.equals(item.dateAdd) : item.dateAdd != null) return false;
        if (dateModified != null ? !dateModified.equals(item.dateModified) : item.dateModified != null) return false;
        if (saleStart != null ? !saleStart.equals(item.saleStart) : item.saleStart != null) return false;
        if (saleEnd != null ? !saleEnd.equals(item.saleEnd) : item.saleEnd != null) return false;
        return !(category != null ? !category.equals(item.category) : item.category != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + orderId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (firm != null ? firm.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (productCode != null ? productCode.hashCode() : 0);
        result = 31 * result + (packSize != null ? packSize.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(packPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateAdd != null ? dateAdd.hashCode() : 0);
        result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (hit ? 1 : 0);
        result = 31 * result + (reservation ? 1 : 0);
        result = 31 * result + (saleStart != null ? saleStart.hashCode() : 0);
        result = 31 * result + (saleEnd != null ? saleEnd.hashCode() : 0);
        result = 31 * result + saleValue;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                ", id=" + id +
                ", orderId=" + orderId +
                ", title='" + title + '\'' +
                ", firm='" + firm + '\'' +
                ", description='" + description + '\'' +
                ", productCode='" + productCode + '\'' +
                ", packSize='" + packSize + '\'' +
                ", price=" + price +
                ", packPrice=" + packPrice +
                ", dateAdd=" + dateAdd +
                ", dateModified=" + dateModified +
                ", active=" + active +
                ", hit=" + hit +
                ", saleStart=" + saleStart +
                ", saleEnd=" + saleEnd +
                ", saleValue=" + saleValue +
                "category=" + category +
                '}';
    }

    public static MinMaxPriceComparator minMaxPriceComparator = new MinMaxPriceComparator();
    public static MaxMinPriceComparator maxMinPriceComparator = new MaxMinPriceComparator();
    public static NewDateComparator newDateComparator = new NewDateComparator();
    public static PopularHitComparator popularHitComparator = new PopularHitComparator();

    private static class MinMaxPriceComparator implements Comparator<Item>{
        @Override
        public int compare(Item o1, Item o2) {
            return Double.valueOf(o1.getPrice()).compareTo(o2.getPrice());
        }
    }

    private static class MaxMinPriceComparator implements Comparator<Item>{
        @Override
        public int compare(Item o1, Item o2) {
            return Double.valueOf(o2.getPrice()).compareTo(o1.getPrice());
        }
    }

    private static class NewDateComparator implements Comparator<Item>{
        @Override
        public int compare(Item o1, Item o2) {
            return o2.getDateAdd().compareTo(o1.getDateAdd());
        }
    }

    private static class PopularHitComparator implements Comparator<Item>{
        @Override
        public int compare(Item o1, Item o2) {
            return Boolean.valueOf(o2.isHit()).compareTo(o1.isHit());
        }
    }
}
