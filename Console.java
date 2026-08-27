public class Console implements Observer {

    @Override
    public void aggiorna(String evento) {
        System.out.println("[OBSERVER] " + evento);
    }
}