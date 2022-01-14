package model;

import java.util.regex.Pattern;

/**
 * @author Ken Wu
 *
 */
public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    private final String emailRegex = "^(.+)@(.+).com$";
    private Pattern pattern = Pattern.compile(emailRegex);

    public Customer(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("The email does not match the format.");
        }
    }

    @Override
    public String toString() {
        return "First Name: " + this.firstName
                + " Last Name: " + this.lastName
                + " Email: " + this.email;
    }

}
