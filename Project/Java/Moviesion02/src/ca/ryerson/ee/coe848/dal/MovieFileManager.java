/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ryerson.ee.coe848.dal;

import ca.ryerson.ee.coe848.cmn.Director;
import ca.ryerson.ee.coe848.cmn.Movie;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author hosseinfani
 */
public class MovieFileManager {
    private File file;

    public MovieFileManager(String movieFileName) throws IOException {
        this.file = new File(movieFileName);
        file.createNewFile();
    }

    public Integer add(Movie newMovie) throws IOException{
        try{
            Integer newId;
            
            //newId = getLastId() + 1;
            //LastId does not work!(why?)
            //delete last movie, add new movie!
            //edit a movie (delete the movie, add updated movie)
            
            newId = getMaxId() + 1;
            
            RandomAccessFile raf = new RandomAccessFile(file, "rw"); 
            //Append new movie to the end of file
            raf.seek(raf.length());
            //Tab separated values => \t
            //Line separated movies => \r\n
            //Why not comma separated values?
            
            //Movie's information
            raf.writeBytes(String.format("%d\t%s\t",
                    newId,
                    newMovie.getTitle()));
            
            //Movie's director information
            //Atomicity test: format exception! but we have partial writing in file!!
//            raf.writeBytes(String.format("%s\t%s\r\n",
//                    newMovie.getDirector().getName()));
//            
            //Bug fixed
            raf.writeBytes(String.format("%s\r\n",
                    newMovie.getDirector().getName()));
            raf.close();
            return newId;
        }catch(IOException ex){
            throw ex;
        }
    }
    
    public Movie getById(Integer id) throws FileNotFoundException, IOException, Exception {
        if(id <= 0)
            return null;
        
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String line;
        while ((line = raf.readLine()) != null) {
            String[] lineParts = line.split("\t");
            if (Integer.parseInt(lineParts[0]) == id) {
                Movie m = new Movie();
                m.setId(id);
                m.setTitle(lineParts[1]);
                
                Director d = new Director();
                d.setName(lineParts[2]);
                
                m.setDirector(d);
                return m;
            }
        }
        raf.close();
        return null;
    }
    
    public void deleteById(Integer id) throws FileNotFoundException, IOException{
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fc = raf.getChannel();
        long oldPosition;
        String line;
        while (true) {
            oldPosition = fc.position();
            line = raf.readLine();
            if(line == null)
                break;
            String[] lineParts = line.split("\t");
            if (Integer.parseInt(lineParts[0]) == id) {
                byte[] b = lineParts[0].getBytes();
                for(int i = 0; i < b.length; ++i)
                    b[i] = "0".getBytes()[0];
                ByteBuffer bb = ByteBuffer.wrap(b);
                fc.write(bb, oldPosition);
                break;
            }
        }
        raf.close();
    }

    private Integer getLastId() throws FileNotFoundException, IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String line;
        Integer lastId = 0;
        while ((line = raf.readLine()) != null) {
            String[] lineParts = line.split("\t");
            //This program knows that the first piece of 
            //information is the movie Id. How about other programs? 
            lastId = Integer.parseInt(lineParts[0]);
        }
        raf.close();
        return lastId;
    }
    
    private Integer getMaxId() throws FileNotFoundException, IOException {
        RandomAccessFile fw = new RandomAccessFile(file, "rw");
        String line;
        Integer maxId = 0;
        while ((line = fw.readLine()) != null) {
            String[] lineParts = line.split("\t");
            //This program knows that the first piece of 
            //information is the movie Id. How about other programs? 
            Integer currentId = Integer.parseInt(lineParts[0]);
            if(currentId > maxId){
                maxId = currentId;
            }
        }
        fw.close();
        return maxId;
    }

    
}
