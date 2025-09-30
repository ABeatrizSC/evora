package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.exceptions.AsaasServiceClientException;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.feign.asaas.AsaasServiceClient;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeRequestDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class AsaasService {
    private final AsaasServiceClient asaasClient;

    public CreateChargeResponseDto createAsaasCharge(String customerId, BigDecimal ticketValue) {
        log.info("createAsaasCharge() started");

        CreateChargeRequestDto charge = CreateChargeRequestDto.builder()
                .customer(customerId)
                .billingType("UNDEFINED")
                .value(ticketValue)
                .dueDate(LocalDate.now())
                .build();

        try {
            CreateChargeResponseDto responseDto = asaasClient.createCharge(charge);
            log.info("createAsaasCharge() ended successfully");
            return responseDto;
        } catch (AsaasServiceClientException e) {
            log.info("CATCH an AsaasServiceClientException in createAsaasCharge(): {}", e.getMessage());
            throw e;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("User");
        }
    }

    public void refundAsaasCharge(String chargeId) {
        log.info("refundAsaasCharge() started with charge id {}", chargeId);

        try {
            asaasClient.refundCharge(chargeId);
            log.info("refundAsaasCharge() ended successfully");
        } catch (AsaasServiceClientException e) {
            log.info("CATCH an AsaasServiceClientException in refundAsaasCharge(): {}", e.getMessage());
            throw e;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("User");
        }
    }
}
