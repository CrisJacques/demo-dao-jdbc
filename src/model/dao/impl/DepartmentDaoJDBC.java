package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) { // Verifica se realmente foi inserido algum registro no banco
                ResultSet rs = st.getGeneratedKeys(); // As chaves geradas no banco são disponibilizadas no formato de um ResultSet
                if (rs.next()) { // Se realmente tem algo dentro do ResultSet
                    int id = rs.getInt(1); // Pega o id do registro inserido no banco (foi só 1)
                    obj.setId(id);// Seta esse id no objeto passado por parâmetro para que ele fique atualizado
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DBException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE department "
                            + "SET Name = ? "
                            + "WHERE Id = ? "
            );

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
                    "DELETE FROM department "
                            + "WHERE Id = ?"
            );
            st.setInt(1, id);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT department.* "
                            + "FROM department "
                            + "WHERE department.Id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){ // Quando o ResultSet é criado, ele inicia no posição zero, onde não tem dados, só tem da posição seguinte em diante (se tiver)
                // Por isso temos que começar verificando se existe um próximo elemento no ResultSet
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null; // Se não cair no if do rs.next(), significa que a consulta não retornou nada, então não existe um Department com o id informado
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT department.* "
                            + "FROM department "
                            + "ORDER BY Name"
            );

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()){
                list.add(instantiateDepartment(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }
}
