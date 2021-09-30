package mx.edu.utez.model;

import mx.edu.utez.util.ConnectionMysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCustomer {

    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public boolean saveCustomers(Customer customer, boolean create){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            if(create){
                String query = "INSERT INTO customers(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2," +
                        "city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setInt(1, customer.getCustomerNumber());
                pstm.setString(2, customer.getCustomerName());
                pstm.setString(3, customer.getContactLastName());
                pstm.setString(4, customer.getContactFirstName());
                pstm.setString(5, customer.getPhone());
                pstm.setString(6, customer.getAddressLine1());
                pstm.setString(7, customer.getAddressLine2());
                pstm.setString(8, customer.getCity());
                pstm.setString(9, customer.getState());
                pstm.setString(10, customer.getPostalCode());
                pstm.setString(11, customer.getCountry());
                pstm.setInt(12, customer.getSalesRepEmployeeNumber());
                pstm.setInt(13, customer.getCreditLimit());
                state = pstm.executeUpdate() == 1;
            }else{
                String query = "UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName = ?, phone = ?, addressLine1 = ?, " +
                        "addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ?" +
                        "WHERE customerNumber = ?";
                pstm = con.prepareStatement(query);
                pstm.setInt(13, customer.getCustomerNumber());
                pstm.setString(1, customer.getCustomerName());
                pstm.setString(2, customer.getContactLastName());
                pstm.setString(3, customer.getContactFirstName());
                pstm.setString(4, customer.getPhone());
                pstm.setString(5, customer.getAddressLine1());
                pstm.setString(6, customer.getAddressLine2());
                pstm.setString(7, customer.getCity());
                pstm.setString(8, customer.getState());
                pstm.setString(9, customer.getPostalCode());
                pstm.setString(10, customer.getCountry());
                pstm.setInt(11, customer.getSalesRepEmployeeNumber());
                pstm.setInt(12, customer.getCreditLimit());
                state = pstm.executeUpdate() == 1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }


    public List<Customer> findAll(){
        List<Customer> listEmployees = new ArrayList<>();

        try {
            con = ConnectionMysql.getConnection();
            String query = "select customers.customerNumber,customers.customerName,customers.contactLastName,customers.contactFirstName,customers.phone,customers.addressLine1," +
                    "customers.addressLine2,customers.city,customers.state,customers.postalCode,customers.country," +
                    "customers.salesRepEmployeeNumber,customers.creditLimit from customers";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getInt("creditLimit"));
                listEmployees.add(customer);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return listEmployees;
    }

    public Customer findById(int customerNumber){
        Customer customer = null;
        try{
            con = ConnectionMysql.getConnection();
            String query = "SELECT * FROM customers WHERE customerNumber = ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, customerNumber);
            rs = pstm.executeQuery();
            if(rs.next()){
                customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getInt("creditLimit"));
            }
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return customer;
    }





    public boolean delete(int customerNumber){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            String query = "DELETE FROM customers WHERE customerNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, customerNumber);
            state = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }

    public void closeConnection(){
        try{
            if(con != null){
                con.close();
            }
            if (pstm != null){
                pstm.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
