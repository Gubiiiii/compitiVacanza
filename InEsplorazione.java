public class InEsplorazione implements StatoUmano {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        Risorsa risorsa = trovaRisorsaPiuVicina(umano, mappa);

        if (risorsa != null) {
            muoviVersoRisorsa(umano, risorsa, mappa);

            System.out.println(
                "L'umano esplora e si muove verso una risorsa."
            );

            return;
        }

        System.out.println(
            "L'umano è in esplorazione, ma non vede risorse."
        );
    }

    private Risorsa trovaRisorsaPiuVicina(Umano umano, Mappa mappa) {

        Risorsa risorsaPiuVicina = null;
        int distanzaMinima = Integer.MAX_VALUE;

        for (Risorsa risorsa : mappa.getRisorse()) {

            int distanza = Math.abs(umano.getX() - risorsa.getX()) +
                           Math.abs(umano.getY() - risorsa.getY());

            if (distanza < distanzaMinima) {
                distanzaMinima = distanza;
                risorsaPiuVicina = risorsa;
            }
        }

        return risorsaPiuVicina;
    }

    private void muoviVersoRisorsa(
        Umano umano,
        Risorsa risorsa,
        Mappa mappa
    ) {

        for (int passo = 0; passo < umano.getVelocita(); passo++) {

            int dx = Integer.compare(risorsa.getX(), umano.getX());
            int dy = Integer.compare(risorsa.getY(), umano.getY());

            if (Math.abs(risorsa.getX() - umano.getX()) >=
                Math.abs(risorsa.getY() - umano.getY())) {

                if (dx != 0) {
                    umano.muovi(dx, 0, mappa);
                } else if (dy != 0) {
                    umano.muovi(0, dy, mappa);
                }
            } else {

                if (dy != 0) {
                    umano.muovi(0, dy, mappa);
                } else if (dx != 0) {
                    umano.muovi(dx, 0, mappa);
                }
            }
        }
    }
}
