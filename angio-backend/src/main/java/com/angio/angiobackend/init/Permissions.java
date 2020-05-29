package com.angio.angiobackend.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  Permissions {

    ANALYSE_VIEW("Просмотр анализа"),
    ANALYSE_VIEW_ALL("Просмотр всех анализов"),
    ANALYSE_CREATE("Создание анализа"),
    ANALYSE_EDIT("Изменение анализа"),
    ANALYSE_EXECUTE_ACTION("Изменение анализа"),
    ANALYSE_REMOVE("Удаление анализа"),
    ANALYSE_PURGE_DELETED("Очистка БД от анализов в статусе DELETED"),

    PATIENT_VIEW("Просмотр данных пациентов"),
    PATIENT_CREATE("Создание пациентов"),
    PATIENT_EDIT("Изменение пациентов"),
    PATIENT_REMOVE("Удаление пациентов"),

    DOCUMENT_UPLOAD("Загрузка документов в систему"),
    IMAGE_UPLOAD("Загрузка изображений в систему"),
    VIDEO_UPLOAD("Загрузка видео в систему"),
    IMAGE_UPLOAD_PURGE_UNUSED("Очистка БД от неиспользуемых изображений"),

    USER_VIEW("Просмотр данных пользователей системы"),
    USER_CREATE("Создание пользователей системы"),
    USER_EDIT("Изменение пользователей системы"),
    USER_REMOVE("Удаление пользователй из системы"),

    ROLE_VIEW("Просмотр ролей"),
    ROLE_CREATE("Создание ролей"),
    ROLE_EDIT("Редактирование ролей"),
    ROLE_REMOVE("Удаление ролей"),

    ORGANIZATION_VIEW("Просмотр данных организаций"),
    ORGANIZATION_CREATE("Создание организаций"),
    ORGANIZATION_EDIT("Редактирование оорганизаций"),
    ORGANIZATION_REMOVE("Удаление организаций"),

    ACTUATOR_VIEW("Взаимодействие с Spring Actuator"),

    TOKEN_VIEW("Просмотр токенов пользователей"),
    TOKEN_REVOKE("Отзыв собственных refresh токенов"),
    TOKEN_REMOVE("Отзыв токенов любых пользователй"),

    PUSH_NOTIFICATION_RECEIVE("Получение собственных push-уведомлений"),
    PUSH_NOTIFICATION_SEND("Отправка push-уведомлений пользователям"),

    DASHBOARD_VIEW("Просмотр панели управления");

    private String description;
}
