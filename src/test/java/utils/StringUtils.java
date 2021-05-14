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

    public static String createString(String generatedString) {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify(generatedString);
    }

    public static String createValidEmail() {
        return createString("????##@##gmail.com");
    }

    public static String createInvalidEmailDoubleToad() {
        return createString("????##@@##gmail.com");
    }

    public static String createInvalidEmailEndsWithDot() {
        return createString("?????##@##gmail.com.");
    }

    public static String createInvalidEmailStartsWithDot() {
        return createString(".????##@##gmail.com");
    }

    public static String createInvalidEmailNoSyntax() {
        return createString("????####?????");
    }

    public static String createInvalidEmailForbiddenDomainSymbols(char symbol) {
        return createString("????##@") + symbol + createString("???.???");
    }

    public static String emailAlreadyExists() {
        return "oberyn.martell@dorne.wst";
    }

}