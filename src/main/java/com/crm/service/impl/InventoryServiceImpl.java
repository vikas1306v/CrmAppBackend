package com.crm.service.impl;
import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.Inventory;
import com.crm.repository.InventoryRepository;
import com.crm.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    @Override
    public boolean checkQuantityInInventory(Long itemId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByItemId(itemId).orElse(null);
        return inventory != null && inventory.getQuantity() >= quantity;
    }

    @Override
    public ResponseEntity<GenericResponseBean<?>> addQuantity(Long itemId, Integer quantity) {
        inventoryRepository.findByItemId(itemId).ifPresentOrElse(
            inventory -> {
                inventory.setQuantity(inventory.getQuantity() + quantity);
                inventoryRepository.save(inventory);
            },
            () -> inventoryRepository.save(Inventory.builder().itemId(itemId).quantity(quantity).build())
        );
        return ResponseEntity.ok(GenericResponseBean.builder().status(true).message("quantity added successfully").build());
    }
}
