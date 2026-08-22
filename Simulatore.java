import java.util.ArrayList;
import java.util.List;

public class Simulatore {

    private Mappa mappa;
    private List<Agente> agenti;
    private int tick;

    public Simulatore(Mappa mappa) {
        this.mappa = mappa;
        this.agenti = new ArrayList<>();
        this.tick = 0;
    }

    public void aggiungiAgente(Agente agente) {
        agenti.add(agente);
    }

    public void eseguiTurno() {

        tick++;

        System.out.println("=== TICK " + tick + " ===");

        for (Agente agente : agenti) {
            agente.agisci(mappa);
        }

        System.out.println();
    }
}