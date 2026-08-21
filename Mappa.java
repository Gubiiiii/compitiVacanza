public class Mappa {

    private int larghezza;
    private int altezza;

    public Mappa(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    public boolean posizioneValida(int x, int y) {
        return x >= 0 && x < larghezza &&
               y >= 0 && y < altezza;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public int getAltezza() {
        return altezza;
    }
}