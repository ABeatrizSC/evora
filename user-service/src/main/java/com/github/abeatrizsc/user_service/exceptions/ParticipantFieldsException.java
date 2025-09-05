package com.github.abeatrizsc.user_service.exceptions;

public class ParticipantFieldsException extends RuntimeException {
    public ParticipantFieldsException() {
        super("To register as an event participant, the 'document' and 'mobile phone' fields are required.");
    }
}
