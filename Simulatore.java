import java.util.ArrayList;
import java.util.List;

public class Simulatore {

    private Mappa mappa;
    private List<Agente> agenti;
    private int tick;
    private boolean simulazioneTerminata;
    private List<Observer> observers;

    public Simulatore(Mappa mappa) {
        this.mappa = mappa;
        this.agenti = new ArrayList<>();
        this.tick = 0;
        this.simulazioneTerminata = false;
        this.observers = new ArrayList<>();
    }

    public void aggiungiAgente(Agente agente) {
        agenti.add(agente);
    }

    public void aggiungiObserver(Observer observer) {
        observers.add(observer);
    }

    public void eseguiTurno() {

        if (simulazioneTerminata) {
            return;
        }

        tick++;

        System.out.println("=== TICK " + tick + " ===");

        notificaObserver("Inizio Tick " + tick);

        for (Agente agente : new ArrayList<>(agenti)) {
            agente.percepisci(mappa);
        }

        for (Agente agente : new ArrayList<>(agenti)) {
            agente.agisci(mappa);
        }

        controllaTrasformazioni();
        controllaCollisioni();

        if (controllaFineSimulazione()) {
            simulazioneTerminata = true;
            System.out.println("=== SIMULAZIONE TERMINATA ===");
            return;
        }

        notificaObserver("Fine Tick " + tick);

        System.out.println();
    }

    private void controllaTrasformazioni() {

        List<Zombie> nuoviZombie = new ArrayList<>();

        for (Agente agente : new ArrayList<>(agenti)) {

            if (agente instanceof Umano) {

                Umano umano = (Umano) agente;

                if (umano.getStato() instanceof Infetto &&
                    umano.getTickInfezione() >= 3) {

                    Zombie nuovoZombie = umano.trasformatiInZombie();

                    nuoviZombie.add(nuovoZombie);

                    mappa.rimuoviAgente(umano);
                    agenti.remove(umano);

                    notificaObserver(
                        "Un umano è diventato uno zombie!"
                    );
                }
            }
        }

        for (Zombie zombie : nuoviZombie) {
            agenti.add(zombie);
            mappa.aggiungiAgente(zombie);
        }
    }

    private void controllaCollisioni() {

        for (int i = 0; i < agenti.size(); i++) {

            for (int j = i + 1; j < agenti.size(); j++) {

                Agente primo = agenti.get(i);
                Agente secondo = agenti.get(j);

                if (primo.siTrovaNellaStessaPosizione(secondo)) {

                    if (primo instanceof Zombie &&
                        secondo instanceof Umano &&
                        secondo.isVivo()) {

                        ((Zombie) primo).attacca((Umano) secondo);
                    }

                    if (primo instanceof Umano &&
                        secondo instanceof Zombie &&
                        primo.isVivo()) {

                        ((Zombie) secondo).attacca((Umano) primo);
                    }
                }
            }
        }
    }

    private boolean controllaFineSimulazione() {

        boolean ciSonoUmani = false;
        boolean ciSonoZombie = false;

        for (Agente agente : agenti) {

            if (agente instanceof Umano &&
                agente.isVivo()) {

                ciSonoUmani = true;
            }

            if (agente instanceof Zombie &&
                agente.isVivo()) {

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

            System.out.println(
                "Non ci sono più agenti!"
            );

            notificaObserver(
                "La simulazione è terminata senza vincitori."
            );

            return true;
        }

        return false;
    }

    private void notificaObserver(String messaggio) {

        for (Observer observer : observers) {
            observer.aggiorna(messaggio);
        }
    }
}