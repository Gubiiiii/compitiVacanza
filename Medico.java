public class Medico implements Professione {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (umano.getSalute() >= 100) {

            System.out.println(
                "Il medico non ha bisogno di curarsi."
            );

            return;
        }

        Medikit medikit = null;

        for (Oggetto oggetto : umano.getInventario()) {

            if (oggetto instanceof Medikit) {
                medikit = (Medikit) oggetto;
                break;
            }
        }

        if (medikit == null) {

            System.out.println(
                "Il medico non ha Medikit!"
            );

            return;
        }

        umano.cura(medikit.getCura());

        umano.getInventario().remove(medikit);

        System.out.println(
            "Il Medikit è stato utilizzato."
        );
    }
}