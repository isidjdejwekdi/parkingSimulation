package Package;

public class Car {
    private int moves;
    private int id;
    private int indexOnParking;
    private int specialIndexOnParking = -1;

    enum Type {PASSANGER, LORRY};
    private Type type;

    public Car(int moves, int id, Type type ) {
        this.moves = moves;
        this.id = id;
        this.type = type;
    }

    public void setIndexOnParking(int indexOnParking) {
        this.indexOnParking = indexOnParking;
    }

    public int getIndexOnParking() {
        return indexOnParking;
    }

    public void setSpecialIndexOnParking(int specialIndexOnParking) {
        this.specialIndexOnParking = specialIndexOnParking;
    }

    public int getSpecialIndexOnParking() {
        return specialIndexOnParking;
    }

    public int getId() {
        return id;
    }

    public int getMoves() {
        return moves;
    }

    public Type getType() {
        return type;
    }

    public void doStep()
    {
        this.moves--;
    }

    @Override
    public String toString() {
        return  (type == Type.PASSANGER ? "легковой " : "грузовой ")+"машине с id "+ id + " осталось ходов: " + moves;
    }
}
