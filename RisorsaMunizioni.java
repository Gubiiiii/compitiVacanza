public class RisorsaMunizioni extends Risorsa {

    private int quantita;

    public RisorsaMunizioni(int x, int y, int quantita) {
        super(x, y);
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }
}