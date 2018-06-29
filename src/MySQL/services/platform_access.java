package MySQL.services;

import MySQL.model.platforms;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class platform_access {

    // these four instance variables are needed for any MySQLconnection
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // connect objects
    private dbService DBService = null;

    public platform_access(){
        DBService = new dbService();
    }

    public static void createPlatforms() {

        Scanner sc = new Scanner(System.in);
        System.out.println("新しいプラトフォムの名をご入力ください。");
        String name = sc.nextLine();
        System.out.println("新しいURLをご入力ください。");
        String url = sc.nextLine();
        System.out.println("新しいサービス料金をご入力ください。");
        int service = sc.nextInt();

        platform_access demo = new platform_access();

        // have a user write in a platform
        try{
            demo.newPlatform(name, url, service);
        } catch(Exception e){
            System.out.println("createPlatforms()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void upPlatforms(){

        Scanner sc = new Scanner(System.in);
        System.out.println("ご変更されたいプラトフォム名をご入力ください。");
        String platform = sc.nextLine();
        System.out.println("ご変更されたいURLをご入力ください。");
        String update = sc.nextLine();
        System.out.println("ご変更されたいサービス料金をご入力ください。");
        int service = sc.nextInt();
        System.out.println("ご変更させたいプラトフォム名をご入力ください。");
        String name = sc.next();

        platform_access demo = new platform_access();

        // have a user update a platform
        try{
            demo.updatePlatform(platform, update, service, name);
        } catch(Exception e){
            System.out.println("upProducts()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }


    public void listPlatforms()
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // list all products in DB
            resultSet = statement.executeQuery("select * from ECommerce.platforms;");

            //write resultSet to an array
            ArrayList<platforms> platforms = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            for (platforms p : platforms){
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listPlatforms()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }


    // create the new platform
    public void newPlatform(String name, String url, int sales_fee)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            //PreparedStatements can be reused
            // Insert elements into the tables
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.platforms (name, url, sales_fee) " +
                            "values (?, ?, ?)");

            // Parameters start with 1
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, url);
            preparedStatement.setInt(3, sales_fee);
            preparedStatement.executeUpdate();

            // print out products to see the addition
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("newPlatform()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }

    // delete platform
    public void deletePlatform()
            throws Exception{
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("削除されたいプラトフォム名をご入力ください。？");
            String name = sc.nextLine();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // delete any product in DB
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.platforms where name = ?; ");

            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deletePlatform()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }

    // update any platform in DB
    public void updatePlatform(String name, String url, int service, String old_name)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // update any product in DB
            preparedStatement = connection
                    .prepareStatement("update ECommerce.platforms " +
                            "set name = ?, url = ?, sales_fee = ? where name = ?; ");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, url);
            preparedStatement.setInt(3, service);
            preparedStatement.setString(4, old_name);

            preparedStatement.executeUpdate();

            // print out products to see the update
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("Error in updateProduct()" + e.getMessage());
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
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");
            int sales_fee = resultSet.getInt("sales_fee");

            System.out.println("ID: " + id);
            System.out.println("製品名: " + name);
            System.out.println("URL: " + url);
            System.out.println("セールス料金: " + sales_fee);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    private ArrayList<platforms> mapResultSetToObjects(ResultSet resultSet) throws SQLException{

        ArrayList<platforms> retList = new ArrayList();

        while (resultSet.next()){
            platforms pl = new platforms();
            pl.setId(resultSet.getInt("id"));
            pl.setName(resultSet.getString("name"));
            pl.setUrl(resultSet.getString("url"));
            pl.setSales_fee(resultSet.getInt("sales_fee"));

            retList.add(pl);
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
