public interface Observer {

    void aggiorna(String evento);

    default void onMortePerMorso(Umano umano) {
    }
}
