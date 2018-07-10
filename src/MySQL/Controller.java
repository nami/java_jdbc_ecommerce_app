package MySQL;

import MySQL.services.Platform_access;
import MySQL.services.Prices_products_platforms_access;
import MySQL.services.Product_access;

import java.util.Scanner;

public class Controller {

    /**
     * main() to start ECommerce
     * @param args
     */
    public static void main(String[] args) {
        Controller c = new Controller();
        c.userMenu();

    }

    public void userMenu() {
        // instantiate user_input variable to hold user input
        int user_input = 0;
        Scanner sc = new Scanner(System.in);
        // print the menu for user to choose from
        printMenu();

        /**
         * Constructor to instantiate required service classes
         */
        Product_access p = new Product_access();
        Platform_access pl = new Platform_access();
        Prices_products_platforms_access ppp = new Prices_products_platforms_access();

        /**
         * primary controller method for ECommerce application - uses do while loop to take commands from user based on
         * menu that is printed within each loop until user enters -1 to quite the program
         */
        try {
            // do while loop to continously prompt for next action
            do {
                // get the user selection
                user_input = sc.nextInt();

                // determine user selection using switch statement and act accordingly
                switch (user_input) {
                    case 0:
                        printMenu();
                        break;
                    case 1:
                        Product_access.createProducts();
                        System.out.println("新しい製品を作りました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 2 : p.listProducts();
                        System.out.println("製品のリストを表示されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 3 : Product_access.upProducts();
                        System.out.println("製品を更新されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 4 : p.deleteProduct();
                        System.out.println("製品を削除されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 5 :
                        Platform_access.createPlatforms();
                        System.out.println("新しいプラトフォムを作りした。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 6 : pl.listPlatforms();
                        System.out.println("プラトフォムを表示されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 7 : Platform_access.upPlatforms();
                        System.out.println("プラトフォムを変更されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 8 : pl.deletePlatform();
                        System.out.println("プラトフォムを削除されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 9 : ppp.newRecord();
                        System.out.println("新しいレコードを。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 10 : ppp.listRecords();
                        System.out.println("レコードのリストを表示されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 11 : ppp.upRecord();
                        System.out.println("レコードを変更されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 12 : ppp.deleteRecord();
                        System.out.println("レコードを削除されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 13 :
                        System.out.println("ご利用頂きまして、誠にありがとうございました。");
                        return;
                }
                // continue while user does not enter -1
            } while (user_input != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that prints menu options for the user
     */
    public void printMenu(){
        System.out.println("ECommerceのメニュ - 番号をご入力ください");
        System.out.println("1. 新しい製品をご入力");
        System.out.println("2. 製品を表示");
        System.out.println("3. 製品を更新");
        System.out.println("4. 製品を削除");
        System.out.println("-------------------------------------------");
        System.out.println("5. 新しいプラトフォムをご入力");
        System.out.println("6. プラトフォムを表示");
        System.out.println("7. プラトフォムを更新");
        System.out.println("8. プラトフォムを削除");
        System.out.println("-------------------------------------------");
        System.out.println("9. 新しいレコードをご入力");
        System.out.println("10. prices_products_platformsのレコードを表示");
        System.out.println("11. レコードを更新");
        System.out.println("12. レコードを削除");
        System.out.println("-------------------------------------------");
        System.out.println("13. 終了");
        System.out.println("-------------------------------------------");
    }
}
