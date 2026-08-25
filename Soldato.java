public class Soldato implements Professione {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        System.out.println(
            "Il soldato è pronto a combattere."
        );
    }
}