package me.jimmyshaw.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mCompany;
    private TextView mBlog;
    private TextView mHtmlUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCompany = (TextView) findViewById(R.id.text_comp);
        mBlog = (TextView) findViewById(R.id.text_blog);
        mHtmlUrl = (TextView) findViewById(R.id.text_hurl);

        MInterface restInterface = MInterface.restAdapter.create(MInterface.class);

        Call<Pojo> call = restInterface.getUser();
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if (response.isSuccessful()) {
                    Pojo result = response.body();

                    mCompany.setText(getString(R.string.company_name, result.getName()));
                    mBlog.setText(getString(R.string.company_blog, result.getBlog()));
                    mHtmlUrl.setText(getString(R.string.company_url, result.getHtmlUrl()));

                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                t.getMessage();
                t.printStackTrace();
            }
        });
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
