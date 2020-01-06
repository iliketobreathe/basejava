package ru.basejava.iliketobreathe;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.iliketobreathe.model.AbstractSection;
import ru.basejava.iliketobreathe.model.Resume;
import ru.basejava.iliketobreathe.model.StringSection;
import ru.basejava.iliketobreathe.util.JsonParser;

public class JsonParserTest {

    @Test
    public void testResume() {
        Resume R1 = ResumeTestData.resumeFill("UUID", "Name");
        String json = JsonParser.write(R1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new StringSection("Text");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}