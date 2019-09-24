package ru.basejava.iliketobreathe;

import ru.basejava.iliketobreathe.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("FirstName SecondName");

        resume.setContact(ContactType.EMAIL, "yandex@yandex.ru");
        resume.setContact(ContactType.PHONE, "+7 (999) 999-99-99");
        resume.setContact(ContactType.HOMEPAGE, "yandex.ru");
        resume.setContact(ContactType.GITHUB, "github777");

        resume.setSection(SectionType.PERSONAL, new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.setSection(SectionType.OBJECTIVE, new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        List<String> achievements = Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(achievements));

        List<String> qualifications = Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Organization> organizations = Arrays.asList(new Organization("Alcatel", "http://www.alcatel.ru/", LocalDate.of(1997, 7, 1), LocalDate.of(2005, 1, 1), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(organizations));

        List<Organization> education = Arrays.asList(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                        LocalDate.of(1984, 9, 1), LocalDate.of(1987, 6, 1), "Студент", ""));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(education));
    }
}
