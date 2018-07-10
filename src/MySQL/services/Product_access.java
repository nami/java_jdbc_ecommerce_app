package MySQL.services;

import MySQL.model.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Product_access {

    // these five instance variables are needed for any MySQLconnection
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // service objects for a connection
    private dbService DBService = null;

    /**
     * Constructor to instantiate required service objects
     */
    public Product_access() {
        DBService = new dbService();
    }

    /**
     * Method for user to input Products they want to create in DB
     * calls the new product method
     */
    public static void createProducts() {

        // asks user for new product name and description
        Scanner sc = new Scanner(System.in);
        System.out.println("新しい製品名をご入力ください。");
        String name = sc.nextLine();
        System.out.println("新しい製品内容をご入力ください。");
        String desc = sc.nextLine();

        Product_access demo = new Product_access();

        // calls the new product method
        try {
            demo.newProduct(name, desc);
        } catch (Exception e) {
            System.out.println("createProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Method for input product updates in DB
     * calls the update product method
     */
    public static void upProducts() {

        // asks user for modified product name, desc, and to confirm old product name
        Scanner sc = new Scanner(System.in);
        System.out.println("ご変更されたい製品名をご入力ください。");
        String product = sc.nextLine();
        System.out.println("ご変更されたい製品内容をご入力ください。");
        String update = sc.nextLine();
        System.out.println("ご変更させたい製品名をご入力ください。");
        String name = sc.nextLine();

        Product_access demo = new Product_access();

        // call the update product method
        try {
            demo.updateProduct(product, update, name);
        } catch (Exception e) {
            System.out.println("upProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Method to list all Products in DB
     *
     * @throws Exception
     */
    public void listProducts()
            throws Exception {
        try {
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // Result set will retrieve the result of the SQL query
            resultSet = statement.executeQuery("select * from ECommerce.Products;");

            // create arraylist of Product objects from resultSet
            ArrayList<Products> Products = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            // iterate through arraylist and print each product to the console
            for (Products p : Products) {
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close connection
            close();
        }
    }


    /**
     * A method to create a new product
     *
     * @param p_name
     * @param p_description
     * @throws Exception
     */
    public void newProduct(String p_name, String p_description)
            throws Exception {
        try {
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate preparedStatement to use for INSERT query
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.Products (p_name, p_description) " +
                            "values (?, ?)");

            // replace question marks in the query above with values we got from the user
            preparedStatement.setString(1, p_name);
            preparedStatement.setString(2, p_description);
            preparedStatement.executeUpdate();

            // instantiate preparedStatement to select all product from the DB
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Products");

            // execute the INSERT statement against the DB
            resultSet = preparedStatement.executeQuery();

            // print to the console the update list of Products
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("newProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close connections
            close();
        }
    }

    /**
     * A method for the user to delete a product in the DB by name
     *
     * @throws Exception
     */
    public void deleteProduct()
            throws Exception {
        try {
            // get product name from user to delete
            Scanner sc = new Scanner(System.in);
            System.out.println("削除されたい製品名をご入力ください。");
            String product = sc.nextLine();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for DELETE query
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.Products where p_name = ?; ");

            // replace question mark in query above with name we got from user
            preparedStatement.setString(1, product);

            // execute DELETE statement against the DB
            preparedStatement.executeUpdate();

            // instantiate preparedStatement to select all product from the DB
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Products");

            // get the new resultSet
            resultSet = preparedStatement.executeQuery();

            // print the resultSet to the control so user can see new updates
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deleteProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close connections
            close();
        }
    }

    /**
     * A method to update any product in the DB
     *
     * @param new_name
     * @param p_description
     * @param old_name
     * @throws Exception
     */
    public void updateProduct(String new_name, String p_description, String old_name)
            throws Exception {
        try {
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for UPDATE query
            preparedStatement = connection
                    .prepareStatement("update ECommerce.Products " +
                            "set p_name = ?, p_description = ? where p_name = ?; ");

            // replace question mark in query above with values we got from user
            preparedStatement.setString(1, new_name);
            preparedStatement.setString(2, p_description);
            preparedStatement.setString(3, old_name);

            // execute UPDATE statement against the DB
            preparedStatement.executeUpdate();

            // instantiate prepared statement to use for select all Products query
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Products");

            // get the result set
            resultSet = preparedStatement.executeQuery();

            // print the result set to the console for the user to see updates
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("updateProduct()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            // close connections
            close();
        }
    }

    /**
     * Method that prints the values inside the DB to show user new changes
     *
     * @param resultSet
     * @throws SQLException
     */
    private void writeResultSet(ResultSet resultSet) throws SQLException {
        //ResultSet has to be written before the first data set
        while (resultSet.next()) {
            // write in columns from Products obj

            // retrieve the values from the columns in the DB
            int id = resultSet.getInt("id");
            String p_name = resultSet.getString("p_name");
            String p_description = resultSet.getString("p_description");

            // print out the values to the console
            System.out.println("ID: " + id);
            System.out.println("製品名: " + p_name);
            System.out.println("製品内容: " + p_description);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    /**
     * Maps result set of DB to an array list
     *
     * @param resultSet
     * @return - ArrayList<Products> - list of data as POJOs from the DB
     * @throws SQLException
     */
    private ArrayList<Products> mapResultSetToObjects(ResultSet resultSet) throws SQLException {

        // instantiate empty arraylist to put our Products into
        ArrayList<Products> retList = new ArrayList();

        // while there are more results in the resultSet
        // .next() returns the next row of data in the resultSet
        while (resultSet.next()) {
            // instantiate empty Product object
            Products p = new Products();

            // set the platform object id from the resultSet column "id"
            p.setId(resultSet.getInt("id"));
            // set the product name from the resultSet column "p_name"
            p.setP_name(resultSet.getString("p_name"));
            // set the product description id from the resultSet column "p_description"
            p.setP_description(resultSet.getString("p_description"));

            // add product object to the arraylist
            retList.add(p);
        }
        // return arraylist of product objects
        return retList;
    }

    //Close connections
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {

        }
    }
}