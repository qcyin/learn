SELECT
FirstName, LastName, City, State
FROM
Person p Left join Address a ON p.PersonId = a.PersonId