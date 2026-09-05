public class Runner extends Zombie {

    public Runner(int x, int y) {
        super(x, y, 50, 3);
    }

    @Override
    public void agisci(Mappa mappa) {

        System.out.println("Il Runner si muove velocemente!");

        Umano bersaglio = trovaBersaglio(mappa);

        if (bersaglio != null) {
            insegui(bersaglio, mappa);
        } else {
            muovi(velocita, 0, mappa);
        }

        System.out.println(
            "Runner: (" + x + ", " + y + ")"
        );
    }
}