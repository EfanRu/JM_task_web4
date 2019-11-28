package DAO;

import org.hibernate.Session;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

}
