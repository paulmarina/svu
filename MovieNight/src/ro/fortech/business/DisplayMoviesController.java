package ro.fortech.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ro.fortech.access.MovieAccess;
import ro.fortech.model.Movie;

@Stateless
public class DisplayMoviesController {
	
	@Inject
	private MovieAccess movieAccess;

	public List<Movie> displayMovies() {

		return movieAccess.searchDocument();

	}

}
