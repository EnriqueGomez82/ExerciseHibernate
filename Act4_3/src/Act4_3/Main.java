package Act4_3;

import java.util.Scanner;
import org.hibernate.SessionFactory;
import pojos.Departments;
import pojos.Teachers;

public class Main {

    static SessionFactory sessionf = NewHibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

        Scanner tcl = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);

        Departments[] deps;
        Teachers[] teas;

        boolean salir = false;
        int op = 0;
        System.out.println();
        String bienvenida[] = new String[]{
            "Show all departments",
            "Show department whose name matches a pattern",
            "Get average salary of a department (by name)",
            "Show average salary of each department",
            "Show all teachers",
            "Show most veteran teacher",
            "Set salary",
            "Rise salary of senior teachers",
            "Delete teachers of a department",
            "Quit"
        };

        String name;
        String comercio = "comercio";

        while (!salir) {
            mostrarMenu(bienvenida);

            op = tcl.nextInt();
            switch (op) {
                case 1: // PERFECTO
                    deps = QueryDep.getAllDepartments();
                    System.out.println("=======================");
                    for (Departments pepe : deps) {
                        QueryDep.showDepartment(pepe);
                    }
                    System.out.println("=======================");
                    break;
                case 2: // PERFECTO
                    System.out.println("DIME NOMBRE DEL DEPARTAMENTO A MOSTRAR");
                    name = sc.nextLine();
                    System.out.println("=======================");
                    QueryDep.showDepartment(QueryDep.getDepartmentByName(name));
                    System.out.println("=======================");
                    break;
                case 3: // PERFECTO
                    System.out.println("DIME NOMBRE DEL DEPARTAMENTO A MOSTRAR MOSTRARE EL SALARIO MEDIO DEL DEPARTAMENTO");
                    name = sc.nextLine();
                    System.out.println("=======================");
                    System.out.println("Salario promedio: " + QueryDep.getAverageSalaryofDepartment(name));
                    System.out.println("=======================");
                    break;
                case 4: // PERFECTO
                    QueryDep.getAverageSalaryPerDept();
                    break;
                case 5: // PERFECTO
                    teas = QueryTeach.getAllTeachers();
                    System.out.println("=======================");
                    for (Teachers pepe : teas) {
                        QueryTeach.showTeacher(pepe);
                    }
                    System.out.println("=======================");
                    break;
                case 6: // PERFECTO
                    QueryTeach.getMostVeteranTeacher();
                    break;
                case 7: // PERFECTO PERO NO USAR PORQUE IGUALA SALARIO TODOS LOS PROFESORES Y EL METODO CASE 3 YA NO TIENE SENTIDO
                    QueryTeach.setSalary(3000); // iguala el salario a todos los profesores
                    break;
                case 8: // PERFECTO
                    QueryTeach.riseSalaryOfSeniors(30, 20);
                    break;
                case 9: // PERFECTO 
                    QueryTeach.deleteTeachersOfDepartment(comercio);
                    break;
                case 10:// PERFECTO
                    salir = true;
                    System.out.println("SALIENDO DEL PROGRAMA");
                    sessionf.close();
                    break;
                default:
                    if (salir = false) {
                        menu();
                    }
            }
        }
    }// Fin menu

    public static void mostrarMenu(String[] menu) {
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ": " + menu[i]);
        }
    }// FIN METODO mostrarMenu
}
