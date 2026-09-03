import java.util.ArrayList;
import java.util.List;

public class Umano extends Agente {

    private StatoUmano stato;
    private Professione professione;
    private List<Oggetto> inventario;
    private int tickInfezione;
    private List<Observer> observers;
    private boolean mortePerMorsoNotificata;
    private Zombie zombieContagio;

    public Umano(int x, int y) {
        super(x, y, 100, 2);

        this.stato = new InEsplorazione();
        this.professione = new Civile();
        this.inventario = new ArrayList<>();
        this.tickInfezione = 0;
        this.observers = new ArrayList<>();
        this.mortePerMorsoNotificata = false;
        this.zombieContagio = null;
    }

    public void setStato(StatoUmano stato) {
        this.stato = stato;
    }

    public StatoUmano getStato() {
        return stato;
    }

    @Override
    public void percepisci(Mappa mappa) {
        if (stato instanceof Infetto) {
            return;
        }

        Zombie zombie = trovaZombieVicino(mappa);

        if (zombie == null) {
            return;
        }

        if (professione instanceof Soldato && puoCombattere()) {
            System.out.println("Il soldato punta lo zombie piu vicino!");
            setStato(new InCombattimento(zombie));
        } else {
            System.out.println("L'umano vede uno zombie e scappa!");

            if (stato instanceof InFuga) {
                ((InFuga) stato).aggiornaMinaccia(zombie);
            } else {
                setStato(new InFuga(zombie));
            }
        }
    }

    public Zombie trovaZombiePiuVicino(Mappa mappa) {

        Zombie bersaglio = null;
        int distanzaMinima = Integer.MAX_VALUE;

        for (Agente agente : mappa.getAgenti()) {

            if (!(agente instanceof Zombie) || !agente.isVivo()) {
                continue;
            }

            Zombie zombie = (Zombie) agente;
            int distanza = distanzaDa(zombie);

            if (distanza < distanzaMinima) {
                distanzaMinima = distanza;
                bersaglio = zombie;
            }
        }

        return bersaglio;
    }

    private Zombie trovaZombieVicino(Mappa mappa) {

        Zombie bersaglio = null;
        int distanzaMinima = Integer.MAX_VALUE;

        for (Agente agente : mappa.getAgentiVicini(this)) {

            if (!(agente instanceof Zombie) || !agente.isVivo()) {
                continue;
            }

            Zombie zombie = (Zombie) agente;
            int distanza = distanzaDa(zombie);

            if (distanza < distanzaMinima) {
                distanzaMinima = distanza;
                bersaglio = zombie;
            }
        }

        return bersaglio;
    }

    public Umano trovaFeritoPiuVicino(Mappa mappa) {

        Umano ferito = null;
        int distanzaMinima = Integer.MAX_VALUE;

        for (Agente agente : mappa.getAgenti()) {

            if (!(agente instanceof Umano) ||
                agente == this ||
                !agente.isVivo()) {

                continue;
            }

            Umano umano = (Umano) agente;

            if (umano.getSalute() >= 100) {
                continue;
            }

            int distanza = distanzaDa(umano);

            if (distanza < distanzaMinima) {
                distanzaMinima = distanza;
                ferito = umano;
            }
        }

        return ferito;
    }

    @Override
    public void agisci(Mappa mappa) {

        raccogliRisorsa(mappa);

        if (stato instanceof Infetto) {
            aumentaTickInfezione();
            stato.agisci(this, mappa);
            return;
        }

        if (professione instanceof Medico) {
            professione.agisci(this, mappa);
            return;
        }

        stato.agisci(this, mappa);
    }

    public void attacca(Zombie zombie) {

        int danno = 48;

        zombie.subisciDanno(danno);

        System.out.println(
            "L'umano attacca lo zombie! -" + danno + " salute"
        );

        System.out.println(
            "Salute zombie: " + zombie.getSalute()
        );
    }

    public void setProfessione(Professione professione) {
        this.professione = professione;
    }

    public Professione getProfessione() {
        return professione;
    }

    public void aggiungiOggetto(Oggetto oggetto) {
        inventario.add(oggetto);
        System.out.println("L'umano ha raccolto: " + oggetto.getNome());
    }
    public List<Oggetto> getInventario() {
        return inventario;
    }

    public boolean puoCombattere() {
        if (!(professione instanceof Soldato)) {
            return false;
        }

        for (Oggetto oggetto : inventario) {

            if (oggetto instanceof Munizioni &&
                ((Munizioni) oggetto).getQuantita() > 0) {

                return true;
            }
        }

        return false;
    }
    public void raccogliRisorsa(Mappa mappa) {

        Risorsa risorsa = mappa.getRisorsaAllaPosizione(x, y);
        if (risorsa == null) {
            return;
        }
        if (risorsa instanceof RisorsaMedikit) {
            RisorsaMedikit medikit = (RisorsaMedikit) risorsa;
            aggiungiOggetto(
                new Medikit(medikit.getCura())
            );
        } else if (risorsa instanceof RisorsaMunizioni) {
            RisorsaMunizioni munizioni =
                (RisorsaMunizioni) risorsa;
            aggiungiOggetto(
                new Munizioni(munizioni.getQuantita())
            );
        }
        mappa.getRisorse().remove(risorsa);
    }
    public void cura(int quantita) {
        salute += quantita;
        if (salute > 100) {
            salute = 100;
        }
        System.out.println(
            "L'umano è stato curato! +" + quantita + " salute"
        );
        System.out.println(
            "Salute umano: " + salute
        );
    }
    public int getSalute() {
        return salute;
    }
    public Zombie getZombieContagio() {
        return zombieContagio;
    }

    public void aggiungiObserver(Observer observer) {
        observers.add(observer);
    }

    public void rimuoviObserver(Observer observer) {
        observers.remove(observer);
    }

    public void muoriPerMorso() {
        muoriPerMorso(null);
    }

    public void muoriPerMorso(Zombie zombie) {
        if (mortePerMorsoNotificata) {
            return;
        }

        zombieContagio = zombie;
        mortePerMorsoNotificata = true;

        System.out.println(
            "L'umano è morto per un morso e avvia il contagio!"
        );

        for (Observer observer : observers) {
            observer.onMortePerMorso(this);
        }
    }

    public void infetta() {
        if (!(stato instanceof Infetto)) {

            setStato(new Infetto());
            tickInfezione = 0;

            System.out.println(
                "L'umano è stato infettato!"
            );
        }
    }
    public void aumentaTickInfezione() {
        tickInfezione++;

        System.out.println(
            "Tick infezione: " + tickInfezione
        );
    }

    public int getTickInfezione() {
        return tickInfezione;
    }
}
