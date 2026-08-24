public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        Runner runner = new Runner(2, 3);
        Tank tank = new Tank(7, 3);

        mappa.aggiungiAgente(runner);
        mappa.aggiungiAgente(tank);

        Simulatore simulatore = new Simulatore(mappa);

        simulatore.aggiungiAgente(runner);
        simulatore.aggiungiAgente(tank);

        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
        simulatore.eseguiTurno();
    }
}