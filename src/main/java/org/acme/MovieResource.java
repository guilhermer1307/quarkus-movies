package org.acme;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieRepository movieRepository;

    @GET
    public Response getAll() {
        List<Movie> movies = movieRepository.listAll();
        return Response.ok(movies).build();
    }

    @GET 
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        return movieRepository.findByIdOptional(id)
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET 
    @Path("title/{title}")
    public Response getByTitle(@PathParam("title") String title) {
        return movieRepository.find("title", title)
        .singleResultOptional()
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST 
    @Transactional
    public Response create(Movie movie) {

        try {
            movieRepository.persistAndFlush(movie);
        } catch (Exception e) {
            return Response.status(Status.CONFLICT).build();
        }
        
        if (movieRepository.isPersistent(movie)) {
            return Response.created(URI.create("/movies/" + movie.getId())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT 
    @Path("{id}") 
    @Transactional
    public Response update(@PathParam("id") Long id, Movie updatedMovie) {
        Movie persistentMovie = movieRepository.findById(id);

        if (persistentMovie == null) {
            return Response.status(Status.NOT_FOUND).build();
        } 
        
        if (updatedMovie == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } 
        
        persistentMovie.setTitle(updatedMovie.getTitle());   
        persistentMovie.setDescription(updatedMovie.getDescription());
        persistentMovie.setDirector(updatedMovie.getDirector());
        persistentMovie.setCountry(updatedMovie.getCountry());

        return Response.ok(persistentMovie).build();
    }

    @DELETE 
    @Path("{id}")
    @Transactional 
    public Response deleteById(@PathParam("id") Long id) {
        boolean deleted = movieRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
}