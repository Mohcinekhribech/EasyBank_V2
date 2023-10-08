import DAO.*;
import DTO.*;
import DTO.Enum.Status;
import Helpers.Database;
import View.ClientView;
import View.EmployeeView;
import View.MissionView;
import View.OperationView;
import View.AccountViews.AccountView;
import View.AccountViews.CurrentAccountView;
import View.AccountViews.SavingAccountView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        EmployeeView employeeService= new EmployeeView(new Employee(),new EmployeDao(),scanner);
        ClientView clientService = new ClientView(new Client(),new ClientDao(),scanner);
        AccountView accountService = new AccountView(scanner);
        OperationView operationService = new OperationView(new Operation(),new OperationDao(),scanner);
        MissionView missionService = new MissionView();
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