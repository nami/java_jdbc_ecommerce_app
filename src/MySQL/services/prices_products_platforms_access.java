package MySQL.services;

import MySQL.model.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class prices_products_platforms_access {

    // these four instance variables are needed for any MySQLconnection
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // required service object
    private dbService DBService = null;

    /**
     * Constructor to instantiate required service objects
     */
    public prices_products_platforms_access(){
        DBService = new dbService();
    }

    /**
     * A method to create a new record in the DB using user input values
     * calls createRecord method
     */
    public static void newRecord() {

        // asks user to input product id, platform id and price
        Scanner sc = new Scanner(System.in);
        System.out.println("製品のIDをご入力ください。");
        int product_id = sc.nextInt();
        System.out.println("プラとフォムのIDをご入力ください。");
        int platform_id = sc.nextInt();
        System.out.println("ご値段をご入力ください。");
        int prices = sc.nextInt();

        prices_products_platforms_access demo = new prices_products_platforms_access();

        // calls the createRecord method
        try{
            demo.createRecord(product_id, platform_id, prices);
        } catch(Exception e){
            System.out.println("createRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * A method to update record in the DB using user input values
     * calls the update record method
     */
    public static void upRecord(){

        // asks user to input modified id, modified platform and product id
        Scanner sc = new Scanner(System.in);
        System.out.println("ご変更させた製品IDをご入力ください。");
        int product_id = sc.nextInt();
        System.out.println("ご変更されたいプラとフォムをご入力ください。");
        int platform_id = sc.nextInt();
        System.out.println("ご変更されたいサービス料金ご入力ください。");
        int price = sc.nextInt();
        System.out.println("製品のIDをご確認ください。");
        int p_id = sc.nextInt();

        prices_products_platforms_access demo = new prices_products_platforms_access();

        // call the update record method
        try{
            demo.updateRecord(product_id, platform_id, price, p_id);
        } catch(Exception e){
            System.out.println("upRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }


    /**
     * A method that lists all records in the DB
     * @throws Exception
     */
    public void listRecords()
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from ECommerce.prices_products_platforms;");

            // create arraylist of reccords objects from resultSet
            ArrayList<records> records = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            // // iterate through arraylist and print each record to the console
            for (records p : records){
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listRecords()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close connections
            close();
        }
    }


    /**
     * Method that creates a new record
     * @param product_id
     * @param platform_id
     * @param prices
     * @throws Exception
     */
    public void createRecord(int product_id, int platform_id, int prices)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            //PreparedStatements can be reused
            // instantiate preparedStatement to use for UPDATE query
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.prices_products_platforms (product_id, platform_id, prices) " +
                            "values (?, ?, ?)");

            // replace question marks in the query above with values we got from the user
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, platform_id);
            preparedStatement.setInt(3, prices);
            preparedStatement.executeUpdate();

            // Get the result of the SQL query
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            // execute the INSERT INTO statement against the DB
            resultSet = preparedStatement.executeQuery();

            // print the resultSet to the console
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("createRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close the connections
            close();
        }
    }

    /**
     * A method to delete records by OD
     * @throws Exception
     */
    public void deleteRecord()
            throws Exception{
        try{
            // take intended ID to delete from user
            Scanner sc = new Scanner(System.in);
            System.out.println("ご削除されたい記録のIDご入力ください。");
            int id = sc.nextInt();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for DELETE query
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.prices_products_platforms where id = ?; ");

            // replace question mark in query above with ID we got from user
            preparedStatement.setInt(1, id);

            // execute DELETE statement against the DB
            preparedStatement.executeUpdate();

            // instantiate prepared statement to use for SELECT query
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            // write new record list to result set
            resultSet = preparedStatement.executeQuery();

            // print new record list to console
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deleteRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close connections
            close();
        }
    }

    /**
     * A method to update records in the DB
     * @param product_id
     * @param platform_id
     * @param prices
     * @param confirm_product_id
     * @throws Exception
     */
    public void updateRecord(int product_id, int platform_id, int prices, int confirm_product_id)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for UPDATE query
            preparedStatement = connection
                    .prepareStatement("update ECommerce.prices_products_platforms " +
                            "set product_id = ?, platform_id = ?, prices = ? where product_id = ?; ");

            //replace question mark in query above with values we got from user
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, platform_id);
            preparedStatement.setInt(3, prices);
            preparedStatement.setInt(4, confirm_product_id);

            // execute UPDATE statement against the DB
            preparedStatement.executeUpdate();

            // instantiate prepared statement to use for SELECT query
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            // write new record list to result set
            resultSet = preparedStatement.executeQuery();

            // print new records list to console
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("Error in updateRecord()" + e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            // close connections
            close();
        }
    }


    /**
     * Method that prints the values inside the DB to show user new changes
     * @param resultSet
     * @throws SQLException
     */
    private void writeResultSet(ResultSet resultSet) throws SQLException{
        //ResultSet has to be written before the first data set
        while (resultSet.next()){
            // write in columns from products obj

            // retrieve the values from the columns in the DB
            int id = resultSet.getInt("id");
            int product_id = resultSet.getInt("product_id");
            int platform_id = resultSet.getInt("platform_id");
            int prices = resultSet.getInt("prices");

            // print out the values to the console
            System.out.println("ID: " + id);
            System.out.println("製品のID: " + product_id);
            System.out.println("プラトフォムのID: " + platform_id);
            System.out.println("ご料金: " + prices);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    /**
     * A method to map the rows of data in a resultSet onto an arraylist of records objects (POJOs)
     * @param resultSet
     * @return ArrayList<records> list of data as POJOs from the DB
     * @throws SQLException
     */
    private ArrayList<records> mapResultSetToObjects(ResultSet resultSet) throws SQLException{

        // instantiate empty arraylist to put our products into
        ArrayList<records> retList = new ArrayList();

        // while there are more results in the resultSet
        // .next() returns the next row of data in the resultSet
        while (resultSet.next()){
            // instantiate empty Product object
            records pl = new records();

            // set the platform object id from the resultSet column "id"
            pl.setId(resultSet.getInt("id"));
            // set the platform object id from the resultSet column "product_id"
            pl.setProduct_id(resultSet.getInt("product_id"));
            // set the platform object id from the resultSet column "platform_id"
            pl.setPlatform_id(resultSet.getInt("platform_id"));
            // set the platform object id from the resultSet column "prices"
            pl.setPrices(resultSet.getInt("prices"));

            // add product object to the arraylist
            retList.add(pl);
        }
        // return arraylist of product objects
        return retList;
    }

    //Close connections
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
