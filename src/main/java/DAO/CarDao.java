package DAO;

import model.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.DailyReportService;

import java.sql.SQLException;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> allCars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return allCars;
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Car car;
        if ((car = checkCarInDB(brand, model, licensePlate)) != null) {
            DailyReportService.getInstance().buyCar(car);
            if (deleteCarFromDB(car) == 0) {
                session.close();
                return null;
            }
        } else {
            session.close();
            return null;
        }
        transaction.commit();
        session.close();
        return car;
    }

    public boolean addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        if (getAllCars().stream().filter(s -> )) {
            session.save(car);
        }
        transaction.commit();
        session.close();
        return true;
    }

    private Car checkCarInDB(String brand, String model, String licensePlate) {
        return (Car) session.createQuery("From Car where brand = :brand, model = :model, licensePlate = :licensePlate")
                .setParameter("brand", brand)
                .setParameter("model", model)
                .setParameter("licensePlate", licensePlate).uniqueResult();
    }

    private int deleteCarFromDB(Car car) {
        return session.createQuery("delete Car where id = :id")
                .setParameter("id", car.getId())
                .executeUpdate();
    }
}
