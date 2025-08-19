package com.anpetna.item.service;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.DeleteItemReq;
import com.anpetna.item.dto.ModifyItemReq;
import com.anpetna.item.dto.RegisterItemReq;
import com.anpetna.item.repository.ItemJpaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemJpaRepository itemJPARepository;

    ModelMapper modelMapper = new ModelMapper();

    //  이게 과연 좋은 코드일까
    @Override
    public ItemDTO registerItem(RegisterItemReq registerItemReq) {
        RegisterItemReq req = registerItemReq;
       modelMapper.typeMap(RegisterItemReq.class, ItemEntity.class)
                .addMappings(mapper -> mapper.skip(ItemEntity::setItemId))

                ;
/*                .validate(); // errors.throwValidationExceptionIfErrorsExist();
        //  예외처리를 잘한다란??*/
        //  Converter의 적재적소 활용봅은??

        ItemEntity savedItem = itemJPARepository.save(item);
        return modelMapper.map(savedItem, ItemDTO.class);
    }


    @Override
    public ItemDTO modifyItem(ModifyItemReq modifyItemReq) {

        return null;
    }

    @Override
    public ItemDTO deleteItem(DeleteItemReq deleteItemReq) {

        itemJPARepository.deleteById(deleteItemReq.getItemId());

        return null;
    }

    @Override
    public List<ItemDTO> getAllItems() {


        return null;
    }

    @Override
    public ItemDTO getOneItem(Long itemId) {

        return null;
    }

}