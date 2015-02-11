package co.mobilemakers.gtad;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskListFragment extends ListFragment {

    final static String PROJECT_ID = "PROJECT_ID";
    ArrayAdapter<TodolyTask> mAdapter;
    TodolyService.ApiInterface mTodolyApiInterface;
    private String mProjectId;

    public TaskListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TodolyService todolyService = new TodolyService(getString(R.string.todoly_username), getString(R.string.todoly_password));
        mTodolyApiInterface = todolyService.generateServiceInterface();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retrieveProjectId();
        prepareListView();
    }

    private void prepareListView() {
        List<TodolyTask> tasks = new ArrayList<>();
        mAdapter = new ArrayAdapter<TodolyTask>(getActivity(), R.layout.list_item_task,
                R.id.text_view_task_name,tasks);
        setListAdapter(mAdapter);
    }

    private void retrieveProjectId() {
        if(getArguments().containsKey(PROJECT_ID)){
            mProjectId = getArguments().getString(PROJECT_ID);
        }else{
            mProjectId = "";
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mTodolyApiInterface.getTasksForProject(mProjectId, new Callback<List<TodolyTask>>() {
            @Override
            public void success(List<TodolyTask> todolyTasks, Response response) {
                mAdapter.clear();
                mAdapter.addAll(todolyTasks);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
