package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

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

    public DailyReport getLastReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        session.close();
        return dailyReports.get(dailyReports.size() - 1);
    }

    public void newDay(Long earnings, Long soldCars) {
        session.save(new DailyReport(earnings, soldCars));
        session.close();
    }

    public void delete() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
//        for (DailyReport dr : dailyReports) {
//            session.delete(dr);
        Query q = session.createQuery("delete from DailyReport");
        q.executeUpdate();
//        }
        session.close();
    }
}
