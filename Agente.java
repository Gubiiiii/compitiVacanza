public abstract class Agente {

    protected int x;
    protected int y;
    protected int salute;
    protected int velocita;

    public Agente(int x, int y, int salute, int velocita) {
        this.x = x;
        this.y = y;
        this.salute = salute;
        this.velocita = velocita;
    }

    public abstract void agisci();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSalute() {
        return salute;
    }

    public int getVelocita() {
        return velocita;
    }
}