package View;

import java.util.Optional;
import java.util.Scanner;

import DAO.AgenceDao;
import DTO.Agence;
import Services.AgenceService;

public class AgenceView {
    Scanner scanner;
    Agence agence = new Agence();
    AgenceService agenceService = new AgenceService(new AgenceDao());

    public AgenceView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        int choice;
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter une agence             2 - Supprimer une agence                 .");
        System.out.println(".                                                                             .");
        System.out.println(". 3 - chercher une agence par code   4 - chercher une agence par adress       .");
        System.out.println(".                                                                             .");
        System.out.println(". 5 - modifié une agence             4 - chercher une agence par adress       .");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addAgence();
                break;
            case 2:
                deleteAgence();
                break;
            case 3:
                searchAgenceBycode();
                break;
            case 4:
                searchAgenceByAdress();
                break;
            case 5:
                updateAgence();
                break;
        }
    }

    private void afficheAgence(Agence agence) {
        System.out.println("------------------------------------");
        System.out.println("- code  : " + agence.getCode());
        System.out.println("- nom  : " + agence.getName());
        System.out.println("- adress :" + agence.getAdress());
        System.out.println("- numero du telephone : " + agence.getPhoneNumber());
        System.out.println("------------------------------------");
    }

    private void addAgence() {
        System.out.print("entrer le code : ");
        agence.setCode(scanner.next());
        System.out.print("Enter l'adresse du l'agence : ");
        agence.setAdress(scanner.next());
        System.out.print("Enter le nom du l'agence : ");
        agence.setName(scanner.next());
        System.out.print("entrer le numero du tele du l'agence : ");
        agence.setPhoneNumber(scanner.next());
        if (this.agenceService.add(agence).isPresent()) {
            System.out.println("l'agence " + agence.getName() + " à été crée ! ");
        } else
            System.out.println("l'agence " + agence.getName() + " à été pas crée ! ");
    }

    private void deleteAgence() {
        System.out.print("Entrer le code du l'agence");
        if (this.agenceService.delete(scanner.next()) > 0) {
            System.out.println("l'agence à été supprimer ! ");
        } else
            System.out.println("l'agence n'existe pas !");
    }

    private void searchAgenceBycode() {
        System.out.print("Enter le code du l'agence : ");
        String s = scanner.next();
        Optional<Agence> agence = this.agenceService.searchByCode(s);
        if (agence.isPresent()) {
            this.afficheAgence(agence.get());
        } else
            System.out.println("cette agence n'existe pa !");
    }

    private void searchAgenceByAdress() {
        System.out.print("Enter le adress du l'agence : ");
        String s = scanner.next();
        Optional<Agence> agence = this.agenceService.searchByAdress(s);
        if (agence.isPresent()) {
            this.afficheAgence(agence.get());
        } else
            System.out.println("cette agence n'existe pa !");
    }

    private void updateAgence() {
        System.out.print("Enter le code du l'agence à modifié : ");
        Optional<Agence> agence = this.agenceService.searchByCode(scanner.next());
        if (agence.isPresent()) {
            System.out.println("les information actuelle du agence " + agence.get().getName() + " :");
            this.afficheAgence(agence.get());
            System.out.println("Entrer les nouvelles informations du l'agence ");
            System.out.print("Enter l'adresse du l'agence : ");
            agence.get().setAdress(scanner.next());
            System.out.print("Enter le nom du l'agence : ");
            agence.get().setName(scanner.next());
            System.out.print("entrer le numero du tele du l'agence : ");
            agence.get().setPhoneNumber(scanner.next());
            if (this.agenceService.update(agence.get(), agence.get().getCode()).isPresent()) {
                System.out.println("l'agence " + agence.get().getName() + " à été modifié ! ");
            } else
                System.out.println("l'agence " + agence.get().getName() + " à été pas modifié ! ");
        } else
            System.out.println("cette agence n'existe pas !");
    }
}
