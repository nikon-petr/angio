package com.angio.angiobackend.config.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  Permissions {

    ANALYSE_VIEW("Просмотр анализа"),
    ANALYSE_CREATE("Создание анализа"),
    ANALYSE_EDIT("Изменение анализа"),
    ANALYSE_EXECUTE_ACTION("Изменение анализа"),
    ANALYSE_REMOVE("Удаление анализа"),

    PATIENT_VIEW("Просмотр данных пациентов"),
    PATIENT_CREATE("Создание пациентов"),
    PATIENT_EDIT("Изменение пациентов"),
    PATIENT_REMOVE("Удаление пациентов"),

    DOCUMENT_UPLOAD("Загрузка документов в систему"),
    IMAGE_UPLOAD("Загрузка изображений в систему"),

    USER_VIEW("Просмотр данных пользователей системы"),
    USER_CREATE("Создание пользователей системы"),
    USER_EDIT("Изменение пользователей системы"),
    USER_REMOVE("Удаление пользователй из системы"),

    TOKEN_VIEW("Просмотр токенов пользователей"),
    TOKEN_REVOKE("Отзыв собственных refresh токенов"),
    TOKEN_REMOVE("Отзыв токенов любых пользователй");

    private String description;
}
