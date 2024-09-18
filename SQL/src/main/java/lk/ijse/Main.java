package lk.ijse;

import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Student;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(1001, "Saman Kumara", 21, "Galle", null);
        Student student2 = new Student(1002, "Kamal Kumara", 23, "Panadura", null);
        Student student3 = new Student(1003, "Nimal Kumara", 23, "Colombo", null);

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

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(batch);

        session.save(student1);
        session.save(student2);
        session.save(student3);

        transaction.commit();
        session.close();

        Session session2 = FactoryConfiguration.getInstance().getSession();
        Transaction transaction2 = session2.beginTransaction();


        //SQL Query - INSERT
        NativeQuery query = session2.createNativeQuery("INSERT INTO student VALUES(?,?,?,?,?)");
        query.addEntity(Student.class);
        query.setParameter(1, 1013);
        query.setParameter(2, "Saman Silva");
        query.setParameter(3, 27);
        query.setParameter(4, "Dompe");
        query.setParameter(5, 2001);

        System.out.println("Inserted : " + query.executeUpdate());

        //SQL Query - DELETE
        NativeQuery query1 = session2.createNativeQuery("DELETE FROM student WHERE sid=?");
        query1.addEntity(Student.class);
        query1.setParameter(1, 1012);

        System.out.println("Deleted : " + query1.executeUpdate());

        //SQL Query - UPDATE
        NativeQuery query2 = session2.createNativeQuery("UPDATE student SET sname=? WHERE sid=?");
        query2.addEntity(Student.class);
        query2.setParameter(1, "Janith Perera");
        query2.setParameter(2, "1011");

        System.out.println("Updated : " + query2.executeUpdate());

        // SQL Query - search by column name
        NativeQuery query3 = session2.createNativeQuery("SELECT * FROM student WHERE age=?");
        query3.addEntity(Student.class);
        query3.setParameter(1, 24); // Pass 24 as an integer

        System.out.println("Searched");

        List<Student> list = query3.list();


        for (Student student : list) {
            System.out.println(student);
        }


        //SQL Query - join query
        NativeQuery query4 = session2.createNativeQuery(
                "SELECT s.sid, s.sname, s.age, s.address, b.bid, b.bname FROM student s INNER JOIN batch b ON s.bid = b.bid"
        );

        List<Object[]> list1 = query4.list();

        for (Object[] row : list1) {
            Integer studentId = (Integer) row[0];
            String studentName = (String) row[1];
            String batchName = (String) row[5];

            System.out.println("Student ID: " + studentId);
            System.out.println("Student Name: " + studentName);
            System.out.println("Batch Name: " + batchName);
        }

        transaction2.commit();
        session2.close();

    }
}