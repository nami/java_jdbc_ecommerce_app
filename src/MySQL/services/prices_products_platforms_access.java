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

    // connect objects
    private dbService DBService = null;

    public prices_products_platforms_access(){
        DBService = new dbService();
    }

    public static void newRecord() {

        Scanner sc = new Scanner(System.in);
        System.out.println("製品のIDをご入力ください。");
        int product_id = sc.nextInt();
        System.out.println("プラとフォムのIDをご入力ください。");
        int platform_id = sc.nextInt();
        System.out.println("ご値段をご入力ください。");
        int prices = sc.nextInt();

        prices_products_platforms_access demo = new prices_products_platforms_access();

        // have a user write in a new record
        try{
            demo.createRecord(product_id, platform_id, prices);
        } catch(Exception e){
            System.out.println("createRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void upRecord(){

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

        // have a user update a record
        try{
            demo.updateRecord(product_id, platform_id, price, p_id);
        } catch(Exception e){
            System.out.println("upRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }


    public void listRecords()
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // list all products in DB
            resultSet = statement.executeQuery("select * from ECommerce.prices_products_platforms;");

            //write resultSet to an array
            ArrayList<records> records = mapResultSetToObjects(resultSet);

            System.out.println("----------------------------------------------------");

            for (records p : records){
                System.out.println(p.toString());
            }

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("listRecords()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }


    // create the new record
    public void createRecord(int product_id, int platform_id, int prices)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            //PreparedStatements can be reused
            // Insert elements into the tables
            preparedStatement = connection
                    .prepareStatement("insert into ECommerce.prices_products_platforms (product_id, platform_id, prices) " +
                            "values (?, ?, ?)");

            // Parameters start with 1
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, platform_id);
            preparedStatement.setInt(3, prices);
            preparedStatement.executeUpdate();

            // print out products to see the addition
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("createRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }

    // delete record
    public void deleteRecord()
            throws Exception{
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("ご削除されたい記録のIDご入力ください。");
            int id = sc.nextInt();

            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // delete any product in DB
            preparedStatement = connection
                    .prepareStatement("delete from ECommerce.prices_products_platforms where id = ?; ");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");

        } catch (Exception e) {
            System.out.println("deleteRecord()のエラー" + e.getMessage());
            System.out.println(e.getStackTrace());
            throw e;
        } finally {
            close();
        }
    }

    // update any record in DB
    public void updateRecord(int product_id, int platform_id, int prices, int confirm_product_id)
            throws Exception{
        try{
            // call connection
            connection = DBService.getConnection();

            // statement allows to issue SQL queries to the database
            statement = DBService.getStatement();

            // update any record in DB
            preparedStatement = connection
                    .prepareStatement("update ECommerce.prices_products_platforms " +
                            "set product_id = ?, platform_id = ?, prices = ? where product_id = ?; ");

            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, platform_id);
            preparedStatement.setInt(3, prices);
            preparedStatement.setInt(4, confirm_product_id);

            preparedStatement.executeUpdate();

            // print out products to see the update
            preparedStatement = connection
                    .prepareStatement("SELECT * from ECommerce.prices_products_platforms");

            resultSet = preparedStatement.executeQuery();

            writeResultSet(resultSet);

            System.out.println("----------------------------------------------------");


        } catch (Exception e) {
            System.out.println("Error in updateRecord()" + e.getMessage());
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
            int product_id = resultSet.getInt("product_id");
            int platform_id = resultSet.getInt("platform_id");
            int prices = resultSet.getInt("prices");

            System.out.println("ID: " + id);
            System.out.println("製品のID: " + product_id);
            System.out.println("プラトフォムのID: " + platform_id);
            System.out.println("ご料金: " + prices);
            System.out.println("---------------------------------");
            System.out.println("---------------------------------");
        }
    }

    private ArrayList<records> mapResultSetToObjects(ResultSet resultSet) throws SQLException{

        ArrayList<records> retList = new ArrayList();

        while (resultSet.next()){
            records pl = new records();
            pl.setId(resultSet.getInt("id"));
            pl.setProduct_id(resultSet.getInt("product_id"));
            pl.setPlatform_id(resultSet.getInt("platform_id"));
            pl.setPrices(resultSet.getInt("prices"));

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
