SELECT *, NULL AS MovieId, NULL AS MovieTitle
FROM Director D
WHERE D.Id NOT IN (
	SELECT D.Id
	FROM Director D, Movie M
	WHERE D.BestMovieId = M.Id
	)
UNION
SELECT D.*, M.Id, M.Title
FROM Director D, Movie M
WHERE D.BestMovieId = M.Id
