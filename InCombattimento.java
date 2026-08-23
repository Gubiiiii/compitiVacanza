public class InCombattimento implements StatoUmano {

    private Zombie bersaglio;

    public InCombattimento(Zombie bersaglio) {
        this.bersaglio = bersaglio;
    }

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (bersaglio.isVivo()) {

            umano.attacca(bersaglio);

        } else {

            System.out.println(
                "Lo zombie è morto!"
            );
        }
    }

    public Zombie getBersaglio() {
        return bersaglio;
    }
}