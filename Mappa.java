import java.util.List;
import java.util.ArrayList;

public class Mappa {

    private int larghezza;
    private int altezza;
    private List<Agente> agenti;

    public Mappa(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.agenti = new ArrayList<>();
    }

    public boolean posizioneValida(int x, int y) {
        return x >= 0 && x < larghezza && y >= 0 && y < altezza;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public int getAltezza() {
        return altezza;
    }
    public void aggiungiAgente(Agente agente) {
        agenti.add(agente);
    }
    public List<Agente> getAgenti() {
        return agenti;
    }
}