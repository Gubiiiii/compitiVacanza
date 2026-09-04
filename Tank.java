public class Tank extends Zombie {

    public Tank(int x, int y) {
        super(x, y, 150, 1);
    }

    @Override
    public void agisci(Mappa mappa) {

        System.out.println("Il Tank va avanti lento");

        Umano bersaglio = trovaBersaglio(mappa);

        if (bersaglio != null) {
            insegui(bersaglio, mappa);
        } else {
            muovi(velocita, 0, mappa);
        }

        System.out.println(
            "Tank: (" + x + ", " + y + ")"
        );
    }
}