package View;

import DAO.SimpleOperationDao;
import DTO.Account;
import DTO.CurrentAccount;
import DTO.Employee;
import DTO.Enum.OperationType;
import DTO.SimpleOperation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class SimpleOperationView {
    Scanner scanner ;
    SimpleOperation SimpleOperation;
    SimpleOperationDao SimpleOperationDao;
    public SimpleOperationView(SimpleOperation SimpleOperation , SimpleOperationDao SimpleOperationDao ,Scanner scanner)
    {
        this.SimpleOperation = SimpleOperation;
        this.SimpleOperationDao = SimpleOperationDao;
        this.scanner = scanner;
    }
    public void menu()  {
        int choice;
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter une opperation        2 - Supprimer opperation            .");
        System.out.println(".                                                                       .");
        System.out.println(". 3 - chercher une opperation par numéro                                .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : addSimpleOperation();
                break;
            case 2 : deleteSimpleOperation();
                break;
            case 3 : searchSimpleOperationBynum();
                break;
        }
    }
    private void addSimpleOperation()  {
        int status;
        Employee employee = new Employee();
        CurrentAccount account = new CurrentAccount();

        System.out.println("entrer votre matricule : ");
        employee.setRegistrationNumber(scanner.next());
        SimpleOperation.setEmployee(employee);
        System.out.print("Entrer le numero du compte");
        account.setAccountNumber(scanner.next());
        SimpleOperation.setAccount(account);

        do{
            System.out.println("entrer ( 1 ) pour SimpleOperation du versement ");
            System.out.println("entrer ( 2 ) pour SimpleOperation du retrait\n : ");
            status = scanner.nextInt();
        }while (status>3 || status<0);

        SimpleOperation.setType(OperationType.values()[status-1]);
        System.out.print("Entrer le sold d'SimpleOperation");
        SimpleOperation.setPrice(scanner.nextDouble());
        SimpleOperation.setDate(LocalDate.now());
        if(SimpleOperationDao.add(SimpleOperation)!=null)
           System.out.println("L'opération s'est terminée avec succès");
        else System.out.println("L'opération n'a pas été complétée avec succès");
    }
    private void deleteSimpleOperation()
    {
        System.out.print("Entrer le numero d'SimpleOperation : ");

        if (SimpleOperationDao.delete(scanner.nextInt())>0)
            System.out.println("SimpleOperation a été supprimé");
        else System.out.println("l'SimpleOperation n'a pas ete supprimé");
    }
    private void searchSimpleOperationBynum()
    {
        System.out.print("Entrer le numero d'SimpleOperation : ");
        Optional<SimpleOperation> SimpleOperations= SimpleOperationDao.searchSimpleOperationByNum(scanner.nextInt());;
        if(SimpleOperations.isEmpty())
            System.out.println("Il n'y a pas une SimpleOperation avec cette numero");
        else
        {
                // for(String keys: SimpleOperations.keySet()){
                //     System.out.println(keys+ " : "  +SimpleOperations.get(keys));
                // }
        }
    }
}
