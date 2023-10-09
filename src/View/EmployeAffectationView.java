package View;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import DAO.AgenceDao;
import DAO.EmployeAffectationDao;
import DAO.EmployeDao;
import DTO.Agence;
import DTO.EmployeAffectation;
import DTO.Employee;
import Interfaces.AgenceInterface;
import Services.AgenceService;
import Services.EmployeAffectationService;
import Services.EmployeService;

public class EmployeAffectationView {
    Scanner scanner;
    AgenceService agenceService = new AgenceService(new AgenceDao());
    EmployeService employeService = new EmployeService(new EmployeDao());
    EmployeAffectation employeAffectation = new EmployeAffectation();
    Agence agence = new Agence();
    Employee employee = new Employee();
    EmployeAffectationService employeAffectationService = new EmployeAffectationService(new EmployeAffectationDao());

    public EmployeAffectationView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        int choice;
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println(". 1 - Affecté un employé à une agence    2 - muter un employé à une autre agenc   .");
        System.out.println(".                                                                                 .");
        System.out.println(". 3 - affiche l'historiques des affectation                                       .");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addEmployeAffectation();
                break;
            case 2:
                transferAnEmployee();
                break;
            case 3:
                assignmentHistory();
                break;
        }
    }

    private void afficheAffectaion(EmployeAffectation employeAffectation) {
        System.out.println("------------------------------------");
        System.out.println("-  code du l'agence  : " + employeAffectation.getAgence().getCode());
        System.out.println("- nom du l'agence   : " + employeAffectation.getAgence().getName());
        System.out.println("- adress du l'agence  :" + employeAffectation.getAgence().getAdress());
        System.out.println("- numero du telephone de l'agence: " + employeAffectation.getAgence().getPhoneNumber());
        System.out.println("   ");
        System.out.println("-  matricule du l'employé  : " + employeAffectation.getEmployee().getRegistrationNumber());
        System.out.println("- le nom complet de l'employé  : " + employeAffectation.getEmployee().getFirstName() + " "
                + employeAffectation.getEmployee().getLastName());
        System.out.println("- email de l'employé  :" + employeAffectation.getEmployee().getEmail());
        System.out
                .println("- la date de naissance du l'employé : " + employeAffectation.getEmployee().getDateOfBirth());
        System.out.println("------------------------------------");
    }

    private void assignmentHistory() {
        List<EmployeAffectation> employeAffectations = employeAffectationService.assignmentHistory();
        for (int i = 0; i < employeAffectations.size(); i++)
            afficheAffectaion(employeAffectations.get(i));
    }

    private void transferAnEmployee() {
        String code;
        boolean exist = false;
        do {
            exist = false;
            code = "";
            System.out.print("Enter le matricule du l'employee : ");
            code = scanner.next();
            exist = employeService.searchByRegistrationNumber(code).isPresent();
            if (exist)
                employee.setRegistrationNumber(scanner.next());
            else
                System.out.println("ce employe n'existe pas !");
        } while (exist);

        do {
            System.out.print("entrer le code du l'agence : ");
            code = scanner.next();
            exist = agenceService.searchByCode(code).isPresent();
            if (exist)
                agence.setCode(code);
            else
                System.out.println("cette agence n'existe pas !");
        } while (exist);

        employeAffectation.setCreationDate(LocalDate.now());
        employeAffectation.setAgence(agence);
        employeAffectation.setEmployee(employee);
        if (this.employeAffectationService.transferAnEmployee(employeAffectation).isPresent()) {
            System.out.println("l'employé evec le matricule " + employee.getRegistrationNumber()
                    + " est muter à l'agence " + agence.getCode() + " !");
        } else
            System.out.println("l'employé evec le matricule " + employee.getRegistrationNumber()
                    + " n'a pas muter à l'agence " + agence.getCode() + " !");
    }

    private void addEmployeAffectation() {
        String code;
        boolean exist = false;

        do {
            System.out.print("entrer le code du l'agence : ");
            code = scanner.next();
            exist = agenceService.searchByCode(code).isPresent();
            if (exist)
                agence.setCode(code);
            else
                System.out.println("cette agence n'existe pas !");
        } while (exist);

        do {
            exist = false;
            code = "";
            System.out.print("Enter le matricule du l'employee : ");
            code = scanner.next();
            exist = employeService.searchByRegistrationNumber(code).isPresent();
            if (exist)
                employee.setRegistrationNumber(scanner.next());
            else
                System.out.println("ce employe n'existe pas !");
        } while (exist);

        employeAffectation.setCreationDate(LocalDate.now());
        employeAffectation.setAgence(agence);
        employeAffectation.setEmployee(employee);
        if (this.employeAffectationService.AssignAnEmployeeToAnAgency(employeAffectation).isPresent()) {
            System.out.println("l'employé evec le matricule " + employee.getRegistrationNumber()
                    + " est affecté à l'agence " + agence.getCode() + " !");
        } else
            System.out.println("l'employé evec le matricule " + employee.getRegistrationNumber()
                    + " n'a pas affecté à l'agence " + agence.getCode() + " !");
    }

}