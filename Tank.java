public class Tank extends Zombie {

    public Tank(int x, int y) {
        super(x, y, 150, 1);
    }

    @Override
    public void agisci(Mappa mappa) {
        System.out.println("Il Tank avanza lentamente!");
        muovi(1, 0, mappa);

        System.out.println("Tank: (" + x + ", " + y + ")");
    }
}