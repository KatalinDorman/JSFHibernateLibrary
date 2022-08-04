package pojos;
// Generated 2022.01.02. 10:22:49 by Hibernate Tools 4.3.1


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tag generated by hbm2java
 */
@Entity
@Table(name="tag"
    ,catalog="konyvtaras"
)
public class Tag  implements java.io.Serializable {


     private Integer id;
     private String nev;
     private String email;
     private List<Konyv> konyvs = new ArrayList<Konyv>(0);

    public Tag() {
    }

	
    public Tag(String nev, String email) {
        this.nev = nev;
        this.email = email;
    }
    public Tag(String nev, String email, List<Konyv> konyvs) {
       this.nev = nev;
       this.email = email;
       this.konyvs = konyvs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="nev", nullable=false, length=65)
    public String getNev() {
        return this.nev;
    }
    
    public void setNev(String nev) {
        this.nev = nev;
    }

    
    @Column(name="email", nullable=false, length=65)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="tag")
    public List<Konyv> getKonyvs() {
        return this.konyvs;
    }
    
    public void setKonyvs(List<Konyv> konyvs) {
        this.konyvs = konyvs;
    }




}


