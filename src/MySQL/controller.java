package MySQL;

import MySQL.services.platform_access;
import MySQL.services.prices_products_platforms_access;
import MySQL.services.product_access;

import java.util.Scanner;

public class controller {

    public static void main(String[] args) {
        controller c = new controller();
        c.userMenu();

    }

    public void userMenu() {
        int user_input = 0;
        Scanner sc = new Scanner(System.in);
        printMenu();

        product_access p = new product_access();
        platform_access pl = new platform_access();
        prices_products_platforms_access ppp = new prices_products_platforms_access();

        try {
            do {
                user_input = sc.nextInt();

                switch (user_input) {
                    case 0:
                        printMenu();
                        break;
                    case 1:
                        product_access.createProducts();
                        System.out.println("新しい製品を作りました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 2 : p.listProducts();
                        System.out.println("製品のリストを表示されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 3 : product_access.upProducts();
                        System.out.println("製品を更新されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 4 : p.deleteProduct();
                        System.out.println("製品を削除されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 5 :
                        platform_access.createPlatforms();
                        System.out.println("新しいプラトフォムを作りした。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 6 : pl.listPlatforms();
                        System.out.println("プラトフォムを表示されました。");
                        System.out.println("メニューを表示には「０」を入力ください。");
                        break;
                    case 7 : platform_access.upPlatforms();
                        break;
                    case 8 : pl.deletePlatform();
                        break;
                    case 9 : ppp.newRecord();
                        break;
                    case 10 : ppp.listRecords();
                        break;
                    case 11 : ppp.upRecord();
                        break;
                    case 12 : ppp.deleteRecord();
                        break;
                    case 13 :
                        System.out.println("ご利用頂きまして、誠にありがとうございました。");
                        return;
                }
            } while (user_input != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
