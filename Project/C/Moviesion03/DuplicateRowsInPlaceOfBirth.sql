SELECT * 
FROM Director
WHERE PlaceOfBirth IN(
	SELECT PlaceOfBirth
	FROM Director
	GROUP BY PlaceOfBirth
	HAVING COUNT(*) > 1
	)
ORDER BY Id	

SELECT * 
FROM Director D1, Director D2
WHERE D1.Id <> D2.Id AND D1.PlaceOfBirth = D2.PlaceOfBirth 
ORDER BY Id	

