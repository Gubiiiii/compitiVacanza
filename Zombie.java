public class Zombie extends Agente {

    public Zombie(int x, int y) {
        super(x, y, 80, 1);
    }

    @Override
    public void agisci() {
        System.out.println("Lo zombie cerca un umano.");
    }
}