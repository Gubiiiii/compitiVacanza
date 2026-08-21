public class Umano extends Agente {

    public Umano(int x, int y) {
        super(x, y, 100, 2);
    }

    @Override
    public void agisci() {
        System.out.println("L'umano si muove.");
    }
}