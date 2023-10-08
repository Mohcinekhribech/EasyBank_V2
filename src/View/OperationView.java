package View;

import DAO.OperationDao;
import DTO.Account;
import DTO.CurrentAccount;
import DTO.Employee;
import DTO.Enum.OperationType;
import DTO.Operation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class OperationView {
    Scanner scanner ;
    Operation operation;
    OperationDao operationDao;
    public OperationView(Operation operation , OperationDao operationDao ,Scanner scanner)
    {
        this.operation = operation;
        this.operationDao = operationDao;
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
            case 1 : addOperation();
                break;
            case 2 : deleteOperation();
                break;
            case 3 : searchOperationBynum();
                break;
        }
    }
    private void addOperation()  {
        int status;
        Employee employee = new Employee();
        CurrentAccount account = new CurrentAccount();

        System.out.println("entrer votre matricule : ");
        employee.setRegistrationNumber(scanner.next());
        operation.setEmployee(employee);
        System.out.print("Entrer le numero du compte");
        account.setAccountNumber(scanner.next());
        operation.setAccount(account);

        do{
            System.out.println("entrer ( 1 ) pour operation du versement ");
            System.out.println("entrer ( 2 ) pour operation du retrait\n : ");
            status = scanner.nextInt();
        }while (status>3 || status<0);

        operation.setType(OperationType.values()[status-1]);
        System.out.print("Entrer le sold d'operation");
        operation.setPrice(scanner.nextDouble());
        operation.setDate(LocalDate.now());
        if(operationDao.add(operation)!=null)
           System.out.println("L'opération s'est terminée avec succès");
        else System.out.println("L'opération n'a pas été complétée avec succès");
    }
    private void deleteOperation()
    {
        System.out.print("Entrer le numero d'operation : ");

        if (operationDao.delete(scanner.nextInt())>0)
            System.out.println("operation a été supprimé");
        else System.out.println("l'operation n'a pas ete supprimé");
    }
    private void searchOperationBynum()
    {
        System.out.print("Entrer le numero d'operation : ");
        Optional<Operation> operations= operationDao.searchOperationByNum(scanner.nextInt());;
        if(operations.isEmpty())
            System.out.println("Il n'y a pas une operation avec cette numero");
        else
        {
                // for(String keys: operations.keySet()){
                //     System.out.println(keys+ " : "  +operations.get(keys));
                // }
        }
    }
}
