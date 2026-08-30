public class ScenarioFactory {

    private ScenarioFactory() {
    }

    public static Simulatore creaScenarioBase(boolean usaConsole) {

        Mappa mappa = new Mappa(12, 10);
        Simulatore simulatore = new Simulatore(mappa, 1000);

        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();
        AgenteFactory runnerFactory = new RunnerFactory();
        AgenteFactory tankFactory = new TankFactory();

        Umano soldato = (Umano) umanoFactory.creaAgente(4, 3);
        soldato.setProfessione(new Soldato());

        Umano medico = (Umano) umanoFactory.creaAgente(2, 2);
        medico.setProfessione(new Medico());

        Umano civile = (Umano) umanoFactory.creaAgente(1, 7);
        civile.setProfessione(new Civile());

        Zombie zombie = (Zombie) zombieFactory.creaAgente(9, 3);
        Runner runner = (Runner) runnerFactory.creaAgente(3, 8);
        Tank tank = (Tank) tankFactory.creaAgente(10, 7);

        mappa.aggiungiRisorsa(new RisorsaMunizioni(4, 3, 10));
        mappa.aggiungiRisorsa(new RisorsaMunizioni(6, 5, 6));
        mappa.aggiungiRisorsa(new RisorsaMedikit(2, 2, 30));
        mappa.aggiungiRisorsa(new RisorsaMedikit(5, 1, 20));
        mappa.aggiungiBarricata(new Barricata(7, 4, 100));
        mappa.aggiungiBarricata(new Barricata(7, 5, 100));
        mappa.aggiungiBarricata(new Barricata(7, 6, 100));
        mappa.aggiungiZonaContaminata(new ZonaContaminata(1, 6));
        mappa.aggiungiZonaContaminata(new ZonaContaminata(8, 2));

        simulatore.aggiungiAgente(soldato);
        simulatore.aggiungiAgente(medico);
        simulatore.aggiungiAgente(civile);
        simulatore.aggiungiAgente(zombie);
        simulatore.aggiungiAgente(runner);
        simulatore.aggiungiAgente(tank);

        if (usaConsole) {
            simulatore.aggiungiObserver(new Console());
        }

        return simulatore;
    }
}
