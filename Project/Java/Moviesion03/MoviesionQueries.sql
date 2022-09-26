
SELECT * FROM Movie
LEFT JOIN MovieGenre ON Movie.Id = MovieId;

SELECT * FROM Genre
LEFT JOIN MovieGenre ON Genre.Id = GenreId
LEFT JOIN Movie ON Movie.Id = MovieId