--if(A) then (B)
SELECT D.*
FROM Director D
WHERE (NOT PlaceOfBirth='USA') OR (DateOfBirth > 1950)

SELECT D.*
FROM Director D
WHERE (DateOfBirth IS NULL) OR (DateOfBirth > 1950)

SELECT D.*
FROM Director D
WHERE DateOfBirth > 1950