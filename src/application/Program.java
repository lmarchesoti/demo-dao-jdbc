package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("=== TEST 2: seller findByDepartmentId ===");
        List<Seller> sellers = sellerDao.findByDepartmentId(2);
        sellers.forEach(System.out::println);
        System.out.println();

        System.out.println("=== TEST 3: seller findAll ===");
        List<Seller> sellersAll = sellerDao.findAll();
        sellersAll.forEach(System.out::println);
        System.out.println();

        System.out.println("=== TEST 4: seller insert ===");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(2, null));
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        System.out.println();

        System.out.println("=== TEST 5: seller update ===");
        Seller seller1 = sellerDao.findById(1);
        seller1.setName("Martha Waine");
        sellerDao.update(seller1);
        System.out.println("Update completed!");
        System.out.println();
    }
}
