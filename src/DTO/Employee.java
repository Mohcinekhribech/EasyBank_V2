package DTO;

import java.time.LocalDate;
import java.util.List;

public class Employee extends Person {
    private String registrationNumber ;
    private LocalDate recruitmentDate;
    private List<Operation> operations;
    private List<Affectation> affectations;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate  getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(LocalDate  recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }
}
