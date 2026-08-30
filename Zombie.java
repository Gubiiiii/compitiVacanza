import java.util.List;

public class Zombie extends Agente {

    public Zombie(int x, int y) {
        super(x, y, 80, 1);
    }

    public Zombie(int x, int y, int salute, int velocita) {
        super(x, y, salute, velocita);
    }

    @Override
    public void percepisci(Mappa mappa) {

        List<Agente> vicini = mappa.getAgentiVicini(this);

        for (Agente agente : vicini) {

            if (agente instanceof Umano) {

                Umano umano = (Umano) agente;

                if (umano.isVivo()) {
                    System.out.println("Lo zombie fiuta un umano!");
                }
            }
        }
    }

    @Override
    public void agisci(Mappa mappa) {

        Umano bersaglio = trovaBersaglio(mappa);

        if (bersaglio != null) {
            insegui(bersaglio, mappa);
        } else {
            muovi(-velocita, 0, mappa);

            System.out.println(
                "Lo zombie si muove a (" + x + ", " + y + ")"
            );
        }
    }

    protected Umano trovaBersaglio(Mappa mappa) {

        Umano bersaglio = null;
        int distanzaMinima = Integer.MAX_VALUE;

        for (Agente agente : mappa.getAgenti()) {

            if (agente instanceof Umano) {

                Umano umano = (Umano) agente;

                if (!umano.isVivo()) {
                    continue;
                }

                int distanza = distanzaDa(umano);

                if (distanza < distanzaMinima) {
                    distanzaMinima = distanza;
                    bersaglio = umano;
                }
            }
        }

        return bersaglio;
    }

    protected void insegui(Umano bersaglio, Mappa mappa) {

        int dx = 0;
        int dy = 0;

        if (bersaglio.getX() > x) {
            dx = velocita;
        } else if (bersaglio.getX() < x) {
            dx = -velocita;
        }

        if (dx == 0) {

            if (bersaglio.getY() > y) {
                dy = velocita;
            } else if (bersaglio.getY() < y) {
                dy = -velocita;
            }
        }

        muovi(dx, dy, mappa);
    }

    public void attacca(Umano umano) {

        if (!isVivo() || !umano.isVivo()) {
            return;
        }

        int danno = 20;

        umano.subisciDanno(danno);

        System.out.println(
            "Lo zombie morde l'umano! -" + danno + " salute"
        );

        System.out.println(
            "Salute umano: " + umano.getSalute()
        );

        if (umano.getSalute() <= 0) {
            umano.muoriPerMorso();
        }
    }
}
