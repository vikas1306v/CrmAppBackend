package com.crm.service;

import com.crm.dto.response.GenericResponseBean;
import org.springframework.http.ResponseEntity;

public interface InventoryService {
boolean checkQuantityInInventory(Long itemId, Integer quantity);

    ResponseEntity<GenericResponseBean<?>> addQuantity(Long itemId, Integer quantity);
}
