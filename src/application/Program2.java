package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        Department department = departmentDao.findById(3);
        System.out.println(department);
        System.out.println();

        System.out.println("=== TEST 2: department findAll ===");
        List<Department> departmentList = departmentDao.findAll();
        departmentList.forEach(System.out::println);
        System.out.println();

        System.out.println("=== TEST 3: department insert ===");
        Department newDepartment = new Department(null, "Games");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());
        System.out.println();

        System.out.println("=== TEST 4: department update ===");
        Department department1 = departmentDao.findById(1);
        department1.setName("Gym");
        departmentDao.update(department1);
        System.out.println("Update completed!");
        System.out.println();

        System.out.println("=== TEST 5: department delete ===");
        System.out.print("Enter id for delete test: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        departmentDao.deleteById(id);
        System.out.println("Delete completed!");
        System.out.println();

        scanner.close();
    }
}
