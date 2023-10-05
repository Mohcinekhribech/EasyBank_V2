package Services;

import DAO.AffectationDao;
import DAO.MissionDao;
import DTO.Affectation;
import DTO.Employee;
import DTO.Mission;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MissionService {
    Scanner scanner = new Scanner(System.in);
    Mission mission ;
    MissionDao missionDao;
    Affectation affectation ;
    AffectationDao affectationDao ;
    public MissionService(Mission mission,MissionDao missionDao,Affectation affectation ,AffectationDao affectationDao)
    {
        this.mission = mission;
        this.missionDao = missionDao;
        this.affectation = affectation;
        this.affectationDao=affectationDao;
    }
    public void menu() throws SQLException {
        int choice;
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(". 1 - Ajouter une mission            2 - Supprimer mission               .");
        System.out.println(". 3 - afficher la list des mission   4 - créer une affectation           .");
        System.out.println(". 5 - supprimé une affection         6 - historiques des affictation     .");
        System.out.println(". 7 - statistiques sur l'affectation                                     .");
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Enter votre choix : ");
        choice = scanner.nextInt();
        switch (choice)
        {
            case 1 : addMission();
            break;
            case 2 : deleteMission();
            break;
            case 3 : showMissions();
            break;
            case 4 : addAffectation();
            break;
            case 5 : deleteAffectation();
            break;
            case 6 : historicalAffectation();
            break;
            case 7 : statisticsAffectation();
            break; 
        }
    }
    private  void historicalAffectation() throws SQLException
    {
        System.out.print("Eanter le matricule du employe : ");
        List<Map<String,String>> affectations = affectationDao.historicalAffectation(scanner.next());
        if(affectations.isEmpty())
            System.out.println("Il n'y a pas des mission");
        else
        {
            for (int i=0 ; i<affectations.size();i++){
                for(String keys: affectations.get(i).keySet()){
                    System.out.println(keys+ " : "  +affectations.get(i).get(keys));
                }
            }
        }
    }
    private void statisticsAffectation() throws SQLException
    {
        Map<String,Integer> affectations = affectationDao.statisticsAffectation();
        if(affectations.isEmpty())
            System.out.println("Il n'y a pas des mission");
        else
        {
                for(String keys: affectations.keySet()){
                    System.out.println(keys+ " : "  +affectations.get(keys));
                }
        }
    }
    private void addAffectation() throws SQLException {
        Employee employee = new Employee();
        System.out.print("Entrer le code du mission :");
        mission.setCode(scanner.next());
        affectation.setMission(mission);
        System.out.print("Entrer le matricule du employee : ");
        employee.setRegistrationNumber(scanner.next());
        affectation.setEmployee(employee);
        System.out.print("Entrer la date de debut du la mission : ");
        affectation.setStartDate(LocalDate.parse(scanner.next()));
        System.out.print("Entrer la date de fin du la mission : ");
        affectation.setEndDate(LocalDate.parse(scanner.next()));
        if(affectationDao.add(affectation).isPresent())
            System.out.println("la mission est affectué");
        else System.out.println("la mission n'a pas affectué");

    }
    private void deleteAffectation() throws SQLException {
        System.out.print("Entrer id  du affectation :");
        if(affectationDao.delete(scanner.nextInt())>0)
            System.out.println("affectation est supprimé");
        else System.out.println("affectation n'a pas supprimé");
    }
    private void addMission() throws SQLException {
        System.out.println("Entrer le code du la mission : ");
        mission.setCode(scanner.next());
        System.out.println("Entrer un nom du mission : ");
        mission.setName(scanner.next());
        System.out.println("Entrer une description pour la mission \n : ");
        mission.setDescription(scanner.next());

        if(missionDao.add(mission).isPresent())
            System.out.println("la mission a été ajouté");
        else System.out.println("la mission n'a pas été ajouter");
    }
    private void deleteMission()
    {
        System.out.print("Entrer le code du mission :");
        if(missionDao.delete(scanner.next())>0)
            System.out.println("la mission a été supprimé");
    }
    private void  showMissions()
    {
        List<Map<String,String>> missions= missionDao.getAll();
        if(missions.isEmpty())
            System.out.println("Il n'y a pas des mission");
        else
        {
            for (int i=0 ; i<missions.size();i++){
                for(String keys: missions.get(i).keySet()){
                    System.out.println(keys+ " : "  +missions.get(i).get(keys));
                }
            }
        }
    }

}