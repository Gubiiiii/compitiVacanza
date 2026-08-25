public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        Umano umano = new Umano(4, 3);
        umano.setProfessione(new Soldato());

        // Test inventario
        mappa.aggiungiRisorsa(
            new RisorsaMedikit(4, 3, 30)
        );

        mappa.aggiungiRisorsa(
            new RisorsaMunizioni(6, 3, 10)
        );

        Zombie zombie = new Zombie(5, 3);

        mappa.aggiungiAgente(umano);
        mappa.aggiungiAgente(zombie);

        Simulatore simulatore = new Simulatore(mappa);

        simulatore.aggiungiAgente(umano);
        simulatore.aggiungiAgente(zombie);

        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
    }
}