import java.util.ArrayList;
import java.util.List;

public class Umano extends Agente {

    private StatoUmano stato;
    private Professione professione;
    private List<Oggetto> inventario;
    private int tickInfezione;
    public Umano(int x, int y) {
        super(x, y, 100, 2);

        this.stato = new InEsplorazione();
        this.professione = new Civile();
        this.inventario = new ArrayList<>();
        this.tickInfezione = 0;
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

        List<Agente> vicini = mappa.getAgentiVicini(this);

        for (Agente agente : vicini) {

            if (agente instanceof Zombie) {

                System.out.println(
                    "L'umano vede uno zombie!"
                );
                if (professione instanceof Soldato) {

                    setStato(
                        new InCombattimento((Zombie) agente)
                    );
                } else if (professione instanceof Medico) {
                } else {
                    setStato(new InFuga());
                }
            }
        }
    }

    @Override
    public void agisci(Mappa mappa) {

        raccogliRisorsa(mappa);

        if (professione instanceof Medico) {
            professione.agisci(this, mappa);
        }

        if (stato instanceof Infetto) {
            aumentaTickInfezione();
        }

        stato.agisci(this, mappa);
    }

    public void attacca(Zombie zombie) {

        int danno = 10;

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
    public Zombie trasformatiInZombie() {
        System.out.println("L'umano si è trasformato in zombie!");
        return new Zombie(x, y);
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