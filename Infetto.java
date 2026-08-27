public class Infetto implements StatoUmano {

    @Override
    public void agisci(Umano umano, Mappa mappa) {

        System.out.println(
            "L'umano è infetto e sta peggiorando..."
        );
    }
}