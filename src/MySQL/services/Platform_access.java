package MySQL.services;

import MySQL.model.Platforms;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Platform_access {

    // these four instance variables are needed for any MySQLconnection
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private dbService DBService = null;

    /**
     *  Constructor to instantiate required service object
     *  dbService required
     */
    public Platform_access(){
        DBService = new dbService();
    }

    /**
     * Method to take in User Input to create a platform in the DB
     * Calls the method that creates a new platform in the DB
     */
    public static void createPlatforms() {

        // asks the user for platform name, url and service charge
        Scanner sc = new Scanner(System.in);
        System.out.println("新しいプラトフォムの名をご入力ください。");
        String name = sc.nextLine();
        System.out.println("新しいURLをご入力ください。");
        String url = sc.nextLine();
        System.out.println("新しいサービス料金をご入力ください。");
        int service = sc.nextInt();

        Platform_access demo = new Platform_access();

        // calls the new platform method
        try{
            demo.newPlatform(name, url, service);
        } catch(Exception e){
            System.out.println("createPlatforms()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Method to take in user input to update a platform in the DB
     * Calls the method to update Platforms in the DB
     */
    public static void upPlatforms(){

        // asks the user for platform name, url and service charge
        // asks the user to confirm platform name they would like to change
        Scanner sc = new Scanner(System.in);
        System.out.println("ご変更されたいプラトフォム名をご入力ください。");
        String platform = sc.nextLine();
        System.out.println("ご変更されたいURLをご入力ください。");
        String update = sc.nextLine();
        System.out.println("ご変更されたいサービス料金をご入力ください。");
        int service = sc.nextInt();
        System.out.println("ご変更させたいプラトフォム名をご入力ください。");
        String name = sc.next();

        Platform_access demo = new Platform_access();

        // calls the method to update platform
        try{
            demo.updatePlatform(platform, update, service, name);
        } catch(Exception e){
            System.out.println("upProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Method to list all Platforms that exist in the DB to the console
     * @throws Exception
     */
    public void listPlatforms()
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // list all Products in DB
            resultSet = statement.executeQuery("select * from ECommerce.Platforms;");

            //write resultSet to an array
            ArrayList<Platforms> Platforms = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            // iterate over arrayList of platform objects and print them to the console
            for (Platforms p : Platforms){
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listPlatforms()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close the connections
            close();
        }
    }


    /**
     * Method that creates a new platform in DB
     * @param name
     * @param url
     * @param sales_fee
     * @throws Exception
     */
    public void newPlatform(String name, String url, int sales_fee)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            //PreparedStatements can be reused
            // create a preparedStatement to use for INSERT
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.Platforms (name, url, sales_fee) " +
                            "values (?, ?, ?)");

            // replace question marks in preparedStatement with values from user
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, url);
            preparedStatement.setInt(3, sales_fee);
            preparedStatement.executeUpdate();

            // select all Platforms fro DB to see the addition of a new platform
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Platforms");

            // execute the insert statement against the DB
            resultSet = preparedStatement.executeQuery();

            // print the results to the console for user
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("newPlatform()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;

        } finally {
            // close the connection
            close();
        }
    }

    /**
     * Method that deletes platform in DB by name.
     * @throws Exception
     */
    public void deletePlatform()
            throws Exception{
        try{
            // takes in user input to the name of the platform they wish to delete
            Scanner sc = new Scanner(System.in);
            System.out.println("削除されたいプラトフォム名をご入力ください。？");
            String name = sc.nextLine();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for DELETE query
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.Platforms where name = ?; ");

            // replace question marks in prepared statement with values we got from user
            preparedStatement.setString(1, name);

            // execute the delete statement against the db
            preparedStatement.executeUpdate();

            // select all Products fro DB to see the deletion of a new platform
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Platforms");

            // execute the delete statement against the db
            resultSet = preparedStatement.executeQuery();

            // print the updated platform list to the console
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deletePlatform()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            // close all connections
            close();
        }
    }

    /**
     * Method that updates the platform in DB with user input
     * @param name
     * @param url
     * @param service
     * @param old_name
     * @throws Exception
     */
    public void updatePlatform(String name, String url, int service, String old_name)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // instantiate prepared statement to use for UPDATE query
            preparedStatement = connection
                    .prepareStatement("update ECommerce.Platforms " +
                            "set name = ?, url = ?, sales_fee = ? where name = ?; ");

            // replace question marks in prepared statement with values we got from user
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, url);
            preparedStatement.setInt(3, service);
            preparedStatement.setString(4, old_name);

            // execute the update statement against the db
            preparedStatement.executeUpdate();

            // select all Products fro DB to see the update of a new platform
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.Platforms");

            // execute the update statement against the db
            resultSet = preparedStatement.executeQuery();

            // print the updated platform list to the console
            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("Error in updateProduct()" + e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            // close all connections
            close();
        }
    }

    /**
     * Method that prints the values inside the DB to show user new changes
     * @param resultSet - values to be printed
     * @throws SQLException
     */
    private void writeResultSet(ResultSet resultSet) throws SQLException{
        //ResultSet has to be written before the first data set
        while (resultSet.next()){

            // retrieve the values from the columns in the DB
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");
            int sales_fee = resultSet.getInt("sales_fee");

            // print out the values to the console
            System.out.println("ID: " + id);
            System.out.println("製品名: " + name);
            System.out.println("URL: " + url);
            System.out.println("セールス料金: " + sales_fee);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    /**
     * Maps result set of DB to an array list
     * @param resultSet
     * @return ArrayList<Platforms> - list of data as POJOs from the DB
     * @throws SQLException
     */
    private ArrayList<Platforms> mapResultSetToObjects(ResultSet resultSet) throws SQLException{

        //instantiate empty arraylist to put our Products into
        ArrayList<Platforms> retList = new ArrayList();

        // while there are more results in the resultSet
        // .next() returns the next row of data in the resultSet
        while (resultSet.next()){
            // instantiate empty Platforms object
            Platforms pl = new Platforms();

            // set the platform object id from the resultSet column "id"
            pl.setId(resultSet.getInt("id"));
            // set the platform name from the resultSet column "name"
            pl.setName(resultSet.getString("name"));
            // set the platform url from the resultSet column "url"
            pl.setUrl(resultSet.getString("url"));
            // set the product sales fee from the resultSet column "sales_fee"
            pl.setSales_fee(resultSet.getInt("sales_fee"));

            // add product object to the arraylist
            retList.add(pl);
        }
        // return arraylist of product objects
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
                preparedStatement.close();
            }

            if(connection != null){
                connection.close();
            }

        } catch (Exception e){

        }
    }

}
