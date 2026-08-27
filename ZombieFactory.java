public class ZombieFactory extends AgenteFactory {

    @Override
    public Agente creaAgente(int x, int y) {

        return new Zombie(x, y);
    }
}