package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        return new CarDao(sessionFactory.openSession()).buyCar(brand, model, licensePlate);
    }

    public boolean addCar(Car car) {
        return new CarDao(sessionFactory.openSession()).addCar(car);
    }
}
