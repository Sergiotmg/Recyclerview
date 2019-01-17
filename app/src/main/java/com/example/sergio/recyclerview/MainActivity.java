package com.example.sergio.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();
    // ... Rest of MainActivity code ...
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Creo el mRecyclerView antes por si acaso porque en el onclick lo utilizamos

        // Creates the RecyclerView and connects it with an adapter and the data.
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager. NO OLVIDARSE DE ESTAS LINEAS!!!OJO!
        LinearLayoutManager manager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);//++Ojo! imprescindible
        //si no le decimos que tipo de layout tiene que utilizxar no sabe cual utilizar
        //conseguir unas lineas entre items del recycler
        DividerItemDecoration dividerItemDecoration=
                new DividerItemDecoration
                        (mRecyclerView.getContext(),manager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //declaro el tama lista
                int wordListSize = mWordList.size();
                // Add a new word to the wordList.
                mWordList.addLast("+ Palabra " + wordListSize);
                // Notify the adapter, that the data has changed.
                // en caso de que eso se a nulo se cerraria app
                if (mRecyclerView.getAdapter()!=null){
                    //solo hay que avisarle que ya hemos insertado
                    mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                    // Scroll to the bottom.
                    mRecyclerView.smoothScrollToPosition(wordListSize);
                }


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        // CREA TODA LA LISTA DE LOS N ITEMS
        createList();




    }

    //meterlo en un metodo aparte auese a creaete list y que en reset llame a ese

    public void createList(){
        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Palabra " + i);
        }
    }

    // limpia tot WordList.clear();
    ///luego el notifyItemchangedataSetchangexd tras el for que crea la lista

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
        if (id == R.id.action_reset) {
            mWordList.clear();
            createList();
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
