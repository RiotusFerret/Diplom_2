package data;

import com.github.javafaker.Faker;

public class TestData {
    public final static String BASE_URL = "https://stellarburgers.education-services.ru";

    static Faker user = new Faker();
    public final static String EMAIL = user.name().lastName() + System.currentTimeMillis() + "@gmail.com";
    public final static String NAME = user.name().firstName();
    public final static String PASSWORD = user.regexify("[0-9]{6}");
}
