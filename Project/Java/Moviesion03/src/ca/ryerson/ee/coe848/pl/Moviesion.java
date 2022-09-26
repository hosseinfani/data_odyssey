package ca.ryerson.ee.coe848.pl;

import ca.ryerson.ee.coe848.cmn.Director;
import ca.ryerson.ee.coe848.cmn.Movie;
import ca.ryerson.ee.coe848.dal.MovieManager;
import ca.ryerson.ee.coe848.dal.SQLiteDatabaseManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hossein Fani
 */
public class Moviesion {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String commandList = 
                  "1) Add Movie\n"
                + "2) Print Movie by Id\n"
                + "3) *Print Movie by Name\n"
                + "4) *Print All Movies\n"
                + "5) *Print All Movies by a Director\n"
                + "6) *Edit Movie by Id\n"
                + "7) Delete Movie by Id\n"
                + "8) Get Movie Count\n"
                + "0) Quit\n"
                + "Enter command: ";
        System.out.println(commandList);
        boolean running = true;
        Integer commandId;
        while (running) {
            try {
                try {
                    commandId = Integer.parseInt(console.nextLine());
                } catch (Exception ex) {
                    System.out.println("Command not recognized!");
                    commandId = -1;
                }
                switch (commandId) {

                    case -1:
                        break;

                    case 0:
                        System.out.println("Bye.");
                        running = false;
                        break;
                    case 1:
                        addMovie(console);
                        break;
                    case 2:
                        printMovieById(console);
                        break;
                    case 3:
                        printMovieByName(console);
                        break;
                    case 4:
                        printAllMovies(console);
                        break;
                    case 5:
                        printAllMoviesByDirector(console);
                        break;
                    case 6:
                        editMovieById(console);
                        break;
                    case 7:
                        deleteMovieById(console);
                        break;
                    case 8:
                        getMovieCount();
                        break;
                    default:
                        System.out.println("Command not recognized!");
                        break;
                }
                System.out.println(commandList);
                        
            } catch (Exception ex) {
                System.out.println("Command failed!");
                ex.printStackTrace();
                System.out.println(commandList);
            }
        }
        console.close();
        
    }

    private static void addMovie(Scanner console) throws Exception {
        System.out.println("Enter movie title:");
        String movieTitle = console.nextLine();
        Movie newMovie = new Movie();
        newMovie.setTitle(movieTitle);
        
        System.out.println("Enter directors name (comma separated): ");
        String[] directorsName = console.nextLine().split(",");
        ArrayList<Director> newDirectors = new ArrayList<>();
        for(String s: directorsName)
            newDirectors.add(new Director(null, s.trim()));
        
        //Relationship between movie and director
        newMovie.setDirectors(newDirectors);
        
        MovieManager mm = new MovieManager();
        mm.add(newMovie);
        System.out.println(
            String.format("Movie '%s' has been added with Id='%s'", 
                    newMovie.getTitle(), 
                    newMovie.getId()));
        
    }

    private static void printMovieByName(Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void printMovieById(Scanner console) throws IOException, Exception {
        System.out.println("Enter movie id:");
        Integer id;
        try {
            id = Integer.parseInt(console.nextLine());
        } catch (Exception ex) {
            System.out.println("Movie id not recognized!");
            return;
        }
        MovieManager mm = new MovieManager();
        Movie m = mm.getById(id);
        
        if(m != null)
            System.out.println(m.toString());
        else
            System.out.println("Movie not found!");
    }
    
    private static void printAllMovies(Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void printAllMoviesByDirector(Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void deleteMovieById(Scanner console) throws IOException, Exception {
        System.out.println("Enter movie id:");
        Integer id;
        try {
            id = Integer.parseInt(console.nextLine());
        } catch (Exception ex) {
            System.out.println("Movie id not recognized!");
            return;
        }
        MovieManager mm = new MovieManager();
        Movie m = mm.getById(id);
        if(m != null){
            mm.deleteById(m.getId());
            System.out.println("Movie is deleted!");
        }else{
            System.out.println("Movie not found!");
        }
    }

    private static void editMovieById(Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void getMovieCount() throws Exception {
        MovieManager mm = new MovieManager();
        int movieCount = 0;
        movieCount = mm.getMovieCountByDb();
        movieCount = mm.getMovieCountByApp();
        System.out.println(String.format("Movie Count is: %d", movieCount));
    }
}
