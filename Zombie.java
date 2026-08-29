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
                System.out.println("Lo zombie fiuta un umano!");
            }
        }
    }

    @Override
    public void agisci(Mappa mappa) {

        muovi(-1, 0, mappa);

        System.out.println(
            "Lo zombie si muove a (" + x + ", " + y + ")"
        );
    }

    public void attacca(Umano umano) {

        int danno = 20;

        umano.subisciDanno(danno);

        System.out.println(
            "Lo zombie morde l'umano! -" + danno + " salute"
        );

        System.out.println(
            "Salute umano: " + umano.getSalute()
        );

        if (umano.getSalute() <= 0) {
            umano.infetta();
        }
    }
}