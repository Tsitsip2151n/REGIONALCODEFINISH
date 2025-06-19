package vivant;

public class Depute extends Personne {
     String groupe;
     int nbVotes;
     Personne suppleant;

    public Depute(String nom, String groupe, Personne suppleant) {
        super(nom);
        if (groupe == null || groupe.trim().isEmpty()) {
            throw new IllegalArgumentException("Le groupe ne peut pas être vide");
        }
        if (suppleant == null) {
            throw new IllegalArgumentException("Le suppléant ne peut pas être null");
        }
        this.groupe = groupe;
        this.nbVotes = 0;
        this.suppleant = suppleant;
    }

    public String getGroupe() {
        return groupe;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public Personne getSuppleant() {
        return suppleant;
    }

    public void ajouterVotes(int nb) {
        if (nb < 0) {
            throw new IllegalArgumentException("Le nombre de votes ne peut pas être négatif");
        }
        this.nbVotes += nb;
    }

    public boolean estEluAvecSuppleant(Depute autre) {
        return this.nbVotes >= (2 * autre.getNbVotes());
    }

    @Override
    
    public String toString() {
        return String.format("%s (%s) - %d votes", getNom(), groupe, nbVotes);
    }
} 
    

