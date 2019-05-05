package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

@SuppressWarnings("Duplicates")
public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testJdbc() {

        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

            printTitle("create schema", 60, '-');
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

            EmployeeDao employeeDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

            printTitle("insert employee", 60, '-');
            Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
            employeeDao.insert(empl1);
            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

            printTitle("update employee", 60, '-');
            empl1.setFirstName("Jaquira");
            empl1 = employeeDao.merge(empl1);
            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

            printTitle("find employee", 60, '-');
            Employee empl = employeeDao.findById(1L);
            System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
            empl = employeeDao.findById(100L);
            System.out.println("empl=" + (empl == null ? (null) : empl.toString()));

            printTitle("find all employees", 60, '-');
            employeeDao.findAll().forEach(System.out::println);
        }
    }

    @SuppressWarnings("unused")
    private static void testJpa() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            EmployeeDao employeeDao = factory.getBean("employeeDaoJpa", EmployeeDao.class);

            Long empl1Id;
            try {
                JpaUtil.beginTransaction(emFactory);
                printTitle("insert employee", 60, '-');
                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                employeeDao.insert(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

                printTitle("update employee", 60, '-');
                empl1.setFirstName("Jaquira");
                empl1 = employeeDao.merge(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

                empl1Id = empl1.getId();
            } finally {
                JpaUtil.commitTransaction(emFactory);
            }


            JpaUtil.executeInTransaction(emFactory, () -> {
                printTitle("find employee", 60, '-');
                Employee empl = employeeDao.findById(empl1Id);
                System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
                empl = employeeDao.findById(100L);
                System.out.println("empl=" + (empl == null ? (null) : empl.toString()));

                printTitle("find all employees", 60, '-');
                employeeDao.findAll().forEach(System.out::println);
            });
        }
    }

    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);

            AtomicReference<Long> empl1Id = new AtomicReference<>();
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

                printTitle("insert employee", 60, '-');
                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                empl1 = employeeRepo.save(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

                Employee empl2 = new Employee("Franz", "Klammer", LocalDate.of(1950, 10, 26));
                empl2 = employeeRepo.save(empl2);
                System.out.println("empl2 = " + (empl2 == null ? (null) : empl2.toString()));


                printTitle("update employee", 60, '-');
                empl1.setFirstName("Jaquira");
                empl1 = employeeRepo.save(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

                empl1Id.set(empl1.getId());
            });

            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

                printTitle("find employee", 60, '-');
                Optional<Employee> empl = employeeRepo.findById(empl1Id.get());
                System.out.println("empl1=" + (empl.isEmpty() ? (null) : empl.get()));
                Optional<Employee> empl2 = employeeRepo.findById(100L);
                System.out.println("empl2=" + (empl.isEmpty() ? (null) : empl.get()));

                printTitle("find all employees", 60, '-');
                employeeRepo.findAll().forEach(System.out::println);
            });
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

                printTitle("findOlderThan", 60, '-');
                employeeRepo.findOlderThan(LocalDate.of(1960, 1, 1))
                        .forEach(System.out::println);
            });
        }
    }

    public static void main(String[] args) {

        printSeparator(60);
        printTitle("testJDBC", 60);
        printSeparator(60);
        testJdbc();

        printSeparator(60);
        printTitle("testJpa", 60);
        printSeparator(60);
        testJpa();

        printSeparator(60);
        printTitle("testSpringData", 60);
        printSeparator(60);
        testSpringData();
    }
}
