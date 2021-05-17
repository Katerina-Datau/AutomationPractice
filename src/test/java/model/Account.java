package model;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import utils.StringUtils;


@Builder
@Value
public class Account {
    private static Faker faker = new Faker();

    @Default
    Gender gender = Gender.Other;

    @Default
    String firstName = faker.name().firstName();

    @Default
    String lastName = faker.gameOfThrones().house();

    @Default
    String password = faker.internet().password(5, 32);

    @Default
    String birthDay = "";

    @Default
    String birthMonth = "";

    @Default
    String birthYear = "";

    @Default
    boolean subscribe = false;

    @Default
    boolean getOffers = false;

    @Default
    String companyName = "";

    @Default
    String address1 = faker.space().agency();

    @Default
    String address2 = "";

    @Default
    String city = faker.gameOfThrones().city();

    @Default
    String country = "21";

    @Default
    int state = (int) (Math.random() * 53 + 1);

    @Default
    String zip = StringUtils.createString("#####");

    @Default
    String other = "";

    @Default
    String homePhone = "";

    @Default
    String mobilePhone = faker.phoneNumber().cellPhone();

    @Default
    String alias = "";
}
