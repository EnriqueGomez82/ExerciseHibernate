package Act4_3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;
import pojos.Teachers;

public class QueryDep {

    static SessionFactory sessionf = NewHibernateUtil.getSessionFactory();

    public static void showDepartment(Departments dep) {
        // Prints in the console the department number, the department name, the office and the number of teachers belonging to the department. 
        Session sesion = sessionf.openSession();
        System.out.println("ID: " + dep.getDeptNum() + " Name: " + dep.getName() + " Office: " + dep.getOffice() + " Profesores: " + dep.getTeacherses().size());
        sesion.close();
    }

    public static Departments[] getAllDepartments() {
        //Uses a query in HQL to retrieve all Departments.
        Departments misDepartamentos[];
        Session sesion = sessionf.openSession();
        Query q = sesion.createQuery("from Departments");
        List<Departments> lista = q.list();
        misDepartamentos = new Departments[lista.size()];
        misDepartamentos = lista.toArray(misDepartamentos);
        //System.out.println(Arrays.toString(misDepartamentos));
        //sesion.close();
        return misDepartamentos;
    }

    public static Departments getDepartmentByName(String nombre) {
        /* Uses a query in HQL to retrieve the department whose name is LIKE the pattern introduced as argument. 
           Use parameters with the :name syntax. Use .uniqueResult(). 
           Be careful if several results are returned, catch the exception and return only the first result. */
        Session sesion = sessionf.openSession();
        Query q = sesion.createQuery("from Departments where name = :nombre");
        q.setString("nombre", nombre);
        //sesion.close(); // no se como cerrar la sesion en este metodo
        return (Departments) q.uniqueResult();
    }

    public static double getAverageSalaryofDepartment(String depName) {
        /* Uses a query in HQL to obtain the average salary of the department whose name is THE SAME AS the name introduced as argument. 
           Use parameters with the :name syntax. Use .uniqueResult(). */
        Session sesion = sessionf.openSession();
        Query que = sesion.createQuery("from Teachers t where t.departments.name =:depName");
        que.setString("depName", depName);
        List<Teachers> listaT = que.list();
        int totalCash = 0;
        double average = 0;
        int prof = 0;
        for (Teachers profesores : listaT) {
            if (profesores.getSalary() != null && profesores.getDepartments().getName().equalsIgnoreCase(depName)) {
                totalCash += profesores.getSalary();
                prof++;
            }
        }
        try {
            average = (totalCash / prof);
        } catch (ArithmeticException e) {
            average = 0;
        }
        sesion.close();
        return average;
    }
//
//    public static void prueba() {
//// ESTE METODO ES UNA PRUEBA PARA LISTAR OBJETOS DIFERENTES        
//        Session sesion = sessionf.openSession();
//        Query q = sesion.createQuery("from Departments d, Teachers t where d.name=t.departments.name");
//        List<Object[]> lista = q.list();
//        System.out.println(lista.size());
//        for (Object[] elemento : lista) {
//
//            Departments dep = (Departments) elemento[0];
//            Teachers tea = (Teachers) elemento[1];
//
//            System.out.println("Departamento: " + dep.getName() + " Salario: " + tea.getSalary());
//        }
//    }

    public static HashMap<String, Double> getAverageSalaryPerDept() {
        /* Uses a query in HQL to obtain the name of each department and its average salary. 
            The result is returned in a HashMap that relates the name of the department with the average salary.*/
        Session sesion = sessionf.openSession();
        Query q = sesion.createQuery("select departments.name,avg(salary) from Teachers group by departments.name");
        HashMap<String, Double> milista = new HashMap<>();

        List<Object[]> lista = q.list();
        for (Object[] obj : lista) {
            milista.put(obj[0].toString(), (Double) obj[1]);
        }
        System.out.println("=======================");
        for (Map.Entry<String, Double> mil : milista.entrySet()) {
            System.out.println("Departamento: " + mil.getKey() + " Salario Medio: " + mil.getValue());
        }
        System.out.println("=======================");

        return milista;
    }

} // FIN DE LA CLASE
