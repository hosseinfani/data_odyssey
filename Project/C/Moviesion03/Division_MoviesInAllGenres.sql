SELECT MovieId, GenreId
FROM MovieGenre 
WHERE MovieId NOT IN (
SELECT M.Id AS MovieId
FROM Movie M, Genre G
WHERE (M.Id, G.Id) NOT IN (
	SELECT MovieId, GenreId
	FROM MovieGenre
	)
)
