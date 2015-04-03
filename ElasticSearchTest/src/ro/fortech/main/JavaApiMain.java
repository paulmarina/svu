package ro.fortech.main;

import java.io.IOException;

import ro.fortech.business.MovieController;
import ro.fortech.model.Movie;

public class JavaApiMain {

	public static void main(String[] args) throws IOException {

		/*
		 * Node node = nodeBuilder().clusterName("mormon").node(); Client client
		 * = node.client();
		 */

		MovieController movieCtrl = new MovieController();
		Movie movie = new Movie("The Mermillo", "Spartacus", 2555, 2);
		/*
		 * Movie movie2 = new Movie("The Avengers", "Marvel", 2012, 17); Movie
		 * movie3 = new Movie("IronMan", "Robert", 2013, 12); Movie movie4 = new
		 * Movie("Superman2", "Director", 2012, 13); Movie movie5 = new
		 * Movie("Captain America", "Dir", 2012, 14); Movie movie6 = new
		 * Movie("Hulk", "Green", 2011, 15);
		 */
		/*
		 * client.prepareIndex(Constants.MOVIE_INDEX, Constants.MOVIE_TYPE,
		 * String.valueOf(movie.getId()))
		 * .setSource(movieCtrl.createJsonDocument(movie)).execute()
		 * .actionGet();
		 */

		// movieCtrl.updateDocument(movie);

		// movieCtrl.deleteDocument("11");

		// node.close();

		movieCtrl.upsertDocument(movie);
		// movieCtrl.searchDocument();
		// movieCtrl.getDocument(13);

		/*
		 * client.close(); node.close();
		 */
	}

}
