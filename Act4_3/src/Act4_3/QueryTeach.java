package Act4_3;

//import static Act4_3.QueryDep.sessionf;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Departments;
import pojos.Teachers;

public class QueryTeach {

    static SessionFactory sessionf = NewHibernateUtil.getSessionFactory();

    public static void showTeacher(Teachers tea) {
        // Prints in the console the name, surname, email, startDate, salary and the name of the department  to which the teacher belongs.
        Session sesion = sessionf.openSession();
        System.out.println("Name: " + tea.getName() + " Surname: " + tea.getSurname() + " Email: " + tea.getEmail() + " Comienzo: " + tea.getStartDate() + " Salario: " + tea.getSalary() + " Departamento: " + tea.getDepartments().getName());
        sesion.close();
    }

    public static Teachers[] getAllTeachers() {
        //Uses a query in HQL to retrieve all Teachers.
        Session sesion = sessionf.openSession();
        Query q = sesion.createQuery("from Teachers");
        List<Teachers> lista = q.list();
        Teachers misTea[] = new Teachers[lista.size()];
        misTea = lista.toArray(misTea);
        //System.out.println(Arrays.toString(misTea));
        // sesion.close(); // No puedo cerrar sesion porque la cierro en showTeacher() entiendo
        return misTea;
    }

    public static Teachers getMostVeteranTeacher() {
        // Uses a query in HQL to retrieve the most veteran teacher (minimum start_date). Use .uniqueResult().
        Session sesion = sessionf.openSession();
        Teachers tea = new Teachers();
        Query q = sesion.createQuery("from Teachers where startDate = (select min(t.startDate) from Teachers t)");
        Teachers profesores = (Teachers) q.uniqueResult();
        System.out.println("=======================");
        System.out.println("Name: " + profesores.getName() + " Surname: " + profesores.getSurname() + " Email: " + profesores.getEmail() + " Comienzo: " + profesores.getStartDate() + " Salario: " + profesores.getSalary() + " Departamento: " + profesores.getDepartments().getName());
        System.out.println("=======================");
        return tea;
    }

    public static int setSalary(int newSalary) {
        //Uses a query in HQL to set the salary of all Teachers to the one introduced as argument. Use parameters with the :name syntax.
        Session sesion = sessionf.openSession();
        Transaction tx = sesion.beginTransaction();
        Query q = sesion.createQuery("update Teachers t set t.salary =:newSalary");
        q.setInteger("newSalary", newSalary);
        int filasModificadas = q.executeUpdate();
        tx.commit();
        System.out.println("=======================");
        System.out.println("Salario actualizado a: " + newSalary);
        System.out.println("=======================");
        return filasModificadas;
    }

    public static int riseSalaryOfSeniors(int numOfYearsToBeSenior, int prctRise) {
        /*  Uses a query in HQL to rise the salary in a percentage indicated by prctRise of those Teachers that have a seniority 
            of at least numOfYearToBeSenior to the one introduced as argument.
            The method returns the number of updated rows. Use parameters with the :name syntax.
            Check Hibernate manual to know how to work with dates. (11.4.5)  */

        Session sesion = sessionf.openSession();
        Transaction tx = sesion.beginTransaction();
        Query subYear = sesion.createSQLQuery("select DATE( DATE_SUB( CURDATE() , INTERVAL :interval YEAR ) ) from dual");
        subYear.setInteger("interval", numOfYearsToBeSenior);
        List<Date> subYearList = subYear.list();

        Query updateSalary = sesion.createQuery("update Teachers t set t.salary = t.salary + (t.salary * :porcent/100) where t.startDate < :senior and startDate is not null");
        updateSalary.setDouble("porcent", prctRise);
        updateSalary.setDate("senior", subYearList.get(0));
        int filasModificadas = updateSalary.executeUpdate();

        Query q = sesion.createQuery("from Teachers t where t.startDate < :senior and startDate is not null");
        q.setDate("senior", subYearList.get(0));
        List<Teachers> lista = q.list();

        for (Teachers profe : lista) {
            showTeacher(profe);
        }
        tx.commit();
        // sesion.close();
        return filasModificadas;
    }

    public static int deleteTeachersOfDepartment(String depName) {
        //Uses a query in HQL to delete all teachers belonging to the department whose name is THE SAME AS the name introduced as argument...Use parameters with the :name syntax.
        Session sesion = sessionf.openSession();
        Transaction tx = sesion.beginTransaction();
        Query q = sesion.createQuery("DELETE FROM Teachers t WHERE t.departments =:depName ");
        q.setParameter("depName", QueryDep.getDepartmentByName(depName));
        int resultado = q.executeUpdate();
        tx.commit();
        System.out.println("=======================");
        System.out.println("Profesores borrados de: " + depName);
        System.out.println("=======================");
        sesion.close();
        return resultado;
    }

    //--------------------ME GUSTARIA SABER COMO HACER ESTE METODO AUNQUE EL EJERCICIO NO LO PIDA-------------------¿copiar tablas?---------
//        public static void insertTeacher(){
//            Session sesion = sessionf.openSession();
//             Transaction transaction = null;
//        
//            // start a transaction
//            transaction = sesion.beginTransaction();
//
//            String hql = "INSERT INTO Teachers (6, 'paquito') " +
//                "SELECT id, name FROM Teachers";
//            Query query = sesion.createQuery(hql);
//            int result = query.executeUpdate();
//            System.out.println("Rows affected: " + result);
//
//            // commit transaction
//            transaction.commit();
//        
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
}// FIN DE LA CLASE
