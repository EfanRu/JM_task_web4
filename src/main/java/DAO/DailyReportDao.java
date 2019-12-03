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
        try {
            session.beginTransaction();
            return session.createQuery("FROM DailyReport").list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public DailyReport getLastReport() {
        try {
            List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
            return dailyReports.get(dailyReports.size() - 1);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void newDay(Long earnings, Long soldCars) {
        try {
            session.beginTransaction();
            session.save(new DailyReport(earnings, soldCars));
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete() {
        try {
            session.beginTransaction();
            List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
            Query q1 = session.createQuery("delete from DailyReport");
            Query q2 = session.createQuery("delete from Car");
            q1.executeUpdate();
            q2.executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
