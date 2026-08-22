public class Main {

    public static void main(String[] args) {

       Mappa mappa = new Mappa(10, 10);

        Umano umano = new Umano(2, 3);
        Zombie zombie = new Zombie(8, 3);

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