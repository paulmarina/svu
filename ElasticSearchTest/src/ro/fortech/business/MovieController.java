package ro.fortech.business;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import ro.fortech.interfaces.MovieControllerInterface;
import ro.fortech.model.Movie;

public class MovieController implements MovieControllerInterface {

	Node node;
	Client client;

	public MovieController() {

		this.node = nodeBuilder().node();
		this.client = node.client();

	}

	public Map<String, Object> createJsonDocument(Movie movie) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put("title", movie.getTitle());
		jsonDocument.put("director", movie.getDirector());
		jsonDocument.put("year", movie.getYear());
		jsonDocument.put("id", movie.getId());
		return jsonDocument;
	}

	public void updateDocument(Movie movie) {

		UpdateRequest updateRequest = new UpdateRequest();

		updateRequest.index("movies");
		updateRequest.type("movie");
		updateRequest.id(String.valueOf(movie.getId()));
		updateRequest.doc(createJsonDocument(movie));
		try {
			client.update(updateRequest).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void upsertDocument(Movie movie) {

		IndexRequest indexRequest = new IndexRequest("movies", "movie",
				String.valueOf(movie.getId())).source(createJsonDocument(movie));
		UpdateRequest updateRequest = new UpdateRequest("movies", "movie",
				String.valueOf(movie.getId())).doc(createJsonDocument(movie))
				.upsert(indexRequest);
		try {
			UpdateResponse response = client.update(updateRequest).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, Object> getDocument(String index, String type, int id) {

		GetResponse getResponse = client
				.prepareGet(index, type, String.valueOf(id)).execute()
				.actionGet();
		Map<String, Object> source = getResponse.getSource();

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");

		return source;

	}

	public void deleteDocument(String index, String type, String id) {

		client.prepareDelete(index, type, id).execute()
				.actionGet();

	}

	public Map<String, Object> searchDocument(String index, String type) {

		/*
		 * SearchResponse response = client .prepareSearch("index")
		 * .setTypes("type") .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		 * .setQuery(QueryBuilders.termQuery("multi", "test")) // Query
		 * .setPostFilter(
		 * FilterBuilders.rangeFilter("year").from(2010).to(2015)) // Filter
		 * .setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		 */
		// SearchResponse response =
		// client.prepareSearch().setPostFilter(FilterBuilders.rangeFilter("year").from(2010).to(2015)).execute().actionGet();

		/*
		 * SearchResponse response = client.prepareSearch()
		 * .setQuery(QueryBuilders.matchQuery("year", 2012)).execute()
		 * .actionGet();
		 */
		SearchResponse response = client.prepareSearch().execute().actionGet();

		Map<String, Object> result = new HashMap<String, Object>();

		SearchHit[] results = response.getHits().getHits();
		for (SearchHit hit : results) {
			result = hit.getSource();
			System.out.println(hit.getType());
			System.out.println(result);
		}

		return result;
	}

}
