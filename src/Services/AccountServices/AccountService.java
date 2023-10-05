package Services.AccountServices;

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
import java.util.Scanner;

public class AccountService {
    private AccountDao accountDao;
    private Account account;
    private Scanner scanner;
    public AccountService(AccountDao accountDao, Account account,Scanner scanner)
    {
        this.currentAccountService = currentAccountService;
        this.savingAccountService = savingAccountService;
        this.accountDao = accountDao;
        this.account = account;
        this.scanner = scanner;
    }
    private CurrentAccountService currentAccountService= new CurrentAccountService(new CurrentAccount(),new CurrentAccountDao());
    private SavingAccountService savingAccountService= new SavingAccountService(new SavingAccount(),new SavingAccountDao());
    public void menu() throws SQLException {
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
        List<Map<String,String>> accounts= accountDao.showByStatus(Status.values()[status-1]);
        if(accounts.isEmpty())
            System.out.println("le compte est indisponible");
        else
        {
            for (int i=0 ; i<accounts.size();i++)
                for(String keys: accounts.get(i).keySet()){
                    System.out.println(keys+ " : "  +accounts.get(i).get(keys));
                }
        }
    }
    public void changeStatus() throws SQLException {
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
        List<Map<String,String>> accounts= accountDao.showByCreationDate(LocalDate.parse(scanner.next()));
        if(accounts.isEmpty())
            System.out.println("Il n'y a pas de compte à cette date");
        else
        {
            for (int i=0 ; i<accounts.size();i++)
                for(String keys: accounts.get(i).keySet()){
                    System.out.println(keys+ " : "  +accounts.get(i).get(keys));
                }
        }
    }
    public void showAccountByOperationNumber()
    {
        System.out.println("Entrer la date de creation du compte");
        Map<String,String> account= accountDao.searchByOperationNumber(scanner.nextInt());
        if(account.isEmpty())
            System.out.println("Il n'y a pas de compte à cette date");
        else
        {
                for(String keys: account.keySet()){
                    System.out.println(keys+ " : "  +account.get(keys));
                }
        }
    }
    public  void show()
    {
        List<Map<String,String>> accounts= accountDao.show();
        for (int i=0 ; i<accounts.size();i++)
            for(String keys: accounts.get(i).keySet()){
                System.out.println(keys+ " : "  +accounts.get(i).get(keys));
            }
    }

}
