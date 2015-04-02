package ro.fortech.main;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import ro.fortech.business.MovieController;
import ro.fortech.model.Movie;
import ro.fortech.utils.Constants;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class JavaApiMain {

	public static void main(String[] args) throws IOException {

		/*ImmutableSettings.Builder settings =ImmutableSettings.settingsBuilder();
		settings.put("node.name", "Garofita");
		Node node = nodeBuilder().settings(settings).clusterName("mormon").node();*/

		
		Node node = nodeBuilder().clusterName("mormon").node();
		Client client = node.client();
		

		MovieController movieCtrl = new MovieController();
		Movie movie = new Movie("Bomberman", "Me", 2013, 20);
	/*	Movie movie2 = new Movie("The Avengers", "Marvel", 2012, 17);
		Movie movie3 = new Movie("IronMan", "Robert", 2013, 12);
		Movie movie4 = new Movie("Superman2", "Director", 2012, 13);
		Movie movie5 = new Movie("Captain America", "Dir", 2012, 14);
		Movie movie6 = new Movie("Hulk", "Green", 2011, 15);
*/
		client.prepareIndex(Constants.MOVIE_INDEX, Constants.MOVIE_TYPE, String.valueOf(movie.getId()))
				.setSource(movieCtrl.createJsonDocument(movie)).execute()
				.actionGet();

				// movieCtrl.updateDocument(movie);

		// movieCtrl.deleteDocument("11");

		// node.close();

		// movieCtrl.upsertDocument(movie4);
		movieCtrl.searchDocument();
		// movieCtrl.getDocument(13);

		/*client.close();
		node.close();*/
	}

}
