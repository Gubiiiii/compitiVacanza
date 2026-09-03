public class Medico implements Professione {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (umano.getSalute() < 100) {

            if (usaMedikit(umano, umano)) {
                return;
            }

            System.out.println(
                "Il medico è ferito ma non ha Medikit!"
            );
        }

        Umano ferito = umano.trovaFeritoPiuVicino(mappa);

        if (ferito == null) {
            System.out.println(
                "Il medico non trova feriti da curare."
            );
            return;
        }

        if (umano.distanzaDa(ferito) <= 1) {

            if (usaMedikit(umano, ferito)) {
                System.out.println(
                    "Il medico cura il ferito piu vicino."
                );
            } else {
                System.out.println(
                    "Il medico non ha Medikit per curare il ferito!"
                );
            }

            return;
        }

        umano.muoviVerso(ferito, mappa);

        System.out.println(
            "Il medico va verso il ferito piu vicino."
        );
    }

    private boolean usaMedikit(Umano medico, Umano paziente) {

        Medikit medikit = null;

        for (Oggetto oggetto : medico.getInventario()) {

            if (oggetto instanceof Medikit) {
                medikit = (Medikit) oggetto;
                break;
            }
        }

        if (medikit == null) {
            return false;
        }

        paziente.cura(medikit.getCura());

        medico.getInventario().remove(medikit);

        System.out.println(
            "Il Medikit è stato utilizzato."
        );

        return true;
    }
}
