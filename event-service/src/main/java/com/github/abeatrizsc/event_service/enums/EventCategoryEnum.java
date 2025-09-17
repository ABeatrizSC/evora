package com.github.abeatrizsc.event_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventCategoryEnum {
    MUSIC("Music"),
    SPORTS("Sports"),
    BUSINESS("Business"),
    EDUCATION("Education"),
    TECHNOLOGY("Technology"),
    HEALTH("Health"),
    ART("Art"),
    THEATER("Theater"),
    FESTIVAL("Festival"),
    LECTURE("Lecture"),
    OTHER("Other");

    private final String displayName;
}

