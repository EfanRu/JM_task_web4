package service;

import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

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
        return null;
    }

    public void buyCar(Car car) {
        new DailyReportDao(sessionFactory.openSession()).buyCar(car);
    }
}
