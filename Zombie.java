public class Zombie extends Agente {

    public Zombie(int x, int y) {
        super(x, y, 80, 1);
    }

    @Override
    public void agisci(Mappa mappa) {
        muovi(-1, 0, mappa);

         System.out.println("Lo zombie si muove a (" + x + ", " + y + ")");
}
    
}