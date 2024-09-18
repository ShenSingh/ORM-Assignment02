package lk.ijse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lk.ijse.config.JPQLFactoryConfiguration;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(1010, "Saman Kumara", 21, "Galle", null);
        Student student2 = new Student(1011, "Kamal Kumara", 23, "Panadura", null);
        Student student3 = new Student(1012, "Nimal Kumara", 23, "Colombo", null);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Batch batch = new Batch();
        batch.setBid(2001);
        batch.setBname("B010");
        batch.setBdescription("GDSE");
        batch.setStudents(students);

        student1.setBatch(batch);
        student2.setBatch(batch);
        student3.setBatch(batch);


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.createNativeQuery("CREATE TABLE IF NOT EXISTS batch (" +
                        "bid INT PRIMARY KEY, " +
                        "bname VARCHAR(255), " +
                        "bdescription VARCHAR(255))")
                .executeUpdate();

        em.createNativeQuery("CREATE TABLE IF NOT EXISTS student (" +
                        "sid INT PRIMARY KEY, " +
                        "sname VARCHAR(255), " +
                        "age INT, " +
                        "address VARCHAR(255), " +
                        "bid INT, " +
                        "FOREIGN KEY (bid) REFERENCES batch(bid))")
                .executeUpdate();

        em.getTransaction().commit();

        em.close();
        emf.close();


        EntityManager entityManager = JPQLFactoryConfiguration.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        //JPQL-INSERT
        entityManager.persist(batch);
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(student3);

        //JPQL-UPDATE
        entityManager.createQuery("UPDATE Student s SET s.sname = :newName WHERE s.sid = :studentId")
                .setParameter("newName", "Supun Perera")
                .setParameter("studentId", 1010)
                .executeUpdate();

        //JPQL-DELETE
        entityManager.createQuery("DELETE FROM Student s WHERE s.sid = :studentId")
                .setParameter("studentId", 1011)
                .executeUpdate();

        //JPQL-SEARCH
        List<Student> result = entityManager.createQuery("SELECT s FROM Student s WHERE s.batch.bid = :batchId", Student.class)
                .setParameter("batchId", 2010)
                .getResultList();

        for (Student student : result) {
            System.out.println(student.getSid());
            System.out.println(student.getSname());
            System.out.println(student.getAge());
            System.out.println(student.getAddress());
        }

        //JPQL-JOIN
        List<Object[]> resultList = entityManager.createQuery("SELECT s.sname, b.bname FROM Student s INNER JOIN s.batch b", Object[].class)
                .getResultList();

        for (Object[] objects : resultList) {
            System.out.println(objects[0]);
            System.out.println(objects[1]);
        }

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}