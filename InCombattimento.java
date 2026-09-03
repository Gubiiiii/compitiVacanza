public class InCombattimento implements StatoUmano {

    private Zombie bersaglio;

    public InCombattimento(Zombie bersaglio) {
        this.bersaglio = bersaglio;
    }

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        if (!bersaglio.isVivo()) {

            System.out.println("Lo zombie è morto!");
            return;
        }

        int distanza = umano.distanzaDa(bersaglio);

        if (distanza <= 3) {

            try {

                Munizioni munizioni = null;

                // Cerchiamo le munizioni nell'inventario
                for (Oggetto oggetto : umano.getInventario()) {

                    if (oggetto instanceof Munizioni) {
                        munizioni = (Munizioni) oggetto;
                        break;
                    }
                }

                // Controlliamo se ci sono munizioni
                if (munizioni == null) {

                    throw new NoAmmoException(
                        "Il soldato non ha munizioni!"
                    );
                }

                if (munizioni.getQuantita() <= 0) {

                    throw new NoAmmoException(
                        "Il soldato ha finito le munizioni!"
                    );
                }

                // Consuma una munizione
                munizioni.usaMunizione();

                System.out.println(
                    "Il soldato usa una munizione!"
                );

                // Attacca lo zombie
                umano.attacca(bersaglio);

                System.out.println(
                    "Munizioni rimaste: "
                    + munizioni.getQuantita()
                );

            } catch (NoAmmoException e) {

                System.out.println(
                    "ERRORE: " + e.getMessage()
                );
            }

        } else {

            umano.muoviVerso(bersaglio, mappa);

            System.out.println(
                "Il soldato avanza verso lo zombie."
            );
        }
    }

    public Zombie getBersaglio() {
        return bersaglio;
    }
}
