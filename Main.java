public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        // Factory
        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();
        AgenteFactory runnerFactory = new RunnerFactory();
        AgenteFactory tankFactory = new TankFactory();

        // Creazione degli agenti tramite Factory
        Umano umano = (Umano) umanoFactory.creaAgente(4, 3);
        Zombie zombie = (Zombie) zombieFactory.creaAgente(8, 3);
        Runner runner = (Runner) runnerFactory.creaAgente(2, 6);
        Tank tank = (Tank) tankFactory.creaAgente(8, 6);

        // Aggiunta degli agenti alla mappa
        mappa.aggiungiAgente(umano);
        mappa.aggiungiAgente(zombie);
        mappa.aggiungiAgente(runner);
        mappa.aggiungiAgente(tank);

        // Creazione del simulatore
        Simulatore simulatore = new Simulatore(mappa);

        // Aggiunta degli agenti al simulatore
        simulatore.aggiungiAgente(umano);
        simulatore.aggiungiAgente(zombie);
        simulatore.aggiungiAgente(runner);
        simulatore.aggiungiAgente(tank);

        // Observer
        Console console = new Console();
        simulatore.aggiungiObserver(console);

        // Eseguiamo 3 turni
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
    }
}