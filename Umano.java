import java.util.List;

public class Umano extends Agente {

    private boolean zombiePercepito;
    private StatoUmano stato;
    public Umano(int x, int y) {
        super(x, y, 100, 2);
        this.zombiePercepito = false;
        this.stato = new InEsplorazione();
    }

    public void setStato(StatoUmano stato) {
        this.stato = stato;
    }

    public StatoUmano getStato() {
        return stato;
    }

    @Override
    public void percepisci(Mappa mappa) {

        zombiePercepito = false;

        List<Agente> vicini = mappa.getAgentiVicini(this);

        for (Agente agente : vicini) {

            if (agente instanceof Zombie) {
                zombiePercepito = true;

                System.out.println(
                    "L'umano vede uno zombie!"
                );
            }
        }
    }

    @Override
    public void agisci(Mappa mappa) {

        stato.agisci(this, mappa);
    }
}