package com.github.abeatrizsc.user_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    CREATOR("Creator"),
    PARTICIPANT("Participant");

    private final String displayName;
}
