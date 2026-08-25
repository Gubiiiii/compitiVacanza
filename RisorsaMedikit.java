public class RisorsaMedikit extends Risorsa {

    private int cura;

    public RisorsaMedikit(int x, int y, int cura) {
        super(x, y);
        this.cura = cura;
    }

    public int getCura() {
        return cura;
    }
}