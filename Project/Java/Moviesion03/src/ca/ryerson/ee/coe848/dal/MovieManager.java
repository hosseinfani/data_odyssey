package ca.ryerson.ee.coe848.dal;

import ca.ryerson.ee.coe848.cmn.Director;
import ca.ryerson.ee.coe848.cmn.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Hossein Fani
 */
public class MovieManager {

    public void add(Movie newMovie) throws ClassNotFoundException, Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
        
            //sql insert command
            String sqlMovie = "INSERT INTO Movie(Title) VALUES(?)";

            //to add readability to code and avoid sql injection in command we use "prepareStatement()"
            ps = db.prepareStatement(sqlMovie, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newMovie.getTitle());

            //execute sql insert command
            int rowsAffected = ps.executeUpdate();

            //get movie id
            rs = ps.getGeneratedKeys();
            int movieId = 0;
            if (rs.next()) {
                movieId = rs.getInt(1);
            }

            ArrayList<Integer> directorIds = new ArrayList<>();
            for (Director d : newMovie.getDirectors()) {
                //sql insert command for each director
                String sqlDirector = "INSERT INTO Director(Name) VALUES(?)";

                ps = db.prepareStatement(sqlDirector, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, d.getName());

                rowsAffected = ps.executeUpdate();

                //get director id
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    directorIds.add(rs.getInt(1));
                }
            }

            //we could move this part inside director loop above
            for (Integer directorId : directorIds) {
                String sqlMovieDirector = "INSERT INTO MovieDirector(MovieId, DirectorId) VALUES(?, ?)";
                ps = db.prepareStatement(sqlMovieDirector);
                ps.setInt(1, movieId);
                ps.setInt(2, directorId);
                rowsAffected = ps.executeUpdate();
            }

            //commit work
            db.commit();
            
            newMovie.setId(movieId);
            for(int i = 0; i < directorIds.size(); ++i) {
                newMovie.getDirectors().get(i).setId(directorIds.get(i));
            }

        } catch (SQLException e) {
            if (db != null) {
                db.rollback();
            }
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public Movie getById(Integer id) throws SQLException, ClassNotFoundException, Exception {
        Movie m = null;
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
        
            String sqlMovie = "SELECT Id, Title FROM Movie WHERE Id = ?";

            ps = db.prepareStatement(sqlMovie);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            
            //since we expect only one movie, we do not use "while"
            if (rs.next()) {
                m = new Movie();
                m.setId(id);
                m.setTitle(rs.getString("Title"));
            }else
                return m;
            
            m.setDirectors(new ArrayList<>());
            String sqlDirectors = "SELECT Id, Name FROM Director WHERE Id IN (SELECT DirectorId FROM MovieDirector WHERE MovieId = ?)";

            ps = db.prepareStatement(sqlDirectors);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //here we expect more than one directors, so we use "while"
            while(rs.next()) {
                m.getDirectors().add(new Director(rs.getInt("Id"), rs.getString("Name")));
            }
            
            //no need for commit command
            //db.commit();
            return m;
            
            

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public void deleteById(Integer id) throws Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //be careful when deleting a movie. 
            //first delete the movie's relationship with directors or other entities if any
            String sqlMovieDirectors = "DELETE FROM MovieDirector WHERE MovieId = ?";
            ps = db.prepareStatement(sqlMovieDirectors);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            //then delete the movie itself
            String sqlMovie = "DELETE FROM Movie WHERE Id = ?";
            ps = db.prepareStatement(sqlMovie);
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
            
            if(rowsAffected < 0)
                throw new Exception("Movie not found in database!", null);
            
            db.commit();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public int getMovieCountByDb() throws ClassNotFoundException, Exception {
        int movieCount = 0;
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sqlMovieCount = "SELECT COUNT(*) AS MovieCount FROM Movie";
        try{
            ps = db.prepareStatement(sqlMovieCount);
            rs = ps.executeQuery();
            if(rs.next()){
                movieCount = rs.getInt("MovieCount");
            }
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(db != null)
                db.close();
        }
        return movieCount;
    }
    
    public int getMovieCountByApp() throws ClassNotFoundException, Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Integer> movieIds = new ArrayList<>();
        String sqlMovieCount = "SELECT Id FROM Movie";
        try{
            ps = db.prepareStatement(sqlMovieCount);
            rs = ps.executeQuery();            
            while(rs.next()){
                movieIds.add(rs.getInt("Id"));
            }
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(db != null)
                db.close();
        }
        return movieIds.size();
    }

}
