package DTO;

import java.time.LocalDate;

import lombok.Data;

public abstract @Data class Person {
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected String phoneNumber;
    protected String email;
}
