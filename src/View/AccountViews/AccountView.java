package View.AccountViews;

import DAO.AccountDao;
import DAO.CurrentAccountDao;
import DAO.SavingAccountDao;
import DTO.Account;
import DTO.CurrentAccount;
import DTO.Enum.Status;
import DTO.SavingAccount;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class AccountView {
    private AccountDao accountDao = new AccountDao() {
        
    };
    private Account account = new Account() {
    };
    private Scanner scanner;
    private CurrentAccountView currentAccountService= new CurrentAccountView(new CurrentAccount(),new CurrentAccountDao());
    private SavingAccountView savingAccountService= new SavingAccountView(new SavingAccount(),new SavingAccountDao());
    public AccountView(Scanner scanner) {
        this.scanner = scanner;
    }
    public void menu()  {
        Scanner scanner = new Scanner(System.in);
        int choice ;
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println(". 1 - Administre les compte courant        2 - Adminidtrer les compte epagne                .");
        System.out.println(". 3 - Changer le status du compte          4-Afficher les comptes par statut                .");
        System.out.println(". 5 - Afficher les compte  par date de creation                                             .");
        System.out.println(". 6 - Afficher les compte                  7 - afficher les compte par numero d'operation   .");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.print("entrer votre chois : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1 : currentAccountService.menu();
                break;
            case 2 : savingAccountService.menu();
            break;
            case 3 : this.changeStatus();
            break;
            case 4 : showAccountsByStatus();
            break;
            case 5 : showAccountByCreationDate();
            break;
            case 6 : show();
            break;
            case 7 : showAccountByOperationNumber();
            break;

        }
    }
    public void deleteAccount()
    {
        System.out.println("Entrer le numero du compte : ");
        if(accountDao.delete(scanner.next())>0)
            System.out.println("le compte est supprimé");
        else System.out.println("le compte n'est pas supprimé");
    }
    public void showAccountsByStatus()
    {
        int status ;
        do{
            System.out.println("pour afficher les compte active entrer ( 1 ) ");
            System.out.println("pour afficher les compte inactive entrer ( 2 ) \n : ");
            status = scanner.nextInt();
        }while (status<3 && status>0);
        List<Account> accounts= accountDao.showByStatus(Status.values()[status-1]);
        if(accounts.isEmpty())
            System.out.println("le compte est indisponible");
        else
        {
            // for (int i=0 ; i<accounts.size();i++)
            //     for(String keys: accounts.get(i).keySet()){
            //         System.out.println(keys+ " : "  +accounts.get(i).get(keys));
            //     }
        }
    }
    public void changeStatus()  {
        int status ;
        System.out.println("Entrer le numero du compte");
        account.setAccountNumber(scanner.next());
        do{
            System.out.println("pour afficher les compte active entrer ( 1 ) ");
            System.out.println("pour afficher les compte inactive entrer ( 2 ) \n : ");
            status = scanner.nextInt();
        }while (status<3 && status>0);
        if(accountDao.changeState(account.getAccountNumber(),Status.values()[status-1]))
            System.out.println(("le status est modifié"));
        else System.out.println(("le status n'est pas modifié"));
    }
    public void showAccountByCreationDate()
    {
        System.out.println("Entrer la date de creation du compte");
        List<Account> accounts= accountDao.showByCreationDate(LocalDate.parse(scanner.next()));
        if(accounts.isEmpty())
            System.out.println("Il n'y a pas de compte à cette date");
        else
        {
            // for (int i=0 ; i<accounts.size();i++)
            //     for(String keys: accounts.get(i).keySet()){
            //         System.out.println(keys+ " : "  +accounts.get(i).get(keys));
            //     }
        }
    }
    public void showAccountByOperationNumber()
    {
        System.out.println("Entrer la date de creation du compte");
        Optional<Account> account= accountDao.searchByOperationNumber(scanner.nextInt());
        if(account.isEmpty())
            System.out.println("Il n'y a pas de compte à cette date");
        else
        {
                // for(String keys: account.keySet()){
                //     System.out.println(keys+ " : "  +account.get(keys));
                // }
        }
    }
    public  void show()
    {
        List<Account> accounts= accountDao.show();
        // for (int i=0 ; i<accounts.size();i++)
        //     for(String keys: accounts.get(i).keySet()){
        //         System.out.println(keys+ " : "  +accounts.get(i).get(keys));
        //     }
    }

}
