package ca.ryerson.ee.coe848.cmn;
/**
 *
 * @author Hossein Fani
 */
public class Director {    
    Integer id;
    String name;
    //other attributes/fields/properties

    public Director(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Director() {}

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
