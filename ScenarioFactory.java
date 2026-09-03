import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ScenarioFactory {

    private ScenarioFactory() {
    }

    public static Simulatore creaScenarioBase(boolean usaConsole) {

        Mappa mappa = new Mappa(20, 20);
        Simulatore simulatore = new Simulatore(mappa, 250);

        AgenteFactory umanoFactory = new UmanoFactory();
        AgenteFactory zombieFactory = new ZombieFactory();
        AgenteFactory runnerFactory = new RunnerFactory();
        AgenteFactory tankFactory = new TankFactory();

        Random random = new Random();
        Set<String> posizioniOccupate = new HashSet<>();

        int numeroSoldati = random.nextInt(3) + 2;
        int numeroMedici = random.nextInt(2) + 4;
        int numeroCivili = random.nextInt(3) + 2;

        //int numeroZombie = random.nextInt(4) + 1;
        int numeroRunner = random.nextInt(2) + 1;
        int numeroTank = random.nextInt(2) + 1;

        for (int i = 0; i < numeroSoldati; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Umano soldato = (Umano) umanoFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            soldato.setProfessione(new Soldato());
            soldato.aggiungiOggetto(
                new Munizioni(random.nextInt(6) + 10)
            );

            simulatore.aggiungiAgente(soldato);
        }

        for (int i = 0; i < numeroMedici; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Umano medico = (Umano) umanoFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            medico.setProfessione(new Medico());
            medico.aggiungiOggetto(new Medikit(40));

            simulatore.aggiungiAgente(medico);
        }

        for (int i = 0; i < numeroCivili; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Umano civile = (Umano) umanoFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            civile.setProfessione(new Civile());

            simulatore.aggiungiAgente(civile);
        }


        for (int i = 0; i < numeroTank; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Tank tank = (Tank) tankFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            simulatore.aggiungiAgente(tank);
        }

        for (int i = 0; i < numeroRunner; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Runner runner = (Runner) runnerFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            simulatore.aggiungiAgente(runner);
        }

        for (int i = 0; i < numeroTank; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            Tank tank = (Tank) tankFactory.creaAgente(
                posizione[0],
                posizione[1]
            );

            simulatore.aggiungiAgente(tank);
        }

        int numeroMunizioni = random.nextInt(3) + 3;

        for (int i = 0; i < numeroMunizioni; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            int quantita = random.nextInt(11) + 5;

            mappa.aggiungiRisorsa(
                new RisorsaMunizioni(
                    posizione[0],
                    posizione[1],
                    quantita
                )
            );
        }

        int numeroMedikit = random.nextInt(3) + 3;

        for (int i = 0; i < numeroMedikit; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            int cura = random.nextInt(21) + 20;

            mappa.aggiungiRisorsa(
                new RisorsaMedikit(
                    posizione[0],
                    posizione[1],
                    cura
                )
            );
        }

        int numeroBarricate = random.nextInt(3) + 2;

        for (int i = 0; i < numeroBarricate; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            mappa.aggiungiBarricata(
                new Barricata(
                    posizione[0],
                    posizione[1],
                    100
                )
            );
        }

        int numeroZoneContaminate = random.nextInt(2) + 2;

        for (int i = 0; i < numeroZoneContaminate; i++) {

            int[] posizione = posizioneCasuale(
                random,
                posizioniOccupate
            );

            mappa.aggiungiZonaContaminata(
                new ZonaContaminata(
                    posizione[0],
                    posizione[1]
                )
            );
        }

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
