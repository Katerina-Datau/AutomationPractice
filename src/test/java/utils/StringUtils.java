package utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

public class StringUtils extends Faker {

    /**
     * String generator:
     *
     * @param generatedString
     * @return
     */

    public static String createBothifiedString(String generatedString) {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify(generatedString);
    }

    public static String createValidEmail() {
        return createBothifiedString("????##@##gmail.com");
    }

    public static String createInvalidEmailDoubleToad() {
        return createBothifiedString("????##@@##gmail.com");
    }

    public static String createInvalidEmailEndsWithDot() {
        return createBothifiedString("?????##@##gmail.com.");
    }

    public static String createInvalidEmailStartsWithDot() {
        return createBothifiedString(".????##@##gmail.com");
    }

    public static String createInvalidEmailNoSyntax() {
        return createBothifiedString("????####?????");
    }

    public static String createInvalidEmailForbiddenDomainSymbols(char symbol) {
        return createBothifiedString("????##@") + symbol + createBothifiedString("???.???");
    }

    public static String emailAlreadyExists() {
        return "oberyn.martell@dorne.wst";
    }

}