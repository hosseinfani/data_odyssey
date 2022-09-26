package ca.ryerson.ee.coe848.cmn;
public class Movie {

    Integer id;
    String title;
    Director director;
    //other attributes/fields/properties

    public Movie(Integer id, String name, Director director) {
        this.id = id;
        this.title = name;
        this.director = director;
    }
    public Movie() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Director getDirector() {
        return director;
    }
    public void setDirector(Director director) {
        this.director = director;
    }
    @Override
    public String toString() {
        return "id=" + id + ", name=" + title + ", director=" + director.getName();
    }
}
