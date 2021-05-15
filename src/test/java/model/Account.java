package model;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import utils.StringUtils;


@Builder @Value
public class Account {
    Faker faker = new Faker();

    @Default
    private String gender = "ml";

    @Default
    private String firstName = faker.name().firstName();

    @Default
    private String lastName = faker.gameOfThrones().house();

    @Default
    private String password = faker.internet().password(5, 32);

    @Default
    private String birthDay = "";

    @Default
    private String birthMonth = "",

    @Default
    private String birthYear ="";

    @Default
    private boolean subscribe = false;

    @Default
    private boolean getOffers = false;

    @Default
    private String companyName = "";

    @Default
    private String address1 = faker.space().agency();

    @Default
    private String address2 = "";

    @Default
    private String city = faker.gameOfThrones().city();

    @Default
    private String country = "21";

    @Default
    private int state = (int) (Math.random() * 53 + 1);

    @Default
    private String zip = StringUtils.createString("#####");

    @Default
    private String homePhone = "";

    @Default
    private String mobilePhone = faker.phoneNumber().cellPhone();

    @Default
    private String alias = "";
}
