public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();
        AgenteFactory runnerFactory = new RunnerFactory();
        AgenteFactory tankFactory = new TankFactory();

        Umano umano = (Umano) umanoFactory.creaAgente(4, 3);
        Zombie zombie = (Zombie) zombieFactory.creaAgente(8, 3);
        Runner runner = (Runner) runnerFactory.creaAgente(2, 6);
        Tank tank = (Tank) tankFactory.creaAgente(8, 6);

   
        umano.subisciDanno(80);

        mappa.aggiungiAgente(umano);
        mappa.aggiungiAgente(zombie);
        mappa.aggiungiAgente(runner);
        mappa.aggiungiAgente(tank);

        Simulatore simulatore = new Simulatore(mappa);

        simulatore.aggiungiAgente(umano);
        simulatore.aggiungiAgente(zombie);
        simulatore.aggiungiAgente(runner);
        simulatore.aggiungiAgente(tank);


        Console console = new Console();
        simulatore.aggiungiObserver(console);

        for (int i = 0; i < 6; i++) {
            simulatore.eseguiTurno();
        }
    }
}