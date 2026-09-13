package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department); // Se o método não for declarado aqui na interface, ele não vai aparecer no
    //autocomplete ao tentar usar no Program.java, mesmo que ele tenha sido implementado na classe que implementa esta interface!

}
