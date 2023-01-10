package Sem4.BanqueteJavali;

public class Javali {
    private int id;
    private int cozinheiro;

    public Javali(int id, int cozinheiro){
        this.id = id;
        this.cozinheiro = cozinheiro;
    }

    @Override
    public String toString() {
        return "Javali id: " + id + " Cozinheiro " + cozinheiro;
    }

}
