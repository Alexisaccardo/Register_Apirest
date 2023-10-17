package com.example.demo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BD {
    public BD() {
    }

    public static int Delete(String id) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/register_products";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM products WHERE id = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, id);
        int f = prepareStatement.executeUpdate();

        if (f > 0){
            return 1;
        }else {
            return 0;
        }
    }

    public static int Delete_all() throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/register_products";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM products";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);

        int f = prepareStatement.executeUpdate();

        if (f > 0){
            return 1;
        }else {
            return 0;
        }
    }

    public Products register(String id, String name, int amount, String description, String category) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/register_products";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

            // Sentencia INSERT
            String sql = "INSERT INTO products (id , name, date, amount, description, category) VALUES (?, ?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, String.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, amount);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, category);

            // Ejecutar la sentencia
            int files = preparedStatement.executeUpdate();

            if (files > 0) {
                System.out.println("Producto agregado exitosamente.");
                return new Products(id, name, amount, description, category);
            } else {
                System.out.println(Errors.error_register);
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Products(null,null,0,null,null);
    }

    public Products edit(String id, int amount) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/register_products";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();
        String consulta = "UPDATE products SET amount = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setInt(1, amount);
        preparedStatement.setString(2, id);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Producto actualizado exitosamente");
            return new Products (id, null, amount, null, null);
        } else {
            System.out.println(Errors.error_edit);
        }

        preparedStatement.close();
        connection2.close();

        return new Products(Errors.edit_control,null,0,null,null);
    }

    public List<Products> search() throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/register_products";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM products");
        List<Products> list = new ArrayList<>();

        while(resultSet2.next()){

            String id = resultSet2.getString("id");
            String name = resultSet2.getString("name");
            int amount = resultSet2.getInt("amount");
            String description = resultSet2.getString("description");
            String category = resultSet2.getString("category");

            Products products = new Products(id, name, amount, description, category);
            list.add(products);
        }
        return list;
    }
}


