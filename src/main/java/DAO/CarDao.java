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
            session.beginTransaction();
            return getAllCarsFromDB();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public Car buyCar(String brand, String licensePlate) {
        try {
            session.beginTransaction();
            Car car;
            if ((car = checkCarInDB(brand, licensePlate)) != null) {
                DailyReportService.getInstance().buyCar(car);
                if (deleteCarFromDB(car) == 0) {
                    session.getTransaction().rollback();
                    return null;
                }
            } else {
                session.getTransaction().rollback();
                return null;
            }
            session.getTransaction().commit();
            return car;
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean addCar(Car car) {
        try {
            session.beginTransaction();
            List<Car> cars = getAllCarsFromDB();
            if (cars != null
                    && cars.stream().filter(s -> (s.getBrand()).equals(car.getBrand())).count() >= 10) {
                session.getTransaction().rollback();
                return false;
            }
            session.save(car);
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    private Car checkCarInDB(String brand, String licensePlate) throws RuntimeException {
        return (Car) session.createQuery("From Car where brand = :brand and licensePlate = :licensePlate")
                .setParameter("brand", brand)
                .setParameter("licensePlate", licensePlate).uniqueResult();
    }

    private int deleteCarFromDB(Car car) throws RuntimeException {
        return session.createQuery("delete Car where id = :id")
                .setParameter("id", car.getId())
                .executeUpdate();
    }
}
