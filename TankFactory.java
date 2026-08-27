public class TankFactory extends AgenteFactory {

    @Override
    public Agente creaAgente(int x, int y) {
        return new Tank(x, y);
    }
}