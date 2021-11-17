package packaging.servlets;

import packaging.DAO.CustomerDAO;
import packaging.entity.Customer;
import packaging.service.CustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private Connection connection;
    private CustomerService customerService;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        this.customerDAO = new CustomerService(connection);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsenName = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            Class.forName(driverClassName);
            connection = DriverManager.getConnection(dbUrl, dbUsenName, dbPassword);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
//        Integer id = Integer.parseInt(req.getParameter("id"));
//        Optional<Customer> customer = customerDAO.find(id);
//        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/home.jsp");
//        req.setAttribute("customer", customer);
//        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String color = req.getParameter("color");
//        Cookie colorCookie = new Cookie("color", color);
//        resp.addCookie(colorCookie);
        resp.sendRedirect(req.getContextPath() + "/home");

//        String email = req.getParameter("email");
//        String phone = req.getParameter("phone");
//        String address = req.getParameter("address");
//        int id = Integer.parseInt(req.getParameter("id"));
//
//        Customer customer = new Customer(id, email, phone, address);
//        this.customerDAO = new CustomerService(connection);
//        customerDAO.update(customer);
    }
}