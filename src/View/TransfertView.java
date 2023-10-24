package View;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import DAO.TransfertDao;
import DTO.CurrentAccount;
import DTO.Employee;
import DTO.Transfert;

public class TransfertView {
    Scanner scanner ;
    Transfert transfert;
    TransfertDao transfertDao;
    public TransfertView(Transfert transfert,TransfertDao transfertDao,Scanner scanner)
    {
        this.transfert = transfert;
        this.transfertDao = transfertDao;
        this.scanner = scanner;
    }
     private void afficheTransfert(Transfert transfert) {
        System.out.println("------------------------------------");
        System.out.println("- price  : " + transfert.getPrice());
        System.out.println("- date  : " + transfert.getDate());
        System.out.println("- vesement de :" + transfert.getFromAccount().getAccountNumber());
        System.out.println("- versement a : " + transfert.getToAccount().getAccountNumber());
        System.out.println("------------------------------------");
    }
    public void menu()  {
        int choice;
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter une transfert        2 - Supprimer une transfert            .");
        System.out.println(".                                                                         .");
        System.out.println(". 3 - chercher une transfer par numéro                                    .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : addTransfert();
                break;
            case 2 : deleteTransfert();
                break;
            case 3 : searchTransfertBynum();
                break;
        }
    }
     private void addTransfert()  {
        int status;
        Employee employee = new Employee();
        CurrentAccount account = new CurrentAccount();

        System.out.println("entrer votre matricule : ");
        employee.setRegistrationNumber(scanner.next());
        transfert.setEmployee(employee);
        System.out.print("versement de (numero du compte)");
        account.setAccountNumber(scanner.next());
        transfert.setFromAccount(account);
        System.out.print("versement a (numero du compte)");
        account.setAccountNumber(scanner.next());
        transfert.setFromAccount(account);
        System.out.print("Entrer le sold d'Transfert");
        transfert.setPrice(scanner.nextDouble());
        transfert.setDate(LocalDate.now());
        if(transfertDao.add(transfert)!=null)
           System.out.println("L'opération s'est terminée avec succès");
        else System.out.println("L'opération n'a pas été complétée avec succès");
    }
    private void deleteTransfert()
    {
        System.out.print("Entrer le numero d'Transfert : ");

        if (transfertDao.delete(scanner.nextInt())>0)
            System.out.println("Transfert a été supprimé");
        else System.out.println("l'Transfert n'a pas ete supprimé");
    }
    private void searchTransfertBynum()
    {
        System.out.print("Entrer le numero d'Transfert : ");
        Optional<Transfert> Transferts= transfertDao.searchTransfertByNum(scanner.nextInt());;
        if(Transferts.isEmpty())
            System.out.println("Il n'y a pas une Transfert avec cette numero");
        else
        {
              afficheTransfert(Transferts.get());
        }
    }
}
