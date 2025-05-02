package me.yusuf.ecommerce_builder.shared.types.dto;

public interface ProductForm {
    String getName();
    String getSpecifications();
    Double getPrice();
    String getDescription();
    byte[] getImage();
    String getCategoryName();
    Float getDiscount();
    Integer getStock();
    String getTags();
}
