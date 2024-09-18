package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batch")
public class Batch {
    @Id
    private int bid;
    @Column(name = "bname")
    private String bname;
    @Column(name = "bdescription")
    private String bdescription;

    @OneToMany(mappedBy = "batch", fetch = FetchType.LAZY)
    private List<Student> students;
}
