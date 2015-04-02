package ro.fortech.main;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import ro.fortech.business.MovieController;
import ro.fortech.model.Movie;

public class JavaApiMain {

	public static void main(String[] args) throws IOException {

		Node node = nodeBuilder().node();
		Client client = node.client();

		MovieController movieCtrl = new MovieController();
		Movie movie = new Movie("Thor", "Director8", 2013, 16);
		Movie movie2 = new Movie("The Avengers", "Marvel", 2012, 11);
		Movie movie3 = new Movie("IronMan", "Robert", 2013, 12);
		Movie movie4 = new Movie("Superman2", "Director", 2012, 13);
		Movie movie5 = new Movie("Captain America", "Dir", 2012, 14);
		Movie movie6 = new Movie("Hulk", "Green", 2011, 15);

		/*client.prepareIndex("movies", "movie", String.valueOf(movie.getId()))
				.setSource(movieCtrl.createJsonDocument(movie4)).execute()
				.actionGet();
*/
		// client.prepareIndex("movies",
		// "movie","2").setSource(movieCtrl.putJsonDocument(movie2)).execute().actionGet();

		// movieCtrl.updateDocument(movie);

		 movieCtrl.deleteDocument("movies", "movie", "11");

		// node.close();

		// movieCtrl.upsertDocument(movie4);
		//movieCtrl.searchDocument("movies", "movie");
		// movieCtrl.getDocument("movies", "movie", 13);
	}

}
