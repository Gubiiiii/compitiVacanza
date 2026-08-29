public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();

        Umano umano = (Umano) umanoFactory.creaAgente(4, 3);
        Zombie zombie = new Zombie(5, 3, 30, 1);

        umano.setProfessione(new Soldato());
        umano.aggiungiOggetto(new Munizioni(10));

        mappa.aggiungiAgente(umano);
        mappa.aggiungiAgente(zombie);

        Simulatore simulatore = new Simulatore(mappa);

        simulatore.aggiungiAgente(umano);
        simulatore.aggiungiAgente(zombie);

        Console console = new Console();
        simulatore.aggiungiObserver(console);

        for (int i = 0; i < 10; i++) {
            simulatore.eseguiTurno();
        }
    }
}