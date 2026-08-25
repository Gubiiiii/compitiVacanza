public class Munizioni extends Oggetto {

    private int quantita;

    public Munizioni(int quantita) {
        super("Munizioni");
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void usaMunizione() {
        quantita--;
    }
}