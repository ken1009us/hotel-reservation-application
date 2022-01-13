package model;

import java.util.regex.Pattern;

public class Customer {

    public String firstName;
    public String lastName;
    public String email;
    public String emailRegex = "^(.+)@(.+).com$";
    Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("The email does not match the format.");
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + email;
    }

}
