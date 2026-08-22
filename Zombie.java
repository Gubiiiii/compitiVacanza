public class Zombie extends Agente {

    public Zombie(int x, int y) {
        super(x, y, 80, 1);
    }

    @Override
    public void agisci(Mappa mappa) {
        muovi(-1, 0, mappa);

         System.out.println("Lo zombie si muove a (" + x + ", " + y + ")");
    }

    public void attacca(Umano umano) {

        int danno = 20;

        umano.subisciDanno(danno);

        System.out.println("Lo zombie morde l'umano! -" + danno + " salute");

        System.out.println("Salute umano: " + umano.getSalute());
    }
    
}