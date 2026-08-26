public class Main {

    public static void main(String[] args) {

        // Creiamo la mappa
        Mappa mappa = new Mappa(10, 10);

        // Creiamo il medico
        Umano medico = new Umano(4, 3);
        medico.setProfessione(new Medico());

        // Feriamo il medico
        medico.subisciDanno(70);

        // Gli diamo un Medikit
        medico.aggiungiOggetto(new Medikit(30));

        // Creiamo uno zombie lontano
        Zombie zombie = new Zombie(8, 3);

        // Aggiungiamo gli agenti alla mappa
        mappa.aggiungiAgente(medico);
        mappa.aggiungiAgente(zombie);

        // Creiamo il simulatore
        Simulatore simulatore = new Simulatore(mappa);

        // Aggiungiamo gli agenti al simulatore
        simulatore.aggiungiAgente(medico);
        simulatore.aggiungiAgente(zombie);

        // Eseguiamo 3 turni
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
    }
}