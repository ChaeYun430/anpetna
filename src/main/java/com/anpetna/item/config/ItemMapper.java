package com.anpetna.item.config;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    @Autowired
   private ModelMapper modelMapper;
    //  필드를 final로 꼭 해야하는가

   public TypeMap<RegisterItemReq, ItemEntity> cItemMapReq() {
       TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);
       typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId));
       return typeMap;
   }

/*    public TypeMap<ItemEntity, RegisterItemRes> cItemMapRes() {
        TypeMap<ItemEntity, RegisterItemRes> typeMap = modelMapper.createTypeMap(ItemEntity.class, RegisterItemRes.class);
        typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId));
        return typeMap;
    }*/

    TypeMap<ModifyItemReq, ItemEntity> uItemMapRes() {
        TypeMap<ModifyItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(ModifyItemReq.class, ItemEntity.class);
        typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId))
                .addMappings(mapper -> mapper.skip(ItemEntity::setItemName))
                .addMappings(mapper -> mapper.skip(ItemEntity::setItemPrice))
                .addMappings(mapper -> mapper.skip(ItemEntity::setItemCategory));
        return typeMap;
    }

  /*  TypeMap<RegisterItemReq, ItemEntity> registerItemMap() {
        TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);
        typeMap.addMappings(mapper ->
                mapper.skip(ItemEntity::setItemId));
        return typeMap;
    }

    TypeMap<RegisterItemReq, ItemEntity> registerItemMap() {
        TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);
        typeMap.addMappings(mapper ->
                mapper.skip(ItemEntity::setItemId));
        return typeMap;
    }

    TypeMap<RegisterItemReq, ItemEntity> registerItemMap() {
        TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);
        typeMap.addMappings(mapper ->
                mapper.skip(ItemEntity::setItemId));
        return typeMap;
    }*/

/*    public ItemEntity dtoToEntity(ItemDTO itemDTO) {
        TypeMap<ItemDTO, ItemEntity> typeMap = modelMapper.createTypeMap(ItemDTO.class, ItemEntity.class);
        return typeMap.map(itemDTO);
    }

    public ItemDTO entityToDto(ItemEntity itemEntity) {
        TypeMap<ItemEntity, ItemDTO> typeMap = modelMapper.createTypeMap(ItemEntity.class, ItemDTO.class);
        return typeMap.map(itemEntity);
    }*/



}
