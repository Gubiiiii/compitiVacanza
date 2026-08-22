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

        // FASE 1: PERCEZIONE
        for (Agente agente : agenti) {
            agente.percepisci(mappa);
        }

        // FASE 2: AZIONE
        for (Agente agente : agenti) {
            agente.agisci(mappa);
        }

        // FASE 3: COLLISIONI
        controllaCollisioni();

        System.out.println();
    }

    private void controllaCollisioni() {

        for (int i = 0; i < agenti.size(); i++) {

            for (int j = i + 1; j < agenti.size(); j++) {

                Agente primo = agenti.get(i);
                Agente secondo = agenti.get(j);

                if (primo.siTrovaNellaStessaPosizione(secondo)) {

                    System.out.println(
                        "COLLISIONE tra " +
                        primo.getClass().getSimpleName() +
                        " e " +
                        secondo.getClass().getSimpleName()
                    );

                    if (primo instanceof Zombie && secondo instanceof Umano) {
                        ((Zombie) primo).attacca((Umano) secondo);
                    }

                    if (primo instanceof Umano && secondo instanceof Zombie) {
                        ((Zombie) secondo).attacca((Umano) primo);
                    }
                }
            }
        }
    }
}