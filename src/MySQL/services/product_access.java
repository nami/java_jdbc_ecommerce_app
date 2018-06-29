package MySQL.services;

import MySQL.model.products;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class product_access {
    // these four instance variables are needed for any MySQLconnection
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // connect objects
    private dbService DBService = null;

    public product_access(){
        DBService = new dbService();
    }

    public static void createProducts() {

        Scanner sc = new Scanner(System.in);
        System.out.println("新しい製品名をご入力ください。");
        String name = sc.nextLine();
        System.out.println("新しい製品内容をご入力ください。");
        String desc = sc.nextLine();

        product_access demo = new product_access();

        // have a user write in a product
        try{
            demo.newProduct(name, desc);
        } catch(Exception e){
            System.out.println("createProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void upProducts(){

        Scanner sc = new Scanner(System.in);
        System.out.println("ご変更されたい製品名をご入力ください。");
        String product = sc.nextLine();
        System.out.println("ご変更されたい製品内容をご入力ください。");
        String update = sc.nextLine();
        System.out.println("ご変更させたい製品名をご入力ください。");
        String name = sc.nextLine();

        product_access demo = new product_access();

        // have a user update a product
        try{
            demo.updateProduct(product, update, name);
        } catch(Exception e){
            System.out.println("upProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }


    public void listProducts()
        throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // list all products in DB
            resultSet = statement.executeQuery("select * from ECommerce.products;");

            //write resultSet to an array
            ArrayList<products> products = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            for (products p : products){
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }


    // create the new product
    public void newProduct(String p_name, String p_description)
        throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            //PreparedStatements can be reused
            // Insert elements into the tables
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.products (p_name, p_description) " +
                    "values (?, ?)");

            // Parameters start with 1
            preparedStatement.setString(1, p_name);
            preparedStatement.setString(2, p_description);
            preparedStatement.executeUpdate();

            // print out products to see the addition
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.products");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("newProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
          close();
        }
    }

    public void deleteProduct()
        throws Exception{
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("削除されたい製品名をご入力ください。");
            String product = sc.nextLine();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // delete any product in DB
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.products where p_name = ?; ");

            preparedStatement.setString(1, product);

            preparedStatement.executeUpdate();

            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.products");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deleteProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }

    // update any product in DB
    public void updateProduct(String new_name, String p_description, String old_name)
        throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // update any product in DB
            preparedStatement = connection
                    .prepareStatement("update ECommerce.products " +
                            "set p_name = ?, p_description = ? where p_name = ?; ");

            preparedStatement.setString(1, new_name);
            preparedStatement.setString(2, p_description);
            preparedStatement.setString(3, old_name);

            preparedStatement.executeUpdate();

            // print out products to see the update
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.products");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("updateProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            close();
        }
    }


    private void writeResultSet(ResultSet resultSet) throws SQLException{
        //ResultSet has to be written before the first data set
        while (resultSet.next()){
            // write in columns from products obj

            int id = resultSet.getInt("id");
           String p_name = resultSet.getString("p_name");
           String p_description = resultSet.getString("p_description");

            System.out.println("ID: " + id);
            System.out.println("製品名: " + p_name);
            System.out.println("製品内容: " + p_description);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    private ArrayList<products> mapResultSetToObjects(ResultSet resultSet) throws SQLException{

        ArrayList<products> retList = new ArrayList();

        while (resultSet.next()){
            products p = new products();
            p.setId(resultSet.getInt("id"));
            p.setP_name(resultSet.getString("p_name"));
            p.setP_description(resultSet.getString("p_description"));

            retList.add(p);
        }
        return retList;
    }

    //Close resultSet
    private void close(){
        try {
            if(resultSet != null){
                resultSet.close();
            }

            if(statement != null){
                statement.close();
            }

            if(preparedStatement != null){
                statement.close();
            }

            if(connection != null){
                connection.close();
            }

        } catch (Exception e){

        }
    }
}