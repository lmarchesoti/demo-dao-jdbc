package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("=== TEST 2: seller findByDepartmentId ===");
        List<Seller> sellers = sellerDao.findByDepartmentId(2);
        sellers.forEach(System.out::println);

        System.out.println("=== TEST 3: seller findAll ===");
        List<Seller> sellersAll = sellerDao.findAll();
        sellersAll.forEach(System.out::println);
    }
}
