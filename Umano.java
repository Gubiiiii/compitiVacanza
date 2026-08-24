import java.util.List;

public class Umano extends Agente {

    private StatoUmano stato;
    private Professione professione;
    public Umano(int x, int y) {
        super(x, y, 100, 2);

        this.stato = new InEsplorazione();
        this.professione = new Civile();
    }

    public void setStato(StatoUmano stato) {
        this.stato = stato;
    }

    public StatoUmano getStato() {
        return stato;
    }

    @Override
    public void percepisci(Mappa mappa) {

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

                } else {

                    setStato(new InFuga());
                }
            }
        }
    }

    @Override
    public void agisci(Mappa mappa) {

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
}