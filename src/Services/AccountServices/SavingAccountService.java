package Services.AccountServices;

import DAO.SavingAccountDao;
import DTO.Enum.Status;
import DTO.SavingAccount;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class SavingAccountService {
    Scanner scanner = new Scanner(System.in);
    SavingAccountDao savingAccountDao ;
    SavingAccount savingAccount ;
    public SavingAccountService(SavingAccount savingAccount,SavingAccountDao savingAccountDao)
    {
        this.savingAccountDao = savingAccountDao;
        this.savingAccount = savingAccount;
    }
    public void menu() throws SQLException {
        int choice ;
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(". 1 - Créer un compte           2 - chercher un compte par client        .");
        System.out.println(".                                                                        .");
        System.out.println(". 9 - modifié le compte                                                  .");
        System.out.println(".                                                                        .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("entrer votre chois : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1: addAccount();
                break;
            case 2: searchByClient();
                break;
            case 3: updateAccount();
                break;
        }
    }
    public void addAccount() throws SQLException {
        System.out.print("Entrer le nombre du compte : ");
        savingAccount.setAccountNumber(scanner.next());
        System.out.print("Entrer le solde :");
        savingAccount.setBalance(scanner.nextDouble());
        savingAccount.setCreationDate(LocalDate.parse(scanner.next()));
        System.out.print("entrer le code de client : ");
        savingAccount.getClient().setCode(scanner.next());
        savingAccount.setStatus(Status.active);
        System.out.print("enter le max sold :");
        savingAccount.setInterestRate(scanner.nextDouble());

        if(savingAccountDao.add(Optional.of(savingAccount)).isPresent())
            System.out.println("le compte "+savingAccount.getAccountNumber()   +" est crée");
        else
            System.out.println("le compte "+savingAccount.getAccountNumber()   +" n'est pas crée");
    }
    public void updateAccount() throws SQLException {
        System.out.print("Entrer le nombre du compte : ");
        savingAccount.setAccountNumber(scanner.next());
        System.out.print("Entrer le solde :");
        savingAccount.setBalance(scanner.nextDouble());
        savingAccount.setCreationDate(LocalDate.parse(scanner.next()));
        System.out.print("entrer le code de client : ");
        savingAccount.getClient().setCode(scanner.next());
        savingAccount.setStatus(Status.active);
        System.out.print("enter le max sold :");
        savingAccount.setInterestRate(scanner.nextDouble());

        if(savingAccountDao.add(Optional.of(savingAccount)).isPresent())
            System.out.println("le compte "+savingAccount.getAccountNumber()   +" est crée");
        else
            System.out.println("le compte "+savingAccount.getAccountNumber()   +" n'est pas crée");
    }

    public void searchByClient()
    {
        System.out.println("Entrer le code de  client : ");
        List<Map<String,String>> accounts= savingAccountDao.searchByClient(scanner.next());

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
}
