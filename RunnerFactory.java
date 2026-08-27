public class RunnerFactory extends AgenteFactory {

    @Override
    public Agente creaAgente(int x, int y) {
        return new Runner(x, y);
    }
}