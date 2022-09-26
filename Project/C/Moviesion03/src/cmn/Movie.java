package ca.ryerson.ee.coe848.cmn;

import java.util.ArrayList;

/**
 *
 * @author Hossein Fani
 */
public class Movie {

    Integer id;
    String title;
    ArrayList<Director> directors;
    //other attributes/fields/properties

    public Movie(Integer id, String name, ArrayList<Director> director) {
        this.id = id;
        this.title = name;
        this.directors = director;
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
    public ArrayList<Director> getDirectors() {
        return directors;
    }
    public void setDirectors(ArrayList<Director> directors) {
        this.directors = directors;
    }
    @Override
    public String toString() {
        String result = "id=" + id + ", name=" + title + ", director=< ";
        for(Director d: this.directors){
            result += d.getName() + ", ";
        }
        result += " >";
        return result;        
    }
}
