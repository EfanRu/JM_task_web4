package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        String brand = req.getParameter("brand");
        String licensePlate = req.getParameter("licensePlate");

        Car car = CarService.getInstance().buyCar(brand, model, licensePlate);
        Gson gson = new Gson();
        String json = gson.toJson(car);

        if (car != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
