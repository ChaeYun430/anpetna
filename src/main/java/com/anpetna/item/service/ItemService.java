package com.anpetna.item.service;


import com.anpetna.item.repository.dto.ItemDTO;
import com.anpetna.item.repository.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.repository.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.repository.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.repository.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.repository.dto.registerItem.RegisterItemReq;
import com.anpetna.item.repository.dto.registerItem.RegisterItemRes;


import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItems();

    ItemDTO getOneItem(Long itemId);

    //관리자 권한
    RegisterItemRes registerItem(RegisterItemReq registerItemReq);

    ModifyItemRes modifyItem(ModifyItemReq modifyItemReq);

    DeleteItemRes deleteItem(DeleteItemReq deleteItemReq);

}
