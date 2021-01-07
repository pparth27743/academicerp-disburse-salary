package com.example.erp.dao.impl;

import com.example.erp.bean.Employee;
import com.example.erp.bean.EmployeeSalary;
import com.example.erp.dao.EmployeeSalaryDAO;
import com.example.erp.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeSalaryDAOImpl implements EmployeeSalaryDAO {

    @Override
    public boolean addEmployeeSalary(List<EmployeeSalary> employeesalary) {
        Session session = SessionUtil.getSession();
        try
        {
            session.beginTransaction();
            for(EmployeeSalary sal: employeesalary) {
                sal.setPayment_date(Date.valueOf(LocalDate.now()));
                session.save(sal);
            }
            session.getTransaction().commit();
            session.close();

            return true;
        }
        catch (HibernateException e){
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public List<EmployeeSalary> getAlreadyDisbursedSalList() {
        Session session=SessionUtil.getSession();
        try {
            LocalDate currentdate = LocalDate.now();
            int currentMonth= currentdate.getMonthValue();
            int currentYear=currentdate.getYear();

            Query query=session.createQuery("from EmployeeSalary where Year(payment_date)=:year and Month(payment_date)=:month");

            query.setParameter("year",currentYear);
            query.setParameter("month",currentMonth);

            return query.getResultList();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }finally {
            session.close();
        }
    }

    public List<EmployeeSalary> getSalRecords(Integer emp_id){
        Session session=SessionUtil.getSession();
        try {

            Query query=session.createQuery("from EmployeeSalary where employee.employee_id=:emp_id order by date(payment_date) desc");
            query.setParameter("emp_id",emp_id);

            return query.getResultList();
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }finally {
            session.close();
        }
    }

    public boolean addSalaryByRecords(EmployeeSalary empsal){
        Session session = SessionUtil.getSession();
        try
        {
            session.beginTransaction();
            empsal.setPayment_date(Date.valueOf(LocalDate.now()));
            session.save(empsal);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        catch(HibernateException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return false;
    }

}
