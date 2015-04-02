package ro.fortech.interfaces;

import java.util.Map;

import ro.fortech.model.Movie;

public interface MovieControllerInterface {

	public Map<String, Object> createJsonDocument(Movie movie);

	public void updateDocument(Movie movie);

	public Map<String, Object> getDocument(String id);

	public void deleteDocument(String id);

	public Map<String, Object> searchDocument(String column,String value);

	public Map<String, Object> searchDocument();
}
