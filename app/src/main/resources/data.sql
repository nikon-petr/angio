INSERT INTO public.users (username, password, enabled, last_password_reset_date)
    VALUES ('admin@admin.ru', '$2a$04$YBwbgAAVn8S0luZ/mlO2V..SM.mmblq0lGs7zaCKIdnl8rhXBaLw2', true, '2018-01-01');
-- password: q1w2e3

INSERT INTO public.users_info (username, firstname, lastname, modified_date)
    VALUES ('admin@admin.ru', 'Admin', 'Admin', '2018-01-01');

INSERT INTO public.authorities (username, authority)
    VALUES ('admin@admin.ru', 'ROLE_ADMIN');

INSERT INTO public.users (username, password, enabled, last_password_reset_date)
VALUES ('user@user.ru', '$2a$04$YBwbgAAVn8S0luZ/mlO2V..SM.mmblq0lGs7zaCKIdnl8rhXBaLw2', true, '2018-01-01');
-- password: q1w2e3

INSERT INTO public.users_info (username, firstname, lastname, modified_date)
VALUES ('user@user.ru', 'User', 'User', '2018-01-01');

INSERT INTO public.authorities (username, authority)
VALUES ('user@user.ru', 'ROLE_USER')