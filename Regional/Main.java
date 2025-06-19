package main;

import java.util.*;
import niveau.*;
import vivant.*;
import interface_ui.*;

public class Main {
    public static void main(String[] args) {
        List<Region> regions = new ArrayList<>();
        
        // Création de la région Analamanga
        Region analamanga = new Region("Analamanga");
        
        // District Antananarivo Atsimondrano
        District antsimondrano = new District("Antananarivo Atsimondrano", true);
        Commune ampitatafika = new Commune("Ampitatafika");
        
        // Bureaux de vote pour Ampitatafika
        BureauVote bv101 = new BureauVote("BV101", true);
        bv101.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv101.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv101.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv101.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv101.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv101.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        BureauVote bv102 = new BureauVote("BV102", true);
        bv102.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv102.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv102.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv102.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv102.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv102.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        ampitatafika.addBureau(bv101);
        ampitatafika.addBureau(bv102);
        antsimondrano.addCommune(ampitatafika);
        analamanga.addDistrict(antsimondrano);
        regions.add(analamanga);
        
        
        Region vakinankaratra = new Region("Vakinankaratra");
        
        
        District antsirabe = new District("Antsirabe I", true);
        Commune ambohimena = new Commune("Ambohimena");
        
        
        BureauVote bv201 = new BureauVote("BV201", true);
        bv201.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv201.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv201.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv201.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv201.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv201.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        BureauVote bv202 = new BureauVote("BV202", true);
        bv202.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv202.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv202.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv202.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv202.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv202.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        ambohimena.addBureau(bv201);
        ambohimena.addBureau(bv202);
        antsirabe.addCommune(ambohimena);
        vakinankaratra.addDistrict(antsirabe);
        regions.add(vakinankaratra);
        
        
        Region atsinanana = new Region("Atsinanana");
        
        
        District toamasina = new District("Toamasina I", true);
        Commune tanambao = new Commune("Tanambao Verrerie");
        
        
        BureauVote bv301 = new BureauVote("BV301", true);
        bv301.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv301.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv301.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv301.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv301.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv301.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        BureauVote bv302 = new BureauVote("BV302", true);
        bv302.addDepute(new Depute("C1", "Parti 1", new Personne("Suppléant C1")));
        bv302.addDepute(new Depute("C2", "Parti 2", new Personne("Suppléant C2")));
        bv302.addDepute(new Depute("C3", "Parti 3", new Personne("Suppléant C3")));
        bv302.addDepute(new Depute("C4", "Parti 4", new Personne("Suppléant C4")));
        bv302.addDepute(new Depute("C5", "Parti 5", new Personne("Suppléant C5")));
        bv302.addDepute(new Depute("C6", "Parti 6", new Personne("Suppléant C6")));
        
        tanambao.addBureau(bv301);
        tanambao.addBureau(bv302);
        toamasina.addCommune(tanambao);
        atsinanana.addDistrict(toamasina);
        regions.add(atsinanana);

        new GestionApplication(regions);
    }
}