package ro.fortech.business;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import ro.fortech.interfaces.MovieControllerInterface;
import ro.fortech.model.Movie;
import ro.fortech.utils.Constants;

public class MovieController implements MovieControllerInterface {

	Node node;
	Client client;

	public MovieController() {

		this.node = nodeBuilder().clusterName("mormon").node();
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

		updateRequest.index(Constants.MOVIE_INDEX);
		updateRequest.type(Constants.MOVIE_TYPE);
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

		client.close();

	}

	public void upsertDocument(Movie movie) {

		IndexRequest indexRequest = new IndexRequest(Constants.MOVIE_INDEX,
				Constants.MOVIE_TYPE, String.valueOf(movie.getId()))
				.source(createJsonDocument(movie));
		UpdateRequest updateRequest = new UpdateRequest(Constants.MOVIE_INDEX,
				Constants.MOVIE_TYPE, String.valueOf(movie.getId())).doc(
				createJsonDocument(movie)).upsert(indexRequest);
		try {
			client.update(updateRequest).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.close();
	}

	public Map<String, Object> getDocument(String id) {

		GetResponse getResponse = client
				.prepareGet(Constants.MOVIE_INDEX, Constants.MOVIE_TYPE, id)
				.execute().actionGet();
		Map<String, Object> source = getResponse.getSource();

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");

		client.close();

		return source;

	}

	public void deleteDocument(String id) {

		client.prepareDelete(Constants.MOVIE_INDEX, Constants.MOVIE_TYPE, id)
				.execute().actionGet();
		client.close();

	}

	public List<Movie> searchDocument(String column, String value) {

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

		SearchResponse response;

		if (value == null && column == null) {
			response = client.prepareSearch().setTypes(Constants.MOVIE_TYPE)
					.execute().actionGet();

		} else {

			response = client.prepareSearch().setTypes(Constants.MOVIE_TYPE)
					.setQuery(QueryBuilders.matchQuery(column, value))
					.execute().actionGet();
		}

		List<Movie> result = new ArrayList<Movie>();

		SearchHit[] results = response.getHits().getHits();
		for (SearchHit hit : results) {
			Map<String, Object> partialResult = hit.getSource();
			
			for (Map.Entry<String, Object> entry : partialResult.entrySet()) {
				String localTitle = "";
				String localDirector = "";
				int localId = 0;
				int localYear = 0;
				if(entry.getKey().equals("title")){
					localTitle = entry.getValue().toString();
				} else if(entry.getKey().equals("director")){
					localDirector = entry.getValue().toString();
				} else if(entry.getKey().equals("id")){
					localId = Integer.parseInt(entry.getValue().toString());
				} else if(entry.getKey().equals("year")){
					localYear = Integer.parseInt(entry.getValue().toString());
				}
				
				Movie movie = new Movie(localTitle, localDirector, localYear, localId);
				result.add(movie);
			}
			
			System.out.println(hit.getType());
			System.out.println(result);
		}

		client.close();
		return result;
	}

	public List<Movie> searchDocument() {
		return searchDocument(null, null);
	}
}
