package com.crm.controller;

import com.crm.dto.response.GenericResponseBean;
import com.crm.service.impl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryServiceImpl inventoryService;
    @PostMapping("/add-quantity/{itemId}/{quantity}")
    public ResponseEntity<GenericResponseBean<?>> addQuantity(@PathVariable Long itemId,@PathVariable Integer quantity) {
        return inventoryService.addQuantity(itemId, quantity);
    }
}
