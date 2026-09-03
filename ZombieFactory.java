public class ZombieFactory extends AgenteFactory {

    @Override
    public Agente creaAgente(int x, int y) {

        return new Zombie(x, y);
    }

    public Zombie creaDaMorso(int x, int y, Zombie zombieContagio) {

        if (zombieContagio instanceof Runner) {
            return (Zombie) new RunnerFactory().creaAgente(x, y);
        }

        if (zombieContagio instanceof Tank) {
            return (Zombie) new TankFactory().creaAgente(x, y);
        }

        return (Zombie) creaAgente(x, y);
    }
}
