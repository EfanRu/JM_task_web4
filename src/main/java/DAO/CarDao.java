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

    public List<Car> getAllCarsFromDB() throws RuntimeException {
        return session.createQuery("FROM Car").list();
    }

    public List<Car> getAllCars() {
        try {
            Transaction transaction = session.beginTransaction();
            List<Car> cars = getAllCarsFromDB();
            transaction.commit();
            return cars;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public Car buyCar(String brand, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Car car;
        if ((car = checkCarInDB(brand, licensePlate)) != null) {
            System.out.println(car);

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
        try {
            Transaction transaction = session.beginTransaction();
            List<Car> cars = getAllCarsFromDB();
            if (cars != null
                    && cars.stream().filter(s -> (s.getBrand()).equals(car.getBrand())).count() >= 10) {
                return false;
            }
            session.save(car);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    private Car checkCarInDB(String brand, String licensePlate) {
        return (Car) session.createQuery("From Car where brand = :brand and licensePlate = :licensePlate")
                .setParameter("brand", brand)
                .setParameter("licensePlate", licensePlate).uniqueResult();
    }

    private int deleteCarFromDB(Car car) {
        return session.createQuery("delete Car where id = :id")
                .setParameter("id", car.getId())
                .executeUpdate();
    }
}
