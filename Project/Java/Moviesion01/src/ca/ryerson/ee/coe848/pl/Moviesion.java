package ca.ryerson.ee.coe848.pl;

import ca.ryerson.ee.coe848.cmn.Director;
import ca.ryerson.ee.coe848.cmn.Movie;
import java.util.ArrayList;
import java.util.Scanner;

public class Moviesion {
    public static void main(String[] args) {

        //Our in-memory movie database
        ArrayList<Movie> movies = new ArrayList<Movie>();
        
        //Auto increment id
        Integer movieId = 100;
        
        Scanner console = new Scanner(System.in);
        String commandList = 
                  "1) Add Movie\n"
                + "2) Print Movie by Id\n"
                + "3) *Print Movie by Title\n"
                + "4) *Print All Movies\n"
                + "5) Print All Movies of a Director\n"
                + "6) *Edit Movie by Id\n"
                + "7) Delete Movie by Id\n"
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
                        addMovie(movieId, movies, console);
                        movieId++;
                        break;
                    case 2:
                        printMovieById(movies, console);
                        break;
                    case 3:
                        printMovieByName(movies, console);
                        break;
                    case 4:
                        printAllMovies(movies, console);
                        break;
                    case 5:
                        printAllMoviesByDirector(movies, console);
                        break;
                    case 6:
                        editMovieById(movies, console);
                        break;
                    case 7:
                        deleteMovieById(movies, console);
                        break;
                    default:
                        System.out.println("Command not recognized!");
                        break;
                }
                System.out.println(commandList);
            } catch (Exception ex) {
                System.out.println("Command failed!\n" + ex.getMessage());
                System.out.println(commandList);
            }
        }
        console.close();
    }

    private static void addMovie(Integer movieId, ArrayList<Movie> movies, Scanner console) {
        
        System.out.println("Enter movie title:");
        String movieTitle = console.nextLine();
        Movie newMovie = new Movie();
        newMovie.setTitle(movieTitle);
        newMovie.setId(movieId);
        
        System.out.println("Enter director name: ");
        String directorName = console.nextLine();
        Director newDirector = new Director();
        newDirector.setName(directorName);
        
        //Relationship between movie and director
        newMovie.setDirector(newDirector);
        
        //Add to our in-memory movie database
        movies.add(newMovie);
        System.out.println(
                String.format("Movie '%s' has been added with Id='%s'", 
                        newMovie.getTitle(), 
                        newMovie.getId()));
        
    }

    private static void printMovieByName(ArrayList<Movie> movies, Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void printMovieById(ArrayList<Movie> movies, Scanner console) {

        System.out.println("Enter movie id:");
        Integer id;
        try {
            id = Integer.parseInt(console.nextLine());
        } catch (Exception ex) {
            System.out.println("Movie id not recognized!");
            return;
        }
        
        for(Movie m: movies){
            if(m.getId() == id){
                System.out.println(m.toString());
                return;
            }
        }
        System.out.println("Movie not found!");
    }
    
    private static void printAllMovies(ArrayList<Movie> movies, Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private static void printAllMoviesByDirector(ArrayList<Movie> movies, Scanner console) {
        System.out.println("Enter director name: ");
        String dircetorName = console.nextLine();
        for(Movie m: movies){
            if(m.getDirector().getName().equalsIgnoreCase(dircetorName)){
                System.out.println(m.toString());
            }                
        }
    }

    private static void editMovieById(ArrayList<Movie> movies, Scanner console) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private static void deleteMovieById(ArrayList<Movie> movies, Scanner console) {
        System.out.println("Enter movie id:");
        Integer id;
        try {
            id = Integer.parseInt(console.nextLine());
        } catch (Exception ex) {
            System.out.println("Movie id not recognized!");
            return;
        }
        
        for(Movie m: movies){
            if(m.getId() == id){
                movies.remove(m);
                System.out.println("Movie is removed.");
                return;
            }
        }
        System.out.println("Movie not found!");
    }
}
