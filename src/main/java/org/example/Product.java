package org.example;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productId;
    private String productName;
    private double productPrice;
    private int productTotal;

    // Constructor không tham số
    public Product() {
    }

    // Constructor đầy đủ tham số
    public Product(int productId, String productName, double productPrice, int productTotal) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productTotal = productTotal;
    }

    // Getter và Setter cho productId
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter và Setter cho productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter và Setter cho productPrice
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    // Getter và Setter cho productTotal
    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    // Phương thức toString để xuất thông tin đối tượng
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productTotal=" + productTotal +
                '}';
    }
}
