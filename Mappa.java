import java.util.List;
import java.util.ArrayList;

public class Mappa {

    private int larghezza;
    private int altezza;
    private List<Agente> agenti;
    private List<Risorsa> risorse;

    public Mappa(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.agenti = new ArrayList<>();
        risorse = new ArrayList<>();
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


    public List<Agente> getAgentiVicini(Agente agente) {

        List<Agente> vicini = new ArrayList<>();

        for (Agente altro : agenti) {

            if (altro == agente) {
                continue;
            }

            int distanzaX = Math.abs(agente.getX() - altro.getX());
            int distanzaY = Math.abs(agente.getY() - altro.getY());

            if (distanzaX <= 1 && distanzaY <= 1) {
                vicini.add(altro);
            }
        }

        return vicini;
    }
    public void aggiungiRisorsa(Risorsa risorsa) {
        risorse.add(risorsa);
    }
    public List<Risorsa> getRisorse() {
        return risorse;
    }

    public Risorsa getRisorsaAllaPosizione(int x, int y) {

        for (Risorsa risorsa : risorse) {

            if (risorsa.getX() == x && risorsa.getY() == y) {
                return risorsa;
            }
        }

        return null;
    }
}