public class Main {

    public static void main(String[] args) {

        Umano umano = new Umano(2, 3);
        Zombie zombie = new Zombie(8, 7);

        System.out.println("Umano:");
        System.out.println("Posizione: " + umano.getX() + ", " + umano.getY());
        System.out.println("Salute: " + umano.getSalute());

        System.out.println();

        System.out.println("Zombie:");
        System.out.println("Posizione: " + zombie.getX() + ", " + zombie.getY());
        System.out.println("Salute: " + zombie.getSalute());

        System.out.println();

        umano.agisci();
        zombie.agisci();
    }
}