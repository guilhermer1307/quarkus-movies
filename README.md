# Quarkus Movies

### List movies

**GET**
/movies

----

### Get movie by id

**GET**
/movies/{id}

----

### Get movie by title

**GET**
/movies/title/{title}

----

### Post movie

**POST**
/movies
```json
{
  "title": "Avatar",
  "description": "On the lush alien world of Pandora live the Na'vi, beings who appear primitive but are highly evolved",
  "director": "Peter Jackson",
  "country": "USA"
}
```

----

### Update movie

**PUT**
/movies/{id}
```json
{
  "title": "Avatar",
  "description": "On the lush alien world of Pandora live the Na'vi, beings who appear primitive but are highly evolved",
  "director": "Peter Jackson",
  "country": "USA"
}
```

----

### Delete movie

**DELETE**
/movies/{id}




