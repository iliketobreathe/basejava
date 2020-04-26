package ru.basejava.iliketobreathe;

import ru.basejava.iliketobreathe.model.Resume;
import ru.basejava.iliketobreathe.storage.SqlStorage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddResumeSqlBase {
    public static void main(String[] args) {
        SqlStorage sqlStorage = (SqlStorage) Config.get().getStorage();
        sqlStorage.clear();
        List<String> list = Arrays.asList("Семенов Алексей", "Карташов Алексей", "Стоянов Юрий", "Курпатова Анастасия", "Аристархов Людвиг",
                "Баркова Людмила", "Кириллов Роман", "Романов Кирилл", "Яковлев Петр", "Юсупова Мария",
                "Алферов Игнат", "Иванов Иван", "Лякин Георгий", "Самойлов Илья", "Агушина Светлана", "Ульянов Василий", "Бродкина Василиса");

        for (String resume : list) {
            final String UUID_RESUME = UUID.randomUUID().toString();
            Resume resume1 = ResumeTestData.resumeFill(UUID_RESUME, resume);
            sqlStorage.save(resume1);
        }
    }
}
