public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        Umano umano = new Umano(4, 3);

        umano.setProfessione(new Medico());

        mappa.aggiungiAgente(umano);

        umano.getProfessione().agisci(umano, mappa);
    }
}