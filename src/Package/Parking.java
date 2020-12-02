package Package;

import java.util.ArrayList;

public class Parking {
    private int numOfFreeParkingPlaces;

    private int numOfParkingPlaces;

    private int numOfLORRYParkingPlaces;
    private int numOfPASSParkingPlaces;

    private int numOfFreeLORRYParkingPlaces;
    private int numOfFreePASSParkingPlaces;

    private ArrayList<Integer> arrOfParkingPlaces = new ArrayList<>();//доп список парк мест
    // 1 - место занято легrовой, 0 - легковое место свободно,
    // 2 - место занято грузовой, 20 - грузовое место свободно, 22- грузовая машина заняла 2 легковых места


    public Parking(int pasParkingPlaces, int lorryParkingPlace) throws Exception {
        this.numOfParkingPlaces = pasParkingPlaces + lorryParkingPlace;
        if(numOfParkingPlaces<1){
            throw new Exception("количество парковочных мест не модет быть < 1");
        }
        else {
            this.numOfLORRYParkingPlaces = lorryParkingPlace;
            this.numOfPASSParkingPlaces = pasParkingPlaces;

            this.numOfFreeParkingPlaces = pasParkingPlaces+lorryParkingPlace;

            this.numOfFreeLORRYParkingPlaces = lorryParkingPlace;
            this.numOfFreePASSParkingPlaces = pasParkingPlaces;
        }
    }

    public void pasPlaceTaked(){
        numOfFreePASSParkingPlaces--;
        numOfFreeParkingPlaces--;
    }

    public void lorryPlaceTaked(){
        numOfFreeLORRYParkingPlaces--;
        numOfFreeParkingPlaces--;
    }

    public void pasPlaceFree(){
        numOfFreePASSParkingPlaces++;
        numOfFreeParkingPlaces++;
    }

    public void lorryPlaceFree(){
        numOfFreeLORRYParkingPlaces++;
        numOfFreeParkingPlaces++;
    }

    public int getNumOfFreeParkingPlaces() { return numOfFreeParkingPlaces; }

    public int getNumOfFreeLORRYParkingPlaces() { return numOfFreeLORRYParkingPlaces; }

    public int getNumOfFreePASSParkingPlaces() { return numOfFreePASSParkingPlaces; }

    public int getNumOfLORRYParkingPlaces() {
        return numOfLORRYParkingPlaces;
    }

    public int getNumOfPASSParkingPlaces() {
        return numOfPASSParkingPlaces;
    }

    public ArrayList<Integer> getArrOfParkingPlaces() {
        return arrOfParkingPlaces;
    }

    public void fillArrOfPP(){
        arrOfParkingPlaces.clear();
        for (int i = 0; i <numOfFreeLORRYParkingPlaces ; i++) {
            arrOfParkingPlaces.add(20);
        }
        for (int i = 0; i <numOfFreePASSParkingPlaces ; i++) {
            arrOfParkingPlaces.add(0);
        }
    }

}
