package ro.fortech.interfaces;

import java.util.Map;

import ro.fortech.model.Movie;

public interface MovieControllerInterface {

	public Map<String, Object> createJsonDocument(Movie movie);

	public void updateDocument(Movie movie);

	public Map<String, Object> getDocument(String index, String type, int id);

	public void deleteDocument(String index, String type, String id);

	public Map<String, Object> searchDocument(String index, String type);

}
