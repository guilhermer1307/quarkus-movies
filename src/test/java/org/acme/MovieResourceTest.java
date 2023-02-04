package org.acme;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import javax.ws.rs.core.Response;

@QuarkusTest
public class MovieResourceTest {

    @InjectMock
    MovieRepository movieRepository;

    @Inject 
    MovieResource movieResource;

    private Movie movie;
    
    @BeforeEach 
    void setUp() {
        movie = new Movie();
        movie.setTitle("Avatar");
        movie.setDescription("The movie");
        movie.setDirector("Any");
        movie.setCountry("Brazil");
        movie.setId(1L);
    }
    
    @Test
    void getAll() {
        List<Movie> movies = new ArrayList();
        movies.add(movie);
        Mockito.when(movieRepository.listAll()).thenReturn(movies);
        Response response = movieResource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Movie> entity = (List<Movie>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals("Avatar", entity.get(0).getTitle());
        assertEquals("The movie", entity.get(0).getDescription());
        assertEquals("Brazil", entity.get(0).getCountry());
        assertEquals("Any", entity.get(0).getDirector());
        assertEquals(1L, entity.get(0).getId());
    }

    
}
