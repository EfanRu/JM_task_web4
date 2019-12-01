package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.DailyReportService;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
       Transaction transaction = session.beginTransaction();
       List<Car> allCars = session.createQuery("Select * Car").list();
       transaction.commit();
       session.close();
       return allCars;
    }


    public boolean buyCar(String brand, String model, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Car c;

        if ((c = getCarFromDB(brand, model, licensePlate)) != null) {
            session.delete(c);
            DailyReportService.getInstance().buyCar(c);
            transaction.commit();
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }
    }

    public Car getCarFromDB(String brand, String model, String licensePlate) {
        Query q = session.createQuery("select * from Car where brand = :brand, model = :model, licensePlate = :licensePlate");
        q.setParameter("brand", brand);
        q.setParameter("model", model);
        q.setParameter("licensePlate", licensePlate);
        return (Car) q.uniqueResult();
    }

    public boolean addCar(String brand, String model, String licensePlate, String price) {
        if (getAllCars().stream().filter(c -> c.getModel().equals(model)).count() >= 10) {
            return false;
        }
        Transaction transaction = session.beginTransaction();
        Car c = new Car(brand, model, licensePlate, Long.parseLong(price));
        session.update(c);
        transaction.commit();
        session.close();
        return true;
    }
}
