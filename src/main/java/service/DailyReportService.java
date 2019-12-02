package service;

import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private Long earnings = 0L;
    private Long soldCars = 0L;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void buyCar(Car car) {
        earnings += car.getPrice();
        soldCars++;
    }

    public void newDay() {
        new DailyReportDao(sessionFactory.openSession()).newDay(earnings, soldCars);
        earnings = 0L;
        soldCars = 0L;
    }

    public void delete() {
        new DailyReportDao(sessionFactory.openSession()).delete();
    }
}
