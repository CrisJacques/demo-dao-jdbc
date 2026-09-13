package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    // Esta classe implementa a interface SellerDao utilizando a tecnologia JDBC para acesso ao banco de dados
    // Caso um dia a forma de acesso ao banco de dados mude (por exemplo, passe a ser passado um ORM), aí basta criar uma nova classe que implementa a interface SellerDao
    // Esta classe será responsável por executar as querys no banco de dados referentes a diferentes tipos de operações (consulta, inserção, atualização, deleção) e também
    //por retornar o resultado no formato de objetos das entidades envolvidas, quando for o caso
    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){ // Quando o ResultSet é criado, ele inicia no posição zero, onde não tem dados, só tem da posição seguinte em diante (se tiver)
                // Por isso temos que começar verificando se existe um próximo elemento no ResultSet
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);
                return seller;
            }
            return null; // Se não cair no if do rs.next(), significa que a consulta não retornou nada, então não existe um Seller com o id informado
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name"
            );
            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>(); // map que vai armazenar todos os Department que forem instanciados

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId")); // verificando se o Department já existe, para só instanciar se ainda não existir

                if (dep == null) {// quando o map.get() não encontra o elemento buscado, ele retorna null
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setDepartment(dep);

        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));

        return dep;
    }
}
