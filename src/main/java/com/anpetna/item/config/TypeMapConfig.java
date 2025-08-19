package com.anpetna.item.config;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TypeMapConfig {

    ModelMapper modelMapper = new ModelMapper();

    public void registerMap(RegisterItemReq req, ItemEntity Item){
        var mapResult = modelMapper.typeMap(RegisterItemReq.class, ItemEntity.class)
                .addMappings(mapper -> mapper.skip(ItemEntity::setItemId))
                .map(req, Item);
        return mapResult;
    }

}
