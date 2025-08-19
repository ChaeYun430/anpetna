package com.anpetna.item.service;


import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.DeleteItemReq;
import com.anpetna.item.dto.ModifyItemReq;
import com.anpetna.item.dto.RegisterItemReq;


import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItems();

    ItemDTO getOneItem(Long itemId);

    //관리자 권한
    ItemDTO registerItem(RegisterItemReq registerItemReq);

    ItemDTO modifyItem(ModifyItemReq modifyItemReq);

    ItemDTO deleteItem(DeleteItemReq deleteItemReq);

}
