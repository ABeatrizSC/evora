package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.domain.Purchase;
import com.github.abeatrizsc.event_service.enums.StatusEnum;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeResponseDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.PurchaseResponseDto;
import com.github.abeatrizsc.event_service.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseService {
    private final PurchaseRepository repository;
    private final AsaasService asaasService;

    @Transactional
    public PurchaseResponseDto createTicketPurchase(BigDecimal ticketPrice, String customerId) {
        log.info("createTicketPurchase() started");

        CreateChargeResponseDto chargeResponse = asaasService.createAsaasCharge(customerId, ticketPrice);

        Purchase newPurchase = new Purchase();
        newPurchase.setCode(chargeResponse.id());
        newPurchase.setStatus(StatusEnum.PENDING);
        newPurchase.setAmount(ticketPrice);
        newPurchase.setDueDate(LocalDate.now());

        newPurchase = repository.save(newPurchase);
        log.info("createTicketPurchase() - new purchased saved successfully");

        return new PurchaseResponseDto(newPurchase, chargeResponse);
    }

    @Transactional
    public void cancelTicketPurchase(String purchaseCode) {
        log.info("cancelTicketPurchase() started");
        Purchase ticketPurchase = getPurchaseByCode(purchaseCode);

        asaasService.refundAsaasCharge(purchaseCode);

        ticketPurchase.setStatus(StatusEnum.CANCELLED);
        ticketPurchase.setCancelledAt(LocalDateTime.now());

        repository.save(ticketPurchase);

        log.info("cancelTicketPurchase() ended successfully");
    }

    public Purchase getPurchaseByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Purchase"));
    }
}