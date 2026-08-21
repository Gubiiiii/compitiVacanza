public class Main {

    public static void main(String[] args) {

        Mappa mappa = new Mappa(10, 10);

        Umano umano = new Umano(2, 3);
        Zombie zombie = new Zombie(8, 3);

        System.out.println("POSIZIONI INIZIALI");

        System.out.println(
            "Umano: (" + umano.getX() + ", " + umano.getY() + ")"
        );

        System.out.println(
            "Zombie: (" + zombie.getX() + ", " + zombie.getY() + ")"
        );

        System.out.println();

        umano.agisci(mappa);
        zombie.agisci(mappa);

        System.out.println();

        System.out.println("POSIZIONI DOPO UN TURNO");

        System.out.println(
            "Umano: (" + umano.getX() + ", " + umano.getY() + ")"
        );

        System.out.println(
            "Zombie: (" + zombie.getX() + ", " + zombie.getY() + ")"
        );
    }
}