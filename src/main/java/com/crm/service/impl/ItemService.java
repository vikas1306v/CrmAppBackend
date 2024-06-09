package com.crm.service.impl;

import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.Item;
import com.crm.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ResponseEntity<GenericResponseBean<Item>> createItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return ResponseEntity.ok(GenericResponseBean.<Item>builder().data(savedItem).message("item create successfully").build());
    }
}
