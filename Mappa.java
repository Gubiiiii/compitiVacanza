import java.util.List;
import java.util.ArrayList;

public class Mappa {

    private int larghezza;
    private int altezza;
    private List<Agente> agenti;
    private List<Risorsa> risorse;
    private List<Barricata> barricate;
    private List<ZonaContaminata> zoneContaminate;

    public Mappa(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.agenti = new ArrayList<>();
        this.risorse = new ArrayList<>();
        this.barricate = new ArrayList<>();
        this.zoneContaminate = new ArrayList<>();
    }

    public boolean posizioneValida(int x, int y) {
        return x >= 0 &&
               x < larghezza &&
               y >= 0 &&
               y < altezza &&
               !contieneBarricata(x, y);
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

    public void rimuoviAgente(Agente agente) {
        agenti.remove(agente);
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

            int distanzaX = Math.abs(
                agente.getX() - altro.getX()
            );

            int distanzaY = Math.abs(
                agente.getY() - altro.getY()
            );

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

    public void aggiungiBarricata(Barricata barricata) {
        barricate.add(barricata);
    }

    public List<Barricata> getBarricate() {
        return barricate;
    }

    public boolean contieneBarricata(int x, int y) {

        for (Barricata barricata : barricate) {
            if (barricata.getX() == x &&
                barricata.getY() == y) {
                return true;
            }
        }

        return false;
    }

    public void aggiungiZonaContaminata(ZonaContaminata zona) {
        zoneContaminate.add(zona);
    }

    public List<ZonaContaminata> getZoneContaminate() {
        return zoneContaminate;
    }

    public boolean contieneZonaContaminata(int x, int y) {

        for (ZonaContaminata zona : zoneContaminate) {
            if (zona.getX() == x &&
                zona.getY() == y) {
                return true;
            }
        }

        return false;
    }

    public boolean posizioneOccupata(int x, int y, Agente escludi) {

        for (Agente agente : agenti) {

            if (agente == escludi || !agente.isVivo()) {
                continue;
            }

            if (agente.getX() == x && agente.getY() == y) {
                return true;
            }
        }

        return false;
    }

    public boolean posizioneValidaPerMovimento(int x, int y, Agente agente) {
        return posizioneValida(x, y) && !posizioneOccupata(x, y, agente);
    }
}
