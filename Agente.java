public abstract class Agente {

    protected int x;
    protected int y;
    protected int salute;
    protected int velocita;

    public Agente(int x, int y, int salute, int velocita) {
        this.x = x;
        this.y = y;
        this.salute = salute;
        this.velocita = velocita;
    }

    public abstract void percepisci(Mappa mappa);

    public abstract void agisci(Mappa mappa);

    public void muovi(int dx, int dy, Mappa mappa) {
        int nuovaX = x + dx;
        int nuovaY = y + dy;

        if (mappa.posizioneValida(nuovaX, nuovaY)) {
            x = nuovaX;
            y = nuovaY;
        }
    }

    public void muoviVerso(Agente bersaglio, Mappa mappa) {
        muoviOttimizzandoDistanza(bersaglio, mappa, true);
    }

    public void muoviLontanoDa(Agente minaccia, Mappa mappa) {
        muoviOttimizzandoDistanza(minaccia, mappa, false);
    }

    private void muoviOttimizzandoDistanza(Agente riferimento,Mappa mappa,boolean avvicina) {
        for (int passo = 0; passo < velocita; passo++) {
            int[][] direzioni = direzioniPreferite(riferimento, avvicina);

            int[] direzioneScelta = null;

            for (int[] direzione : direzioni) {

                int nuovaX = x + direzione[0];
                int nuovaY = y + direzione[1];

                if (mappa.posizioneValida(nuovaX, nuovaY)) {
                    direzioneScelta = direzione;
                    break;
                }
            }

            if (direzioneScelta == null) {
                // Completamente bloccato in ogni direzione: si ferma
                return;
            }

            x += direzioneScelta[0];
            y += direzioneScelta[1];
        }
    }

    private int[][] direzioniPreferite(
        Agente riferimento,
        boolean avvicina
    ) {

        int versoX = Integer.compare(riferimento.getX(), x);
        int versoY = Integer.compare(riferimento.getY(), y);

        if (!avvicina) {
            versoX = -versoX;
            versoY = -versoY;
        }

        if (Math.abs(riferimento.getX() - x) >=
            Math.abs(riferimento.getY() - y)) {

            return new int[][] {
                {versoX, 0},
                {0, versoY},
                {0, -versoY},
                {-versoX, 0},
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
            };
        }

        return new int[][] {
            {0, versoY},
            {versoX, 0},
            {-versoX, 0},
            {0, -versoY},
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };
    }


    public void subisciDanno(int danno) {
        salute -= danno;

        if (salute < 0) {
            salute = 0;
        }
    }

    public boolean isVivo() {
        return salute > 0;
    }

    public boolean siTrovaNellaStessaPosizione(Agente altro) {
        return this.x == altro.x && this.y == altro.y;
    }

    public int distanzaDa(Agente altro) {
        return Math.abs(this.x - altro.x) + Math.abs(this.y - altro.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSalute() {
        return salute;
    }

    public int getVelocita() {
        return velocita;
    }
}
