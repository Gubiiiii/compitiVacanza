import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ScenarioFactory {

    private ScenarioFactory() {
    }

    public static Simulatore creaScenarioBase(boolean usaConsole) {

        Mappa mappa = new Mappa(20, 20);
        Simulatore simulatore = new Simulatore(mappa, 1000);

        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();
        AgenteFactory runnerFactory = new RunnerFactory();
        AgenteFactory tankFactory = new TankFactory();

        Random random = new Random();
        Set<String> posizioniOccupate = new HashSet<>();

        int[] posizioneSoldato = posizioneCasuale(random, posizioniOccupate);
        int[] posizioneMedico = posizioneCasuale(random, posizioniOccupate);
        int[] posizioneCivile = posizioneCasuale(random, posizioniOccupate);
        int[] posizioneZombie = posizioneCasuale(random, posizioniOccupate);
        int[] posizioneRunner = posizioneCasuale(random, posizioniOccupate);
        int[] posizioneTank = posizioneCasuale(random, posizioniOccupate);

        Umano soldato = (Umano) umanoFactory.creaAgente(
            posizioneSoldato[0],
            posizioneSoldato[1]
        );
        soldato.setProfessione(new Soldato());

        Umano medico = (Umano) umanoFactory.creaAgente(
            posizioneMedico[0],
            posizioneMedico[1]
        );
        medico.setProfessione(new Medico());

        Umano civile = (Umano) umanoFactory.creaAgente(
            posizioneCivile[0],
            posizioneCivile[1]
        );
        civile.setProfessione(new Civile());

        Zombie zombie = (Zombie) zombieFactory.creaAgente(
            posizioneZombie[0],
            posizioneZombie[1]
        );

        Runner runner = (Runner) runnerFactory.creaAgente(
            posizioneRunner[0],
            posizioneRunner[1]
        );

        Tank tank = (Tank) tankFactory.creaAgente(
            posizioneTank[0],
            posizioneTank[1]
        );

        mappa.aggiungiRisorsa(
            new RisorsaMunizioni(4, 3, 10)
        );

        mappa.aggiungiRisorsa(
            new RisorsaMunizioni(6, 5, 6)
        );

        mappa.aggiungiRisorsa(
            new RisorsaMedikit(2, 2, 30)
        );

        mappa.aggiungiRisorsa(
            new RisorsaMedikit(5, 1, 20)
        );

        mappa.aggiungiBarricata(
            new Barricata(7, 4, 100)
        );

        mappa.aggiungiBarricata(
            new Barricata(7, 5, 100)
        );

        mappa.aggiungiBarricata(
            new Barricata(7, 6, 100)
        );

        mappa.aggiungiZonaContaminata(
            new ZonaContaminata(1, 6)
        );

        mappa.aggiungiZonaContaminata(
            new ZonaContaminata(8, 2)
        );

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

    private static int[] posizioneCasuale(
        Random random,
        Set<String> posizioniOccupate
    ) {

        int x;
        int y;
        String posizione;

        do {
            x = random.nextInt(20);
            y = random.nextInt(20);
            posizione = x + "," + y;
        } while (posizioniOccupate.contains(posizione));

        posizioniOccupate.add(posizione);

        return new int[] {x, y};
    }
}