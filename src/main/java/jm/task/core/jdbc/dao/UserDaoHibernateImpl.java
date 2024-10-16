package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery
                    ("CREATE TABLE users(id bigserial not null, name VARCHAR(255), lastname VARCHAR(255), age int2, primary key (id));").executeUpdate();
            session.getTransaction().commit();
        } catch (SQLGrammarException e ){

        }catch (PersistenceException e){

        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery
                    ("DROP TABLE users;").executeUpdate();
            transaction.commit();
        }catch (PersistenceException e){

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Transaction transaction = null;
            User user = new User(name,lastName,age);
            try (Session session = Util.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                if(user != null){
                    session.save(user);
                }
                transaction.commit();
            }
        }catch (HibernateException e){

        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            User user = session.get(User.class, id);
            if(user != null){
                session.delete(user);
            }
            session.getTransaction().commit();
        }catch (HibernateException | EntityNotFoundException e){

        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("FROM User", User.class).list();
        }catch (HibernateException e){
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.createSQLQuery
                    ("TRUNCATE Users;").executeUpdate();
            session.getTransaction().commit();
        }catch (SQLGrammarException e){

        }
    }
}
