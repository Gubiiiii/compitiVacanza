import java.util.ArrayList;
import java.util.List;

public class Simulatore implements Observer {

    private Mappa mappa;
    private List<Agente> agenti;
    private int tick;
    private int maxTick;
    private boolean simulazioneTerminata;
    private List<Observer> observers;
    private List<Umano> trasformazioniPendenti;

    public Simulatore(Mappa mappa) {
        this(mappa, 1000);
    }

    public Simulatore(Mappa mappa, int maxTick) {
        this.mappa = mappa;
        this.agenti = new ArrayList<>();
        this.tick = 0;
        this.maxTick = maxTick;
        this.simulazioneTerminata = false;
        this.observers = new ArrayList<>();
        this.trasformazioniPendenti = new ArrayList<>();
    }

    public void aggiungiAgente(Agente agente) {
        agenti.add(agente);

        if (!mappa.getAgenti().contains(agente)) {
            mappa.aggiungiAgente(agente);
        }

        if (agente instanceof Umano) {
            ((Umano) agente).aggiungiObserver(this);
        }
    }

    public void aggiungiObserver(Observer observer) {
        observers.add(observer);
    }

    public void eseguiTurno() {

        if (simulazioneTerminata) {
            return;
        }

        tick++;

        System.out.println("TICK " + tick);

        notificaObserver("Inizio Tick " + tick);

        for (Agente agente : new ArrayList<>(agenti)) {
            if (agente.isVivo()) {
                agente.percepisci(mappa);
            }
        }

        for (Agente agente : new ArrayList<>(agenti)) {
            if (agente.isVivo()) {
                agente.agisci(mappa);
            }
        }

        applicaEffettiAmbientali();
        controllaCollisioni();
        controllaTrasformazioni();
        rimuoviZombieMorti();

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

                if (umano.isVivo() &&
                    umano.getStato() instanceof Infetto &&
                    umano.getTickInfezione() >= 3 &&
                    !trasformazioniPendenti.contains(umano)) {

                    trasformazioniPendenti.add(umano);

                    notificaObserver(
                        "Un umano infetto sta per trasformarsi."
                    );
                }
            }
        }

        for (Umano umano : new ArrayList<>(trasformazioniPendenti)) {

            if (!agenti.contains(umano)) {
                trasformazioniPendenti.remove(umano);
                continue;
            }

            ZombieFactory factory = new ZombieFactory();
            Zombie nuovoZombie = factory.creaDaMorso(
                umano.getX(),
                umano.getY(),
                umano.getZombieContagio()
            );

            nuoviZombie.add(nuovoZombie);

            umano.rimuoviObserver(this);
            mappa.rimuoviAgente(umano);
            agenti.remove(umano);
            trasformazioniPendenti.remove(umano);

            notificaObserver(
                "Un umano è stato trasformato in zombie."
            );
        }

        for (Zombie zombie : nuoviZombie) {
            aggiungiAgente(zombie);
        }
    }

    private void applicaEffettiAmbientali() {

        for (Agente agente : new ArrayList<>(agenti)) {

            if (agente instanceof Umano &&
                agente.isVivo() &&
                mappa.contieneZonaContaminata(
                    agente.getX(),
                    agente.getY()
                )) {

                Umano umano = (Umano) agente;
                umano.infetta();

                notificaObserver(
                    "Un umano è entrato in una zona contaminata."
                );
            }
        }
    }

    private void rimuoviZombieMorti() {

        for (Agente agente : new ArrayList<>(agenti)) {

            if (agente instanceof Zombie &&
                !agente.isVivo()) {

                agenti.remove(agente);
                mappa.rimuoviAgente(agente);

                notificaObserver(
                    "Uno zombie è stato eliminato."
                );
            }
        }
    }

    private void controllaCollisioni() {

        for (int i = 0; i < agenti.size(); i++) {

            for (int j = i + 1; j < agenti.size(); j++) {

                Agente primo = agenti.get(i);
                Agente secondo = agenti.get(j);

                if (!primo.isVivo() || !secondo.isVivo()) {
                    continue;
                }

                if (!primo.siTrovaNellaStessaPosizione(secondo)) {
                    continue;
                }

                if (primo instanceof Zombie &&
                    secondo instanceof Umano) {

                    ((Zombie) primo).attacca((Umano) secondo);
                }

                else if (primo instanceof Umano &&
                         secondo instanceof Zombie) {

                    ((Zombie) secondo).attacca((Umano) primo);
                }
            }
        }
    }

    private boolean controllaFineSimulazione() {

        boolean ciSonoUmani = false;
        boolean ciSonoZombie = false;
        boolean ciSonoSoldatiArmati = false;

        for (Agente agente : agenti) {

            if (agente instanceof Umano &&
                agente.isVivo()) {

                ciSonoUmani = true;

                if (((Umano) agente).puoCombattere()) {
                    ciSonoSoldatiArmati = true;
                }
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

        if (ciSonoUmani && ciSonoZombie && !ciSonoSoldatiArmati) {

            System.out.println(
                "Gli zombie hanno vinto: non ci sono piu soldati armati!"
            );

            notificaObserver(
                "Gli zombie hanno vinto: non ci sono piu soldati armati!"
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

        if (tick >= maxTick) {

            System.out.println(
                "Stallo: limite massimo di turni raggiunto."
            );

            notificaObserver(
                "Stallo: limite massimo di turni raggiunto."
            );

            return true;
        }

        return false;
    }

    @Override
    public void aggiorna(String evento) {
        notificaObserver(evento);
    }

    @Override
    public void onMortePerMorso(Umano umano) {

        if (!trasformazioniPendenti.contains(umano)) {
            trasformazioniPendenti.add(umano);
        }

        notificaObserver(
            "Evento onMortePerMorso ricevuto dal simulatore."
        );
    }

    private void notificaObserver(String messaggio) {

        for (Observer observer : observers) {
            observer.aggiorna(messaggio);
        }
    }

    public int getTick() {
        return tick;
    }

    public boolean isSimulazioneTerminata() {
        return simulazioneTerminata;
    }

    public Mappa getMappa() {
        return mappa;
    }

    public List<Agente> getAgenti() {
        return new ArrayList<>(agenti);
    }

    public int contaUmaniVivi() {

        int totale = 0;

        for (Agente agente : agenti) {
            if (agente instanceof Umano &&
                agente.isVivo()) {
                totale++;
            }
        }

        return totale;
    }

    public int contaZombieVivi() {

        int totale = 0;

        for (Agente agente : agenti) {
            if (agente instanceof Zombie &&
                agente.isVivo()) {
                totale++;
            }
        }

        return totale;
    }
}
