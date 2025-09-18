package com.github.abeatrizsc.event_service.exceptions;

public class CancellationTicketPurchaseException extends RuntimeException {
    public CancellationTicketPurchaseException() {
        super("This ticket cannot be cancelled because it has already been cancelled or the cancellation period has ended.");
    }
}
