package com.anpetna.item.service;

import com.anpetna.item.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.ReigisterItemRes;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemRes;


import java.util.List;

public interface ItemService {
    List<SearchAllItemsReq> getAllItems(SearchAllItemsReq req);

    SearchOneItemRes getOneItem(SearchOneItemReq req);

    ReigisterItemRes registerItem(RegisterItemReq req);

    ModifyItemRes modifyItem(ModifyItemReq req);

    DeleteItemRes deleteItem(DeleteItemReq req);
}
