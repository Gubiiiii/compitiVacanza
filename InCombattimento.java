public class InCombattimento implements StatoUmano {

    private Zombie bersaglio;

    public InCombattimento(Zombie bersaglio) {
        this.bersaglio = bersaglio;
    }

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (!bersaglio.isVivo()) {

            System.out.println("Lo zombie è morto!");
            return;
        }

        int distanza = umano.distanzaDa(bersaglio);

        if (distanza <= 1) {

            umano.attacca(bersaglio);

        } else {

            System.out.println(
                "Lo zombie è troppo lontano per essere attaccato."
            );
        }
    }

    public Zombie getBersaglio() {
        return bersaglio;
    }
}