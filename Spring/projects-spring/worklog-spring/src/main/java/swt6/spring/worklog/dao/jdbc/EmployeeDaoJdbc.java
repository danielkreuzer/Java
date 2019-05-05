package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.sql.*;
import java.util.List;

public class EmployeeDaoJdbc extends JdbcDaoSupport implements EmployeeDao {

    private static class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            var employee = new Employee();
            employee.setId(rs.getLong("ID"));
            employee.setFirstName(rs.getString("FIRSTNAME"));
            employee.setLastName(rs.getString("LASTNAME"));
            employee.setDateOfBirth(rs.getDate("DATEOFBIRTH").toLocalDate());
            return employee;
        }
    }

    public void insert0(Employee employee) {
        final String sql =
                "insert into Employee (firstName, lastName, dateOfBirth) values (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, Date.valueOf((employee.getDateOfBirth())));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert1(Employee employee) throws DataAccessException {
        String sql =
                "insert into Employee (firstName, lastName, dateOfBirth) values (?, ?, ?)";
        getJdbcTemplate().update(sql, (PreparedStatement ps) -> {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
        });
    }

    public void insert2(Employee employee) throws DataAccessException {
        String sql =
                "insert into Employee (firstName, lastName, dateOfBirth) values (?, ?, ?)";
        getJdbcTemplate().update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                Date.valueOf(employee.getDateOfBirth()));
    }

    @Override
    public void insert(Employee employee) throws DataAccessException {
        // sql -> effectively final
        final String sql =
                "insert into Employee (firstName, lastName, dateOfBirth) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
                return ps;
            }
        }, keyHolder);

        employee.setId(keyHolder.getKey().longValue());
    }

    private void update(Employee employee) throws DataAccessException {
        final String sql = "update EMPLOYEE set FIRSTNAME=?, LASTNAME=?, DATEOFBIRTH=? where ID=?";
        getJdbcTemplate().update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                Date.valueOf(employee.getDateOfBirth()),
                employee.getId());
    }

    @Override
    public Employee merge(Employee employee) throws DataAccessException {
        if (employee.getId() == null) {
            insert(employee);
        } else {
            update(employee);
        }
        return employee;
    }

    @Override
    public Employee findById(Long id) throws DataAccessException {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE WHERE ID = ?";
        var args = new Object[]{id};
        List<Employee> employees = getJdbcTemplate().query(sql, args, new EmployeeMapper());
        if (employees.size() == 0) {
            return null;
        } else if (employees.size() == 1) {
            return employees.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, employees.size());
        }
    }

    @Override
    public List<Employee> findAll() throws DataAccessException {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE";

        return getJdbcTemplate().query(sql, new EmployeeMapper());
    }
}
