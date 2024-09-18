package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    private int sid;
    @Column(name = "sname")
    private String sname;
    @Column(name = "age")
    private int age;
    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "bid", referencedColumnName = "bid", nullable = false)
    private Batch batch;
}
