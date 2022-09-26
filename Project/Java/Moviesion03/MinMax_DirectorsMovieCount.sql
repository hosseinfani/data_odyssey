--MIN(MovieCount)
SELECT *
FROM Director D
WHERE D.MovieCount NOT IN (
	SELECT D1.MovieCount 
	FROM Director D1, Director D2
	WHERE D1.MovieCount > D2.MovieCount
	)

--MAX(MovieCount)
SELECT *
FROM Director D
WHERE D.MovieCount NOT IN (
	SELECT D1.MovieCount 
	FROM Director D1, Director D2
	WHERE D1.MovieCount < D2.MovieCount
	)