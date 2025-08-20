package com.anpetna.item.config;

import com.anpetna.coreDomain.ImageEntity;
import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    @Autowired
   private ModelMapper modelMapper = new ModelMapper();
    //  필드를 final로 꼭 해야하는가

   public TypeMap<RegisterItemReq, ItemEntity> cItemMapReq() {
       TypeMap<RegisterItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(RegisterItemReq.class, ItemEntity.class);

       typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId));
       typeMap.setPostConverter(ctx-> {
           RegisterItemReq req = ctx.getSource();
           ItemEntity entity = ctx.getDestination();
           entity.getImages().clear();

           if (req.getImages() != null) {
               req.getImages().forEach(img -> entity.getImages().add(modelMapper.map(img, ImageEntity.class)));
           }
           return entity;
       });
       return typeMap;
   }

   public TypeMap<ItemEntity, RegisterItemRes> cItemMapRes() {
        TypeMap<ItemEntity, RegisterItemRes> typeMap = modelMapper.createTypeMap(ItemEntity.class, RegisterItemRes.class);
        typeMap.addMappings(mapper -> {mapper.skip(RegisterItemRes::setRes);});
        return typeMap;
    }

    public TypeMap<ModifyItemReq, ItemEntity> uItemMapReq() {
        TypeMap<ModifyItemReq, ItemEntity> typeMap = modelMapper.createTypeMap(ModifyItemReq.class, ItemEntity.class);
        typeMap.addMappings(mapper -> mapper.skip(ItemEntity::setItemId));
        typeMap.setPostConverter(ctx-> {
            ModifyItemReq req = ctx.getSource();
            ItemEntity entity = ctx.getDestination();
            entity.getImages().clear();
            if (req.getImages() != null) {
                req.getImages().forEach(img -> entity.getImages().add(modelMapper.map(img, ImageEntity.class)));
            }
            return entity;
        });
        return typeMap;
    }

    public TypeMap<ItemEntity, ModifyItemRes> uItemMapRes() {
        TypeMap<ItemEntity, ModifyItemRes> typeMap = modelMapper.createTypeMap(ItemEntity.class, ModifyItemRes.class);

        return typeMap;
    }

}
