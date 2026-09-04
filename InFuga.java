public class InFuga implements StatoUmano {

    private Zombie minaccia;
    private int ultimaX;
    private int ultimaY;

    public InFuga() {
        this(null);
    }

    public InFuga(Zombie minaccia) {
        this.minaccia = minaccia;
        this.ultimaX = -1;
        this.ultimaY = -1;
    }

    public void aggiornaMinaccia(Zombie minaccia) {
        this.minaccia = minaccia;
    }

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (minaccia == null || !minaccia.isVivo()) {
            minaccia = umano.trovaZombiePiuVicino(mappa);
        }

        for (int passo = 0; passo < umano.getVelocita(); passo++) {

            int[] direzione = scegliDirezioneMigliore(umano, mappa);

            if (direzione == null) {
                break;
            }

            ultimaX = umano.getX();
            ultimaY = umano.getY();
            umano.muovi(direzione[0], direzione[1], mappa);
        }

        System.out.println(
            "L'umano è in fuga!"
        );
    }

    private int[] scegliDirezioneMigliore(Umano umano, Mappa mappa) {

        int[][] direzioni = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        int[] miglioreDirezione = null;
        int migliorePunteggio = Integer.MIN_VALUE;

        for (int[] direzione : direzioni) {

            int nuovaX = umano.getX() + direzione[0];
            int nuovaY = umano.getY() + direzione[1];

            if (!mappa.posizioneValidaPerMovimento(nuovaX, nuovaY, umano)) {
                continue;
            }

            int punteggio = calcolaPunteggioFuga(nuovaX, nuovaY, mappa);

            if (nuovaX == ultimaX && nuovaY == ultimaY) {
                punteggio -= 1000;
            }

            if (minaccia != null) {
                punteggio += distanza(
                    nuovaX,
                    nuovaY,
                    minaccia.getX(),
                    minaccia.getY()
                );
            }

            if (punteggio > migliorePunteggio) {
                migliorePunteggio = punteggio;
                miglioreDirezione = direzione;
            }
        }

        return miglioreDirezione;
    }

    private int calcolaPunteggioFuga(int x, int y, Mappa mappa) {

        int distanzaMinima = Integer.MAX_VALUE;
        int distanzaTotale = 0;
        boolean trovatoZombie = false;

        for (Agente agente : mappa.getAgenti()) {

            if (!(agente instanceof Zombie) || !agente.isVivo()) {
                continue;
            }

            trovatoZombie = true;

            int distanza = distanza(
                x,
                y,
                agente.getX(),
                agente.getY()
            );

            distanzaMinima = Math.min(distanzaMinima, distanza);
            distanzaTotale += distanza;
        }

        if (!trovatoZombie) {
            return 0;
        }

        return distanzaMinima * 100 + distanzaTotale;
    }

    private int distanza(int primaX, int primaY, int secondaX, int secondaY) {
        return Math.abs(primaX - secondaX) +
               Math.abs(primaY - secondaY);
    }
}
