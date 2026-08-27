import java.util.ArrayList;
import java.util.List;

public class Simulatore {

    private Mappa mappa;
    private List<Agente> agenti;
    private List<Observer> osservatori;
    private int tick;
    private boolean simulazioneTerminata;

    public Simulatore(Mappa mappa) {
        this.mappa = mappa;
        this.agenti = new ArrayList<>();
        this.osservatori = new ArrayList<>();
        this.tick = 0;
        this.simulazioneTerminata = false;
    }

    public void aggiungiAgente(Agente agente) {
        agenti.add(agente);
    }

    public void rimuoviAgente(Agente agente) {
        agenti.remove(agente);
    }

    public void aggiungiObserver(Observer observer) {
        osservatori.add(observer);
    }

    public void notificaObserver(String evento) {

        for (Observer observer : osservatori) {
            observer.aggiorna(evento);
        }
    }

    public void eseguiTurno() {
        if (simulazioneTerminata) {return;}

        tick++;

        System.out.println("=== TICK " + tick + " ===");

        notificaObserver(
            "Inizio Tick " + tick
        );

        // FASE 1: PERCEZIONE
        for (Agente agente : agenti) {
            agente.percepisci(mappa);
        }

        // FASE 2: AZIONE
        for (Agente agente : agenti) {
            agente.agisci(mappa);
        }

        // FASE 3: COLLISIONI
        controllaCollisioni();

        if (controllaFineSimulazione()) {
            simulazioneTerminata = true;
            System.out.println("=== SIMULAZIONE TERMINATA ===");
            return;
        }

        notificaObserver(
            "Fine Tick " + tick
        );

        System.out.println();
    }

    private void controllaCollisioni() {

        for (int i = 0; i < agenti.size(); i++) {

            for (int j = i + 1; j < agenti.size(); j++) {

                Agente primo = agenti.get(i);
                Agente secondo = agenti.get(j);

                // Consideriamo solo collisioni tra Umano e Zombie
                boolean umanoZombie =
                    (primo instanceof Umano && secondo instanceof Zombie)
                    ||
                    (primo instanceof Zombie && secondo instanceof Umano);

                if (!umanoZombie) {
                    continue;
                }

                if (primo.siTrovaNellaStessaPosizione(secondo)) {

                    String evento =
                        "COLLISIONE tra " +
                        primo.getClass().getSimpleName() +
                        " e " +
                        secondo.getClass().getSimpleName();

                    System.out.println(evento);

                    notificaObserver(evento);

                    if (primo instanceof Zombie && secondo instanceof Umano) {

                        Zombie zombie = (Zombie) primo;
                        Umano umano = (Umano) secondo;

                        zombie.attacca(umano);

                        controllaTrasformazione(umano);
                    }

                    if (primo instanceof Umano && secondo instanceof Zombie) {

                        Umano umano = (Umano) primo;
                        Zombie zombie = (Zombie) secondo;

                        zombie.attacca(umano);

                        controllaTrasformazione(umano);
                    }
                }
            }
        }
    }

    private void controllaTrasformazione(Umano umano) {
        if (!umano.isVivo()) {
            Zombie nuovoZombie = umano.trasformatiInZombie();
            rimuoviAgente(umano);
            mappa.rimuoviAgente(umano);

            aggiungiAgente(nuovoZombie);
            mappa.aggiungiAgente(nuovoZombie);

            notificaObserver(
                "Un umano è diventato uno zombie!"
            );
        }
    }
    private boolean controllaFineSimulazione() {
        boolean ciSonoUmani = false;
        boolean ciSonoZombie = false;

        for (Agente agente : agenti) {

            if (!agente.isVivo()) {
                continue;
            }

            if (agente instanceof Umano) {
                ciSonoUmani = true;
            }

            if (agente instanceof Zombie) {
                ciSonoZombie = true;
            }
        }

        if (!ciSonoUmani && ciSonoZombie) {

            System.out.println("Gli zombie hanno vinto!");

            notificaObserver(
                "Gli zombie hanno vinto!"
            );

            return true;
        }

        if (ciSonoUmani && !ciSonoZombie) {

            System.out.println("Gli umani hanno vinto!");

            notificaObserver(
                "Gli umani hanno vinto!"
            );

            return true;
        }

        if (!ciSonoUmani && !ciSonoZombie) {

            System.out.println("Non ci sono più agenti!");

            notificaObserver(
                "La simulazione è terminata senza vincitori."
            );

            return true;
        }

        return false;
    }
}