-- **************** super admin ****************
INSERT INTO public.users (username, password, enabled, last_password_reset_date)
    VALUES ('super.admin@angio.ru', '$2a$04$YBwbgAAVn8S0luZ/mlO2V..SM.mmblq0lGs7zaCKIdnl8rhXBaLw2', true, '2018-01-01');
-- password: q1w2e3

INSERT INTO public.users_info (username, firstName, lastName, modified_date)
    VALUES ('super.admin@angio.ru', 'Admin', 'Admin', '2018-01-01');

INSERT INTO public.authorities (username, authority)
    VALUES ('super.admin@angio.ru', 'ROLE_USER');
INSERT INTO public.authorities (username, authority)
    VALUES ('super.admin@angio.ru', 'ROLE_ADMIN');
INSERT INTO public.authorities (username, authority)
    VALUES ('super.admin@angio.ru', 'ROLE_SUPER_ADMIN');

-- **************** admin ****************
INSERT INTO public.users (username, password, enabled, last_password_reset_date)
    VALUES ('admin@angio.ru', '$2a$04$YBwbgAAVn8S0luZ/mlO2V..SM.mmblq0lGs7zaCKIdnl8rhXBaLw2', true, '2018-01-01');
-- password: q1w2e3

INSERT INTO public.users_info (username, firstName, lastName, modified_date)
    VALUES ('admin@angio.ru', 'Admin', 'Admin', '2018-01-01');

INSERT INTO public.authorities (username, authority)
    VALUES ('admin@angio.ru', 'ROLE_USER');
INSERT INTO public.authorities (username, authority)
    VALUES ('admin@angio.ru', 'ROLE_ADMIN');

-- **************** user ****************
INSERT INTO public.users (username, password, enabled, last_password_reset_date)
VALUES ('user@angio.ru', '$2a$04$YBwbgAAVn8S0luZ/mlO2V..SM.mmblq0lGs7zaCKIdnl8rhXBaLw2', true, '2018-01-01');
-- password: q1w2e3

INSERT INTO public.users_info (username, firstName, lastName, modified_date)
VALUES ('user@angio.ru', 'Геннадий', 'Врачебный', '2018-01-01');

INSERT INTO public.authorities (username, authority)
VALUES ('user@angio.ru', 'ROLE_USER');

-- **************** patient ****************
INSERT INTO public.patients (firstname, lastname, patronymic, email, phone, bday, location_address, work_address, policy, comment)
    VALUES('Иван', 'Заболевальный', 'Семёнович', 'ivan_sem@gmail.com', '89271237898', '1985-05-06 00:00:00.000000', 'Саратов, ул. Посадского 228/226, 10', 'Саратов, ул. Московская 56', '9087675467878954', '');
INSERT INTO public.patients (firstname, lastname, patronymic, email, phone, bday, location_address, work_address, policy, comment)
    VALUES('Пётр', 'Семакин', 'Владимирович', 'semakin_sem@mail.ru', '89279385898', '1960-12-12 00:00:00.000000', 'Саратов, ул. Рахова 21, 90', 'На пенсии', '8207675467872250', '');
INSERT INTO public.patients (firstname, lastname, patronymic, email, phone, bday, location_address, work_address, policy, comment)
    VALUES('Николай', 'Козловский', 'Николаевич', 'kozlito@yandex.ru', '89699389098', '1992-02-16 00:00:00.000000', 'Саратов, ул. Чернышевского 240, 1', 'Саратов, ул. Степана Разина 8', '1276215467890790', '');

-- **************** analyse_info ****************
INSERT INTO public.analyses_info (username, patient_id, name, short_description, full_description, analyse_type, comment, img, analyse_date, finished, conclusion)
    VALUES ('user@angio.ru', 1, 'Оценка сосудистой системы глазного дна', 'Анализ глазного дна с целью выявления основных геометрических характеристик сосудов', '', 'Первичный анализ', '', '02.05.2018 07:40:27.630.png', '2018-04-05 12:09:00.000000', false, '');
INSERT INTO public.analyses_info (username, patient_id, name, short_description, full_description, analyse_type, comment, img, analyse_date, finished, conclusion)
    VALUES ('user@angio.ru', 2, 'Первичный анализ глазного дна', 'Выявление проблемных зон, большое кол-во зон ишемии', '', 'Первичный анализ', '', '02.05.2018 07:40:27.630.png', '2018-04-06 11:21:00.000000', false, '');
INSERT INTO public.analyses_info (username, patient_id, name, short_description, full_description, analyse_type, comment, img, analyse_date, finished, conclusion)
    VALUES ('user@angio.ru', 3, 'Вторичный анализ, результаты', 'Определение улучшений', '', 'Вторичный анализ', '', '02.05.2018 07:40:27.630.png', '2018-05-06 09:29:00.000000', false, '');