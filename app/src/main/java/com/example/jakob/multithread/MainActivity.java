package com.example.jakob.multithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filename = "numbers.txt";
        file   = new File(this.getFilesDir(), filename);
        create = new Creatable(file);

        ListView myListView = (ListView)(findViewById(R.id.listView));
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(myAdapter);
        load = new Loadable(myAdapter, file);
    }

    public void create(View v) {
        create.run();
    }

    public void load(View v) {
        load.run();
    }

    public void clear(View v) {
        myAdapter.clear();
    }

    private File file;
    private String filename;
    private ArrayAdapter<String> myAdapter;
    private Creatable create;
    private Loadable load;

}
class Creatable implements Runnable {

    public void run() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            for (int i = 1; i < 11; ++i) {
                String output = (String.valueOf(i) + "\n");
                out.write(output);
                Thread.sleep(250);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Threw: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    Creatable(File myFile) {
        file = myFile;
    }
    private File file;
}

class Loadable implements Runnable {

    public void run() {
        String line;

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            while ((line = in.readLine()) != null) {
                myAdapter.add(line);
                Thread.sleep(250);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Threw: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    Loadable(ArrayAdapter<String> adapter, File myFile) {
        myAdapter = adapter;
        file = myFile;
    }

    private File file;
    private ArrayAdapter<String> myAdapter;
}
