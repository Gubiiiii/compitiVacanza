public class InFuga implements StatoUmano {

    private Zombie minaccia;

    public InFuga() {
        this(null);
    }

    public InFuga(Zombie minaccia) {
        this.minaccia = minaccia;
    }

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (minaccia == null || !minaccia.isVivo()) {
            minaccia = umano.trovaZombiePiuVicino(mappa);
        }

        if (minaccia != null) {
            umano.muoviLontanoDa(minaccia, mappa);
        }

        System.out.println(
            "L'umano è in fuga!"
        );
    }
}
