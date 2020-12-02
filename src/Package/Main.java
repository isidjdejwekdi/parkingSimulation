package Package;

import kotlin.jvm.JvmOverloads;

import java.util.*;

public class Main {
    static ArrayList<Integer> unicID = new ArrayList<>();
    static ArrayList<Car> waitingCars = new ArrayList<>();
    static ArrayList<Car> carsOnParking = new ArrayList<>();
    static ArrayList<Car> leftingCars = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Scanner command = new Scanner(System.in);
        //scanner
        //ArrayList<Object> list = new ArrayList<>(Arrays.asList(0,new Car(10,111, Car.Type.PASSANGER)));

        System.out.println("Введите количество легковых и грузовых парковочных мест соответсвенно: ");
        Parking parking = new Parking(scanner.nextInt(),scanner1.nextInt());

        //parking.setParkingPlaces(scanner.nextInt());
        System.out.println("Список команд: z-сделать ход, x- получить информацию о парковочных местах, c-очистить парковку от машин");
        //HashMap<String, String> map = new HashMap<String, String>();
        //ArrayList<HashMap> list = new ArrayList<>();
        //list.add(map);
        parking.fillArrOfPP();
        createWaitingCars(parking.getNumOfPASSParkingPlaces(),parking.getNumOfLORRYParkingPlaces());

        while (true){//начинаем ходы
            //сделать ход
            if(parking.getNumOfFreeParkingPlaces() ==0) {
                System.out.println("Парковка забита");
                System.out.println("Место будет свободно через " + whenFree(carsOnParking)+" ходов");
            }

            System.out.println("Введите команду");

                switch (command.nextLine()) {
                    case ("z")://ход
                        while (parking.getNumOfFreeParkingPlaces()>0){//машины заехжают на свободные места

                            Car firstLORcar = getFirstLORcar(waitingCars);
                            Car firstPAScar = getFirstPAScar(waitingCars);
                            Car firstWcar = waitingCars.get(0);

                                switch(firstWcar.getType()){

                                    case PASSANGER:
                                        if(parking.getArrOfParkingPlaces().contains(0)){//легковая занимает место
                                            System.out.println("легковая " + "машина id " + firstWcar.getId() + " заехжает на парковку");
                                            carsOnParking.add(firstWcar);//занимает место
                                            firstWcar.setIndexOnParking(parking.getArrOfParkingPlaces().indexOf(0));//запоминаем индекс места на которое заехала машина для связки списков carsOnParking и arrOfParkingPlaces
                                            waitingCars.remove(firstWcar);//больше не ждет
                                            parking.pasPlaceTaked();//место было занято
                                            parking.getArrOfParkingPlaces().set(parking.getArrOfParkingPlaces().indexOf(0), 1);//выставляем значение занятого места в 1 (занято легковой)
                                        }
                                        else {//легковая пропускает грузовик

                                            assert firstLORcar != null;
                                            System.out.println("грузовая машина id "+ firstLORcar.getId()+" заехжает на парковку");
                                            carsOnParking.add(firstLORcar);
                                            firstLORcar.setIndexOnParking(parking.getArrOfParkingPlaces().indexOf(20));//запоминаем индекс места на которое заехала машина

                                            waitingCars.remove(firstLORcar);
                                            parking.lorryPlaceTaked();
                                            parking.getArrOfParkingPlaces().set(parking.getArrOfParkingPlaces().indexOf(20), 2);
                                        }

                                        break;
                                    case LORRY:
                                        if(parking.getArrOfParkingPlaces().contains(20)){//грузовик занимает место
                                            System.out.println("грузовая " + "машина id " + firstWcar.getId() + " заехжает на парковку");
                                            carsOnParking.add(firstWcar);
                                            firstWcar.setIndexOnParking(parking.getArrOfParkingPlaces().indexOf(20));//запоминаем индекс места на которое заехала машина

                                            waitingCars.remove(firstWcar);
                                            parking.lorryPlaceTaked();
                                            parking.getArrOfParkingPlaces().set(parking.getArrOfParkingPlaces().indexOf(20), 2);
                                        }
                                        else if (whenContain(parking.getArrOfParkingPlaces()) > -1){//ждущий грузовик занимает 2 легковых места если они есть
                                            System.out.println("грузовая " + "машина id " + firstWcar.getId() + " заехжает на парковку и занимает 2 легковых места");

                                            carsOnParking.add(firstWcar);
                                            firstWcar.setSpecialIndexOnParking(whenContain(parking.getArrOfParkingPlaces()));//запоминаем индекс первого места из двух на которое заехала машина

                                            waitingCars.remove(firstWcar);

                                            int f = whenContain(parking.getArrOfParkingPlaces());

                                            parking.getArrOfParkingPlaces().set(f, 22);
                                            parking.getArrOfParkingPlaces().set(f+1, 22);

                                            parking.pasPlaceTaked();
                                            parking.pasPlaceTaked();

                                        }
                                        else {//грузовик пропускает легковую
                                            assert firstPAScar != null;
                                            System.out.println("легковая машина id "+ firstPAScar.getId()+" заехжает на парковку");
                                            carsOnParking.add(firstPAScar);
                                            firstPAScar.setIndexOnParking(parking.getArrOfParkingPlaces().indexOf(0));//запоминаем индекс места на которое заехала машина

                                            waitingCars.remove(firstPAScar);
                                            parking.pasPlaceTaked();
                                            parking.getArrOfParkingPlaces().set(parking.getArrOfParkingPlaces().indexOf(0), 1);
                                        }
                                        break;

                                    default:
                                }

                        }

                        for (Car el: carsOnParking){//каждая машина делает ход
                            el.doStep();

                            if (el.getMoves()<=0) {//машина должна уехать на этом ходу
                                leftingCars.add(el);//запоминаем индексы уезжающих машин
                                System.out.println((el.getType() == Car.Type.PASSANGER ? "легковая " : "грузовая ")+"машина id = "+el.getId()+" уезжает с парковки");
                            }
                        }

                        for (Car leftC: leftingCars) {//машины уезжают

                            if(leftC.getSpecialIndexOnParking()>-1){//уезжает машина занимавшая 2 места
                                int index = leftC.getSpecialIndexOnParking();
                                parking.getArrOfParkingPlaces().set(index, 0);
                                parking.getArrOfParkingPlaces().set(index+1, 0);
                                createWaitingCars(parking.getNumOfPASSParkingPlaces() / 3, parking.getNumOfLORRYParkingPlaces() / 3);
                                parking.pasPlaceFree();
                                parking.pasPlaceFree();
                                carsOnParking.remove(leftC);
                            }
                            else {
                                parking.getArrOfParkingPlaces().set(leftC.getIndexOnParking(), (leftC.getType() == Car.Type.PASSANGER) ? 0 : 20);
                                createWaitingCars(parking.getNumOfPASSParkingPlaces() / 3, parking.getNumOfLORRYParkingPlaces() / 3);
                                if ((leftC.getType() == Car.Type.PASSANGER)) {
                                    parking.pasPlaceFree();
                                } else {
                                    parking.lorryPlaceFree();
                                }
                                carsOnParking.remove(leftC);
                            }
                        }
                        leftingCars.clear();

                        for (Car el: carsOnParking )
                            System.out.println(el);

                        break;

                    case ("x")://узнать
                        if(carsOnParking.isEmpty())
                            System.out.println("парковка пуста");
                        else {
                            int numOfPASTakedPlaces = parking.getNumOfPASSParkingPlaces() - parking.getNumOfFreePASSParkingPlaces();
                            int numOfLORTakedPlaces = parking.getNumOfLORRYParkingPlaces() - parking.getNumOfFreeLORRYParkingPlaces();

                            System.out.println("Легковых мест занято: "+ numOfPASTakedPlaces + " Свободно: " + parking.getNumOfFreePASSParkingPlaces() );
                            System.out.println("Грузовых мест занято: "+ numOfLORTakedPlaces + " Свободно: " + parking.getNumOfFreeLORRYParkingPlaces() );

                            System.out.println("место появится через " + whenFree(carsOnParking) + " ходов");

                            for (Car car: carsOnParking) {
                                if (car.getSpecialIndexOnParking()>-1)
                                    System.out.println("грузовая машина с id " + car.getId() + " занимает два легковых места " + "ходов осталось: " + car.getMoves());
                                else
                                System.out.println((car.getType() == Car.Type.PASSANGER ? "легковая ": "грузовая ") + "машина с id " + car.getId() + " занимает " +
                                        (car.getType() == Car.Type.PASSANGER ? "легковое ": "грузовое ") + "место, " + "ходов осталось: " + car.getMoves());
                            }
                        }

                        break;
                    case("c")://очистить
                        for (int i = 0; i <carsOnParking.size() ; i++) {
                            if (carsOnParking.get(i).getType() == Car.Type.PASSANGER)
                                parking.pasPlaceFree();
                            else if (carsOnParking.get(i).getSpecialIndexOnParking()>-1) {
                                parking.pasPlaceFree();
                                parking.pasPlaceFree();
                            }
                            else
                                parking.lorryPlaceFree();
                        }
                        carsOnParking.clear();
                        createWaitingCars(parking.getNumOfPASSParkingPlaces(), parking.getNumOfLORRYParkingPlaces());
                        parking.fillArrOfPP();
                        System.out.println("Парковка очищена");
                        break;
                    default:
                        System.out.println("Неизвестная команда");
                        System.out.println("Список команд: z-сделать ход, x- получить информацию о парковочных местах, c-очистить парковку от машин");
                        break;
                }
        }
    }

    public static void createWaitingCars(int pValue, int lValue){
        for (int i = 0; i <pValue ; i++) {//создаем новые ждущие машины
            int random_number1 = 1000 + (int) (Math.random() * 9000);
            int random_number2 = 2 + (int) (Math.random() * 10);
            while (unicID.contains(random_number1)) {
                random_number1 = 1000 + (int) (Math.random() * 9000);
            }
            unicID.add(random_number1);
            waitingCars.add(new Car (random_number2, random_number1, Car.Type.PASSANGER));
        }

        for (int i = 0; i <lValue ; i++) {//создаем новые ждущие машины
            int random_number1 = 1000 + (int) (Math.random() * 9000);
            int random_number2 = 2 + (int) (Math.random() * 10);
            while (unicID.contains(random_number1)) {
                random_number1 = 1000 + (int) (Math.random() * 9000);
            }
            unicID.add(random_number1);
            waitingCars.add(new Car (random_number2, random_number1, Car.Type.LORRY));
        }
    }
    //@JvmOverloads
    //public static void createWaitingCars()
    //{
    //    waitingCars.add(new Car (4, 2001, Car.Type.LORRY));
    //    waitingCars.add(new Car (4, 2002, Car.Type.LORRY));
    //    waitingCars.add(new Car (4, 2003, Car.Type.LORRY));
//
    //    waitingCars.add(new Car (4, 1001, Car.Type.PASSANGER));
    //    waitingCars.add(new Car (3, 1002, Car.Type.PASSANGER));
    //    waitingCars.add(new Car (1, 1003, Car.Type.PASSANGER));
    //    waitingCars.add(new Car (1, 1004, Car.Type.PASSANGER));
//
    //    waitingCars.add(new Car (10, 777, Car.Type.LORRY));
//
//
    //}

    public static int whenFree(ArrayList<Car> cars){
        ArrayList<Integer> numOfMoves = new ArrayList<>();
        for (Car el : cars) {
            numOfMoves.add(el.getMoves());
        }
        int buf = Collections.min(numOfMoves);
        numOfMoves.clear();

        return buf;
    }

    public static int whenContain(ArrayList<Integer> list){//возвращает индекс первого свободного легкового места из двух для груз машины
        int value = -1;

        for (int i = 0; i <list.size()-1 ; i++) {
            if((list.get(i)==0) && (list.get(i+1)==0)){
                value = i;
                break;
            }
        }
        return value;
    }

    public static Car getFirstPAScar(ArrayList<Car> list){

        for (Car el: list) {
            if (el.getType() == Car.Type.PASSANGER)
                return el;
        }
        return null;
    }

    public static Car getFirstLORcar(ArrayList<Car> list){
        for (Car el: list) {
            if (el.getType() == Car.Type.LORRY)
                return el;
        }
        return null;
    }


}

