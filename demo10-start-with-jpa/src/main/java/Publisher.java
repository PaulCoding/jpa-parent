import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Publisher {
    @Id
    @GeneratedValue
    private long id;
    private String city;
    private String country;
    private String name;

    public Publisher(String city, String country, String name) {
        this.city = city;
        this.country = country;
        this.name = name;
    }

    public Publisher() {
    }

    //    //..NON OWNING SIDE indicated by mappedBy attribute
//    @OneToOne(mappedBy="publisher")
//    PublisherInfo info;




}
