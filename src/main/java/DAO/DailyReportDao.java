package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;
    private Long earnings;
    private Long soldCars;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    protected void buyCar(Car car) {
        earnings += car.getPrice();
        soldCars++;
    }
}
