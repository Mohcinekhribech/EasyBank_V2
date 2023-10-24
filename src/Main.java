import DAO.*;
import DTO.*;
import DTO.Enum.Status;
import View.AgenceView;
import View.ClientView;
import View.EmployeAffectationView;
import View.EmployeeView;
import View.MissionView;
import View.SimpleOperationView;
import View.TransfertView;
import View.AccountViews.AccountView;
import View.AccountViews.CurrentAccountView;
import View.AccountViews.SavingAccountView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        AgenceView agenceView = new AgenceView(scanner);
        EmployeeView employeeView= new EmployeeView(new Employee(),new EmployeDao(),scanner);
        ClientView clientView = new ClientView(new Client(),new ClientDao(),scanner);
        AccountView accountView = new AccountView(scanner);
        MissionView missionView = new MissionView();
        TransfertView transfertView = new TransfertView(new Transfert() , new TransfertDao(), scanner);
        EmployeAffectationView employeAffectationView = new EmployeAffectationView(scanner);
        SimpleOperationView simpleOperationView = new SimpleOperationView(new SimpleOperation(), new SimpleOperationDao(), scanner);
        int choice ;
        do{
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(". 1 - Administrer les employés    2 - Adminidtrer les clients                         .");
        System.out.println(".                                                                                     .");
        System.out.println(". 3 - Administrer les comptes     4 - Adminidtrer les operations(retrait , vairsement).");
        System.out.println(".                                                                                     .");
        System.out.println(". 5 - Administrer les missions    6 - Administrer les agences                         .");
        System.out.println(".                                                                                     .");        
        System.out.println(". 7 - Administrer les transferts    8 - Administrer les affectations à les agences    .");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.print("entrer votre chois : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1 : employeeView.menu();
            break;
            case 2 : clientView.menu();
            break;
            case 3 : accountView.menu();
            break;
            case 4 : simpleOperationView.menu();
            break;
            case 5 : missionView.menu();
            break;
            case 6 : agenceView.menu();
            break;
            case 7 : transfertView.menu();
            break;
            case 8 : employeAffectationView.menu();
            break;
            default : scanner.close();
        }
    }while(choice<6&& choice>0);
    }
}