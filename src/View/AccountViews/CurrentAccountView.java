package View.AccountViews;

import DAO.CurrentAccountDao;
import DAO.SavingAccountDao;
import DTO.Client;
import DTO.CurrentAccount;
import DTO.Enum.Status;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class CurrentAccountView {
    Scanner scanner = new Scanner(System.in);
    CurrentAccount currentAccount = new CurrentAccount();
    CurrentAccountDao currentAccountDao = new CurrentAccountDao();
    CurrentAccountView(CurrentAccount currentAccount,CurrentAccountDao currentAccountDao)
    {
        this.currentAccount = currentAccount;
        this.currentAccountDao = currentAccountDao;
    }
    public void menu()  {
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
    public void addAccount()  {
        Client client=new Client(); 
        System.out.print("Entrer le nombre du compte : ");
        currentAccount.setAccountNumber(scanner.next());
        System.out.print("Entrer le solde :");
        currentAccount.setBalance(scanner.nextDouble());
        currentAccount.setCreationDate(LocalDate.now());
        System.out.print("entrer le code de client : ");
        client.setCode(scanner.next());
        currentAccount.setClient(client);
        currentAccount.setStatus(Status.active);
        System.out.print("enter le max sold :");
        currentAccount.setMaxPrice(scanner.nextDouble());

        if(currentAccountDao.add(Optional.of(currentAccount)).isPresent())
            System.out.println("le compte "+currentAccount.getAccountNumber()   +" est crée");
        else
            System.out.println("le compte "+currentAccount.getAccountNumber()   +" n'est pas crée");
    }
    public void updateAccount()  {
        System.out.print("Entrer le nombre du compte : ");
        currentAccount.setAccountNumber(scanner.next());
        System.out.print("Entrer le solde :");
        currentAccount.setBalance(scanner.nextDouble());
        currentAccount.setCreationDate(LocalDate.parse(scanner.next()));
        System.out.print("entrer le code de client : ");
        currentAccount.getClient().setCode(scanner.next());
        currentAccount.setStatus(Status.active);
        System.out.print("enter le max sold :");
        currentAccount.setMaxPrice(scanner.nextDouble());

        if(currentAccountDao.add(Optional.of(currentAccount)).isPresent())
            System.out.println("le compte "+currentAccount.getAccountNumber()   +" est crée");
        else
            System.out.println("le compte "+currentAccount.getAccountNumber()   +" n'est pas crée");
    }

    public void searchByClient()
    {
        System.out.println("Entrer le code de  client : ");
        List<CurrentAccount> accounts= currentAccountDao.searchByClient(scanner.next());

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
}
