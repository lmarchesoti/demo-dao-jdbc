package model.dao.impl;

import db.DB;
import db.DbException;
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

public class SellerDaoJdbc implements SellerDao {

    private final Connection connection;

    public SellerDaoJdbc(Connection connection) {
        this.connection = connection;
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = """
                SELECT seller.*, department.Name AS DepName
                FROM seller
                INNER JOIN department ON seller.DepartmentId = department.Id
                WHERE seller.id = ?""";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Department department = instantiateDepartment(resultSet);

                return instantiateSeller(resultSet, department);
            } else {
                return null;
            }

        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }

    private static Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    private static Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Seller> result = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        String sql = """
                SELECT seller.*, department.Name AS DepName
                FROM seller
                INNER JOIN department ON seller.DepartmentId = department.Id
                ORDER BY Name""";

        try {
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int departmentId = resultSet.getInt("DepartmentId");
                Department newDepartment = departmentMap.get(departmentId);

                if (newDepartment == null) {
                    newDepartment = instantiateDepartment(resultSet);
                    departmentMap.put(departmentId, newDepartment);
                }

                result.add(instantiateSeller(resultSet, newDepartment));
            }

            return result;

        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Seller> findByDepartmentId(Integer departmentId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Seller> result = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        String sql = """
                SELECT seller.*, department.Name AS DepName
                FROM seller
                INNER JOIN department ON seller.DepartmentId = department.Id
                WHERE DepartmentId = ?
                ORDER BY Name""";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, departmentId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int newDepartmentId = resultSet.getInt("DepartmentId");
                Department newDepartment = departmentMap.get(newDepartmentId);

                if (newDepartment == null) {
                    newDepartment = instantiateDepartment(resultSet);
                    departmentMap.put(newDepartmentId, newDepartment);
                }

                result.add(instantiateSeller(resultSet, newDepartment));
            }

            return result;

        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
    }
}
