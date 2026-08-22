public class Umano extends Agente {

    public Umano(int x, int y) {
        super(x, y, 100, 2);
    }

    @Override
    public void agisci(Mappa mappa) {
        muovi(1, 0, mappa);

        System.out.println("L'umano si muove a (" + x + ", " + y + ")");
    }
}