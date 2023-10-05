import DAO.*;
import DTO.*;
import DTO.Enum.Status;
import Helpers.Database;
import Services.AccountServices.AccountService;
import Services.AccountServices.CurrentAccountService;
import Services.AccountServices.SavingAccountService;
import Services.ClientService;
import Services.EmployeeService;
import Services.MissionService;
import Services.OperationService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService= new EmployeeService(new Employee(),new EmployeDao(),scanner);
        ClientService clientService = new ClientService(new Client(),new ClientDao(),scanner);
        AccountService accountService = new AccountService(new AccountDao(),new Account(),scanner);
        OperationService operationService = new OperationService(new Operation(),new OperationDao(),scanner);
        MissionService missionService = new MissionService(new Mission(), new MissionDao(),new Affectation(),new AffectationDao());
        int choice ;
        do{
        System.out.println("-----------------------------------------------------------------");
        System.out.println(". 1 - Administre les employés    2 - Adminidtrer les clients    .");
        System.out.println(".                                                               .");
        System.out.println(". 3 - Administre les comptes     4 - Adminidtrer les operations .");
        System.out.println(".                                                               .");
        System.out.println(". 5 - Administre les missions                                   .");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("entrer votre chois : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1 : employeeService.menu();
            break;
            case 2 : clientService.menu();
            break;
            case 3 : accountService.menu();
            break;
            case 4 : operationService.menu();
            break;
            case 5 : missionService.menu();
            break;
            default : scanner.close();
        }
    }while(choice<6&& choice>0);
    }
}