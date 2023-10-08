package View;

import DAO.ClientDao;
import DAO.EmployeDao;
import DTO.Client;
import DTO.Employee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {
    private Client client ;
    private ClientDao clientDao;
    Scanner scanner ;
    public ClientView(Client client , ClientDao clientDao,Scanner scanner)
    {
        this.client = client;
        this.clientDao = clientDao;
        this.scanner = scanner;
    }
    public void menu()  {
        int choice;
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter client                    2 - Supprimer client              .");
        System.out.println(".                                                                         .");
        System.out.println(". 3 - chercher client par code     4 - Afficher la liste d'client    .");
        System.out.println(".                                                                         .");
        System.out.println(". 5 - chercher client  par attributs    6 - Modifié client                .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : addClient();
                break;
            case 2 : deleteClient();
                break;
            case 3 : searchClientByCode();
                break;
            case 4 : showClients();
                break;
            case 5 : searchClientByAttribut();
                break;
            case 6 : updateClient();
                break;
        }
    }
    public void addClient()  {
        System.out.print("Entrer le prenom : ");
        client.setFirstName(scanner.next());
        System.out.print("Entrer le nom :");
        client.setLastName(scanner.next());
        System.out.print("Entrer la date de naissance :");
        client.setDateOfBirth(LocalDate.parse(scanner.next()));
        System.out.print("entrer le nombre de tele : ");
        client.setPhoneNumber(scanner.next());
        System.out.print("enter le adress :");
        client.setAdress(scanner.next());
        System.out.print("enter le code :");
        client.setCode(scanner.next());
        if(clientDao.add(Optional.of(client)).isPresent())
            System.out.println("client "+client.getFirstName() +" est ajouté");
        else
            System.out.println("client "+client.getFirstName() +" n'est pas ajouté");
    }

    public void deleteClient()  {
        System.out.print("Entrer le Matricule : ");
        if(clientDao.delete(scanner.next())>0)
            System.out.println("le client est supprimé");
        else
            System.out.println("le client n'est pas supprimé");
    }
    public void updateClient()  {
        System.out.print("Entrer le Matricule : ");
        client.setCode(scanner.next());
        client = clientDao.Search(client).get(0);
        System.out.print("Entrer le prenom : ");
        client.setFirstName(scanner.next());
        System.out.print("Entrer le nom :");
        client.setLastName(scanner.next());
        System.out.print("Entrer la date de naissance :");
        client.setDateOfBirth(LocalDate.parse(scanner.next()));
        System.out.print("entrer le nombre de tele : ");
        client.setPhoneNumber(scanner.next());
        System.out.print("enter le code :");
        client.setCode(scanner.next());
        System.out.print("enter le adress :");
        client.setAdress(scanner.next());
        if(clientDao.update(client,client.getCode()).isPresent())
            System.out.println("employe "+client.getFirstName() +" est modifié");
        else
            System.out.println("employe "+client.getFirstName() +" n'est pas modifié");
    }
    public void searchClientByAttribut()
    {
        System.out.println("chercher par : 1-nom 2-prenom 3-adress ");
        System.out.println("4-code 5-telephone 6-date de naissance  : ");
        int choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : {
                System.out.print("Entrer le nom : ");
                client.setLastName(scanner.next());
                break;
            }
            case 2 : {
                System.out.print("Entrer le prenom : ");
                client.setFirstName(scanner.next());
                break;
            }
            case 3 : {
                System.out.print("Entrer le adress : ");
                client.setAdress(scanner.next());
                break;
            }
            case 4 : {
                System.out.print("Entrer le code : ");
                client.setCode(scanner.next());
                break;
            }
            case 5 : {
                System.out.print("Entrer le telephone : ");
                client.setPhoneNumber(scanner.next());
                break;
            }
            case 6 : {
                System.out.print("Entrer la date de naissance : ");
                client.setDateOfBirth(LocalDate.parse(scanner.next()));
                break;
            } default: choice = scanner.nextInt();
        }
        List<Client> clients= clientDao.Search(client);
        // for (int i=0 ; i<clients.size();i++)
        //     for(String keys: clients.get(i).keySet()){
        //         System.out.println(keys+ " : "  +clients.get(i).get(keys));
        //     }
    }
    public void showClients()
    {
        List<Client> clients= clientDao.showClients();
        // for (int i=0 ; i<employes.size();i++)
        //     for(String keys: employes.get(i).keySet()){
        //         System.out.println(keys+ " : "  +employes.get(i).get(keys));
        //     }
    }
    public void searchClientByCode()
    {
        System.out.print("Entrer le matricule de l'employe : ");
        Client employe = clientDao.searchByCode(scanner.next()).get();

        // if(employe!=null)
            // for(String keys: employe.keySet()){
            //     System.out.println("- "+ keys+ " : "  +employe.get(keys));
            // }
        // else System.out.println("ce employe n'existe pas");
    }
}
