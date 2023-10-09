package View;

import DAO.EmployeDao;
import DTO.Employee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeView {
    private Employee employee ;
    private EmployeDao employeDao;
    Scanner scanner = new Scanner(System.in);
    public EmployeeView(Employee employee , EmployeDao employeDao,Scanner scanner)
    {
        this.employee = employee;
        this.employeDao = employeDao;
        this.scanner = scanner;
    }
    public void menu()  {
        int choice;
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter employée                  2 - Supprimer employe            .");
        System.out.println(".                                                                        .");
        System.out.println(". 3 - chercher employe par matricule    4 - Afficher la liste d'employés .");
        System.out.println(".                                                                        .");
        System.out.println(". 5 - chercher employée par attributs    6 - Modifié employé             .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : addEmploye();
            break;
            case 2 : deleteEmploye();
            break;
            case 3 : searchEmployeByMatricule();
            break;
            case 4 : showEmployes();
            break;
            case 5 : searchEmployeByAttribut();
            break;
            case 6 : updateEmploye();
            break;
        }
    }
    public void addEmploye()  {
        System.out.print("Entrer le prenom : ");
        employee.setFirstName(scanner.next());
        System.out.print("Entrer le nom :");
        employee.setLastName(scanner.next());
        System.out.print("Entrer la date de naissance :");
        employee.setDateOfBirth(LocalDate.parse(scanner.next()));
        System.out.print("Entrer la date de recrutment :");
        employee.setRecruitmentDate(LocalDate.parse(scanner.next()));
        System.out.print("entrer le nombre de tele : ");
        employee.setPhoneNumber(scanner.next());
        System.out.print("enter le matricule :");
        employee.setRegistrationNumber(scanner.next());
        if(employeDao.add(Optional.of(employee)).isPresent())
            System.out.println("employe "+employee.getFirstName() +" est ajouté");
        else
            System.out.println("employe "+employee.getFirstName() +" n'est pas ajouté");
    }

    public void deleteEmploye()  {
        System.out.print("Entrer le Matricule : ");
        if(employeDao.delete(scanner.next())>0)
            System.out.println("employe est supprimé");
        else
            System.out.println("employe n'est pas supprimé");
    }
    public void updateEmploye()  {
        System.out.print("Entrer le Matricule : ");
        employee.setRegistrationNumber(scanner.next());
        employee = employeDao.Search(employee).get(0);
        System.out.print("Entrer le prenom : ");
        employee.setFirstName(scanner.next());
        System.out.print("Entrer le nom :");
        employee.setLastName(scanner.next());
        System.out.print("Entrer la date de naissance :");
        employee.setDateOfBirth(LocalDate.parse(scanner.next()));
        System.out.print("Entrer la date de recrutment :");
        employee.setRecruitmentDate(LocalDate.parse(scanner.next()));
        System.out.print("entrer le nombre de tele : ");
        employee.setPhoneNumber(scanner.next());
        System.out.print("enter le matricule :");
        employee.setEmail(scanner.next());
        if(employeDao.update(employee,employee.getRegistrationNumber()).isPresent())
            System.out.println("employe "+employee.getFirstName() +" est modifié");
        else
            System.out.println("employe "+employee.getFirstName() +" n'est pas modifié");
    }
    public void searchEmployeByAttribut()
    {
        System.out.println("chercher par : 1-nom 2-prenom 3-email 4-date de recrutment");
        System.out.println("5-matricule 6-telephone 7-date de naissance");
        int choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : {
                System.out.print("Entrer le nom : ");
                employee.setLastName(scanner.next());
                break;
            }
            case 2 : {
                System.out.print("Entrer le prenom : ");
                employee.setFirstName(scanner.next());
                break;
            }
            case 3 : {
                System.out.print("Entrer le email : ");
                employee.setEmail(scanner.next());
                break;
            }
            case 4 : {
                System.out.print("Entrer le date de recrutment : ");
                employee.setFirstName(scanner.next());
                break;
            }
            case 5 : {
                System.out.print("Entrer le matricule : ");
                employee.setRegistrationNumber(scanner.next());
                break;
            }
            case 6 : {
                System.out.print("Entrer le telephone : ");
                employee.setPhoneNumber(scanner.next());
                break;
            }
            case 7 : {
                System.out.print("Entrer la date de naissance : ");
                employee.setDateOfBirth(LocalDate.parse(scanner.next()));
                break;
            } default: choice = scanner.nextInt();
        }
        List<Employee> employes= employeDao.Search(employee);
        // for (int i=0 ; i<employes.size();i++){
        //     System.out.println("---------------------------------");
        //     for(String keys: employes.get(i).keySet()){
        //         System.out.println(keys+ " : "  +employes.get(i).get(keys));
        //     }
        }
    
    public void showEmployes()
    {
        List<Employee> employes= employeDao.getAll();
        // for (int i=0 ; i<employes.size();i++){
        //     System.out.println("---------------------------------");
        //     for(String keys: employes.get(i).keySet()){
        //         System.out.println(keys+ " : "  +employes.get(i).get(keys));
        //     }
        // }
    }
    public void searchEmployeByMatricule()
    {
        System.out.print("Entrer le matricule de l'employe : ");
        Optional<Employee> employe = employeDao.SearchByRegistrationNumber(scanner.next());

        // if(employe!=null)
            //  for(String keys: employe.keySet()){
            //      System.out.println("- "+ keys+ " : "  +employe.get(keys));
            //  }
        // else System.out.println("ce employe n'existe pas");
    }
}
