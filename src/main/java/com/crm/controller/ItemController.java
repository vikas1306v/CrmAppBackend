package com.crm.controller;

import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.Item;
import com.crm.service.impl.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    @PostMapping("/create")
    public ResponseEntity<GenericResponseBean<Item>> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }
}
