/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ryerson.ee.coe848.pl;

import ca.ryerson.ee.coe848.cmn.Director;
import ca.ryerson.ee.coe848.cmn.Movie;
import ca.ryerson.ee.coe848.dal.MovieFileManager;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author hosseinfani
 */
public class Moviesion {
    private static final String movieFileName = "Movies.txt";
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
                + "6) (Bug)Edit Movie by Id\n"
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
        
        //We do not know what is the max Id for movies in our database
        //MovieFileManger knows it
        //newMovie.setId(movieId);
        
        System.out.println("Enter director name: ");
        String directorName = console.nextLine();
        Director newDirector = new Director();
        newDirector.setName(directorName);
        
        //Relationship between movie and director
        newMovie.setDirector(newDirector);
        
        //Add to our movie database in file. 
        //MovieFileManger return the Id of the new movie if the addition was successfull
        //Otherwise it throws an exception
        MovieFileManager mfm = new MovieFileManager(movieFileName);
        Integer id = mfm.add(newMovie);
        newMovie.setId(id);
        
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
        MovieFileManager mfm = new MovieFileManager(movieFileName);
        Movie m = mfm.getById(id);
        
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

    private static void editMovieById(Scanner console) throws IOException, Exception {
        System.out.println("Enter movie id:");
        Integer id;
        try {
            id = Integer.parseInt(console.nextLine());
        } catch (Exception ex) {
            System.out.println("Movie id not recognized!");
            return;
        }
        MovieFileManager mfm = new MovieFileManager(movieFileName);
        Movie m = mfm.getById(id);
        if(m != null)
            System.out.println(m.toString());
        else{
            System.out.println("Movie not found!");
            return;
        }
        
        System.out.println("Enter movie's new title:");
        String movieTitle = console.nextLine();
        m.setTitle(movieTitle);
        
        System.out.println("Enter director name: ");
        String directorName = console.nextLine();
        m.getDirector().setName(directorName);
        
        mfm.deleteById(m.getId());
        Integer newId = mfm.add(m);//Wrong! How come?
        
        //Need to write a new function in MovieFileManger to handle edit
        //mfm.edit(m);
        
        Movie editedMovie = mfm.getById(m.getId());
        //editedMovie is null!
        
        editedMovie = mfm.getById(newId);
        System.out.println(editedMovie.toString());        
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
        MovieFileManager mfm = new MovieFileManager(movieFileName);
        Movie m = mfm.getById(id);
        if(m != null){
            mfm.deleteById(m.getId());
            System.out.println("Movie is deleted!");
        }else{
            System.out.println("Movie not found!");
        }
    }
}
