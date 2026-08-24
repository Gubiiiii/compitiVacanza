public class Medico implements Professione {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        System.out.println("Il medico cerca feriti da curare.");
    }
}