public class UmanoFactory extends AgenteFactory {

    @Override
    public Agente creaAgente(int x, int y) {

        return new Umano(x, y);
    }
}