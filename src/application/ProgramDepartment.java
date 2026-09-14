package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class ProgramDepartment {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("\n === TEST 1: department insert === ");
        Department dep = new Department(null, "Testes");
        depDao.insert(dep);
        System.out.println("Inserted! New id = " + dep.getId());

        System.out.println("\n === TEST 2: department findById === ");
        Department depTest2 = depDao.findById(6);
        System.out.println(depTest2);

        System.out.println("\n === TEST 3: department update === ");
        Department depTest3 = depDao.findById(6);
        depTest3.setName("Teste1");
        depDao.update(depTest3);
        System.out.println(depTest3);

        System.out.println("\n === TEST 4: department findAll === ");
        List<Department> list = depDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n === TEST 5: department deleteById === ");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        depDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
