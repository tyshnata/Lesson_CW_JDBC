package com;

import java.sql.*;

public class Main {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static int idValue = 0;

    public static void main(String[] args){
        getConnection();
        createTable();
        addDataToDB(1,"Masha", 25 );
        addDataToDB(2,"Misha", 19 );
        addDataToDB(3,"Sasha", 5 );
        getDataFromDB();
        updateDB("age = 5","age = 4");
        getDataFromDB();
        deleteData("id = 1");
        getDataFromDB();
        deleteData("id = 2");
        getDataFromDB();
        deleteData("id = 3");
        getDataFromDB();
    }

    private static void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Registered");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not found JDBC driver");
            e.printStackTrace();
            return;
        }

        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?serverTimezone=Europe/Kiev", "root", "sasha");
            System.out.println("Successful connection");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void createTable() {

        Statement statement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?serverTimezone=Europe/Kiev", "root", "sasha");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String myTable = "CREATE TABLE users (id INT, name VARCHAR(50),age INT)";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(myTable);
            System.out.println("Table Created");
        }
        catch (SQLException e ) {
            System.out.println("An error has occurred on Table Creation");
        }

    }


    private static void addDataToDB(int id, String userName, int age) {
        System.out.println("------------ adding data --------------");

        String insertStatement="INSERT  INTO  users VALUES  (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, userName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println(userName + " adding successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void updateDB(String set, String condition){
        System.out.println("------------ updating data --------------");

        String insertStatement="UPDATE users SET "  + set +" WHERE " + condition +";";
        try {
            preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.executeUpdate();
            System.out.println(" updating successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void getDataFromDB(){
        System.out.println("----------- table users ----------- ");

        String getStatement = "SELECT * FROM users";
        try {
            preparedStatement=connection.prepareStatement(getStatement);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer id=resultSet.getInt("id");
                String userName=resultSet.getString("name");
                int age=resultSet.getInt("age");

                System.out.format("%s, %s, %s\n", id, userName, age);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

    private static void deleteData(String condition){
        String insertStatement="DELETE FROM users WHERE "  + condition + ";";
        try {
            preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.executeUpdate();
            System.out.println(" deleting successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
