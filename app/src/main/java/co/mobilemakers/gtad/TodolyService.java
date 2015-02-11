package co.mobilemakers.gtad;

import android.util.Base64;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.Path;

/**
 * Created by diana.perez on 11/02/2015.
 */
public class TodolyService {

    final static String TODOLY_API_URL = "https://todo.ly/api";
    final static String PROJECTS_ENDPOINT = "/projects.json";
    final static String PROJECT_TASKS_ENDPOINT ="/projects/{id}/items.json";
    final static String ACCEPTED_DATA = "application/json";

    private String mUsername;
    private String mPassword;

    public interface ApiInterface {
        @GET(PROJECTS_ENDPOINT)
        void getProjects(Callback<List<TodolyProject>> callback);
        @GET(PROJECT_TASKS_ENDPOINT)
        void getTasksForProject(@Path("id")String id, Callback<List<TodolyTask>> callback);
    }

    public TodolyService(String username, String password){
        mUsername = username;
        mPassword = password;
    }

    public ApiInterface generateServiceInterface(){
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(TODOLY_API_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String authString = "Basic " +
                                Base64.encodeToString((mUsername + ": " + mPassword).getBytes(), Base64.NO_WRAP);
                        request.addHeader("Accept", ACCEPTED_DATA);
                        request.addHeader("Authorization", authString);
                    }
                });
        RestAdapter restAdapter = builder.setEndpoint(TODOLY_API_URL).build();
        ApiInterface service = restAdapter.create(ApiInterface.class);
        return service;
    }
}
