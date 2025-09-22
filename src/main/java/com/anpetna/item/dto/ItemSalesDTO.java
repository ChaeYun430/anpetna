package com.anpetna.item.dto;

import com.anpetna.item.constant.ItemCategory;

import java.time.LocalDate;

public interface ItemSalesDTO {

    Long getItemId();
    ItemCategory getItemCategory();
    int getQuantity();
    LocalDate getPeriod();
}
