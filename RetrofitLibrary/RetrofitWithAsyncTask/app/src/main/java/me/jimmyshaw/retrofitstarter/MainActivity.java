package me.jimmyshaw.retrofitstarter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);

        mButton = (Button) findViewById(R.id.button);

        /* Use Android's AsyncTask class to asynchronously issue an HTTP request */
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
                final Call<List<Contributor>> call = gitHubService.repoContributors("guildsa", "androidstudentsamples");
                new NetworkCall().execute(call);
            }
        });

        /* Use Retrofit's own enqueue method to asynchronously issue an HTTP request */
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
//                final Call<List<Contributor>> call = gitHubService.repoContributors("guildsa", "androidstudentsamples");
//                call.enqueue(new Callback<List<Contributor>>() {
//                    @Override
//                    public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
//                        if (response.isSuccessful()) {
//                            List<Contributor> result = response.body();
//                            String resultString = result.toString();
//
//                            mTextView.setText(resultString);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Contributor>> call, Throwable t) {
//                        t.getMessage();
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });


    }

    // Android won't allow network calls on the UI thread. The UI thread should only handle user
    // input. Performing any long blocking operations on this thread will simply make the user
    // experience lethargic.
    // We need to move our network call to a background thread by using an AsyncTask, the default
    // way to perform expensive computations on Android. It's not the most efficient way, as it
    // creates our Retrofit object every time we push the button, but it works.
    private class NetworkCall extends AsyncTask<Call, Void, String> {

        @Override
        protected String doInBackground(Call... params) {
            try {
                Call<List<Contributor>> call = params[0];
                Response<List<Contributor>> response = call.execute();

                return response.body().toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.setText(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
