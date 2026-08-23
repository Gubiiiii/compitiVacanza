public class InEsplorazione implements StatoUmano {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        umano.muovi(1, 0, mappa);

        System.out.println(
            "L'umano è in esplorazione e si muove."
        );
    }
}