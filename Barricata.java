public class Barricata extends ElementoAmbientale {

    private int resistenza;

    public Barricata(int x, int y, int resistenza) {
        super(x, y);
        this.resistenza = resistenza;
    }

    public int getResistenza() {
        return resistenza;
    }
}
