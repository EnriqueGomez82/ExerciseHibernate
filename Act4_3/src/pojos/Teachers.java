package pojos;

import java.util.Date;

public class Teachers implements java.io.Serializable {

    private int id;
    private Departments departments;
    private String name;
    private String surname;
    private String email;
    private Date startDate;
    private Integer salary;

    public Teachers() {
    }

    public Teachers(int id) {
        this.id = id;
    }

    public Teachers(int id, Departments departments, String name, String surname, String email, Date startDate, Integer salary) {
        this.id = id;
        this.departments = departments;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.startDate = startDate;
        this.salary = salary;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departments getDepartments() {
        return this.departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

}
