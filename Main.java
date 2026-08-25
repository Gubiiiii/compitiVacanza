public class Main {

    public static void main(String[] args) {

        // Creiamo la mappa
        Mappa mappa = new Mappa(10, 10);

        // Creiamo un umano
        Umano umano = new Umano(4, 3);

        // Gli assegniamo la professione Soldato
        umano.setProfessione(new Soldato());

        // Creiamo uno zombie vicino all'umano
        Zombie zombie = new Zombie(5, 3);

        // Mettiamo Medikit e Munizioni nella stessa cella dell'umano
        mappa.aggiungiRisorsa(
            new RisorsaMedikit(4, 3, 30)
        );

        mappa.aggiungiRisorsa(
            new RisorsaMunizioni(4, 3, 2)
        );

        // Aggiungiamo gli agenti alla mappa
        mappa.aggiungiAgente(umano);
        mappa.aggiungiAgente(zombie);

        // Creiamo il simulatore
        Simulatore simulatore = new Simulatore(mappa);

        // Aggiungiamo gli agenti al simulatore
        simulatore.aggiungiAgente(umano);
        simulatore.aggiungiAgente(zombie);

        // Eseguiamo 3 turni
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
    }
}