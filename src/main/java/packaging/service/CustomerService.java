package packaging.service;

import packaging.DAO.CustomerDAO;
import packaging.entity.Customer;
import packaging.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService implements CustomerDAO {

    private final String SQL_SELECT_ALL = "SELECT * from customersone";
    private final String sqlInsert = "INSERT INTO customersone (login, password, role, email, phone, address) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SQL_INSERT_INTO = "INSERT INTO customersone (login, password, role, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM customersone WHERE id = ?";
    private final String SQL_UPDATE = "UPDATE customersone SET email = ?, phone = ? address = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM customersone WHERE id = ?";

    private Connection connection;

    public CustomerService(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<Customer> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                return Optional.of(new Customer(id, login, password, role, email, phone, address));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean save(Customer model) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO);
            ps.setString(1, model.getLogin());
            ps.setString(2, model.getPassword());
            ps.setString(3, UserRole.STUDENT.toString());
            ps.setString(4, model.getEmail());
            ps.setString(5, model.getPhone());
            ps.setString(6, model.getAddress());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer model) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setString(1, model.getEmail());
            ps.setString(2, model.getPhone());
            ps.setString(3, model.getAddress());
            ps.setInt(4, model.getId());

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(Integer id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setInt(1, id);
            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Customer> findAll() {
        try {
            List<Customer> customers = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                Customer customer = new Customer(id, login, password, role, email, phone, address);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Customer> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public boolean isExist(String login, String password) {
        try {
            List<Customer> customers = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                String log = resultSet.getString("login");
                String pass = resultSet.getString("password");

                Customer customer = new Customer(log, pass);
                customers.add(customer);
            }
            for (Customer customer : customers) {
                if (customer.getLogin().equals(login) &&
                        customer.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}