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