package com.anpetna.item.config;

import com.anpetna.coreDomain.ImageEntity;
import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemRes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    @Autowired
   private ModelMapper modelMapper = new ModelMapper();
    //  필드를 final로 꼭 해야하는가

   public TypeMap<RegisterItemReq, ItemEntity> cItemMapReq() {
       TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);

       typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId));
       typeMap.setPostConverter(ctx-> {
           RegisterItemReq src = ctx.getSource();
           ItemEntity des = ctx.getDestination();

           des.getImages().clear();

           if (src.getImages() != null) {
               src.getImages().forEach(img -> des.addImage(modelMapper.map(img, ImageEntity.class)));
           }
           return des;
       });
       return typeMap;
   }

   public TypeMap<ItemEntity, RegisterItemRes> cItemMapRes() {
        TypeMap<ItemEntity, RegisterItemRes> typeMap = modelMapper.createTypeMap(ItemEntity.class, RegisterItemRes.class);
        typeMap.addMappings(mapper -> {mapper.skip(RegisterItemRes::setRes);});
        return typeMap;
    }

    public TypeMap<ItemEntity, SearchOneItemRes> r1ItemMapRes() {
        TypeMap<ItemEntity, SearchOneItemRes> typeMap = modelMapper.createTypeMap(ItemEntity.class, SearchOneItemRes.class);

        typeMap.setPostConverter(ctx -> {
            ItemEntity src = ctx.getSource();
            SearchOneItemRes des = ctx.getDestination();

            if (src.getImages() != null && !src.getImages().isEmpty()) {
                des.setImages(
                        src.getImages().stream()
                                .map(img -> modelMapper.map(img, ImageDTO.class))
                                .collect(Collectors.toList())
                );
            } else {
                des.setImages(null); // 명시적으로 null 내려줌
            }
            return des;
        });

        return typeMap;
    }
}
