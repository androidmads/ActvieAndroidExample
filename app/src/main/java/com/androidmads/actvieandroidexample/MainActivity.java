package com.androidmads.actvieandroidexample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Holder holder = new Holder();
    Details details = new Details();
    Long id;
    List<Details> detailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();

        holder.btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotEmpty(holder.edt_name) && isNotEmpty(holder.edt_age)) {
                    details.name = holder.edt_name.getText().toString().trim();
                    details.age = holder.edt_age.getText().toString().trim();
                    insertData(details);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter in All Fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.btn_read_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsList = getAll();
                populate(detailsList);
            }
        });

        holder.btn_read_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsList.clear();
                if (isNotEmpty(holder.edt_id)) {
                    detailsList.add(getOne(holder.edt_id.getText().toString().trim()));
                    populate(detailsList);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Id to read", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotEmpty(holder.edt_id)) {
                    deleteOne(Long.parseLong(holder.edt_id.getText().toString().trim()));
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Id to Delete", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsList.clear();
                if (isNotEmpty(holder.edt_id) && isNotEmpty(holder.edt_name) && isNotEmpty(holder.edt_age)) {
                    Details details = getOne(holder.edt_id.getText().toString().trim());
                    details.name = holder.edt_name.getText().toString().trim();
                    details.age = holder.edt_age.getText().toString().trim();
                    updateData(details);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter in All Fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void initialization() {
        holder.edt_name = (EditText) findViewById(R.id.name);
        holder.edt_age = (EditText) findViewById(R.id.age);
        holder.edt_id = (EditText) findViewById(R.id.id);
        holder.btn_insert = (Button) findViewById(R.id.insert);
        holder.btn_read_all = (Button) findViewById(R.id.readAll);
        holder.btn_read_one = (Button) findViewById(R.id.read);
        holder.btn_delete_all = (Button) findViewById(R.id.deleteAll);
        holder.btn_delete = (Button) findViewById(R.id.delete);
        holder.btn_update = (Button) findViewById(R.id.update);
    }

    // validating each EditText
    public boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().trim().length() > 0;
    }

    // Save Data
    public void insertData(Details details){
        id = details.save();
        Log.v("id_value", String.valueOf(id));
        recreate();
    }

    // Read all Data
    private List<Details> getAll() {
        return new Select()
                .from(Details.class)
                .orderBy("id ASC")
                .execute();
    }

    // Read Particular Data
    private Details getOne(String id) {
        return new Select()
                .from(Details.class)
                .where("id = ?", id)
                .executeSingle();
    }

    // Delete All
    private void deleteAll() {
        new Delete().from(Details.class).execute();
    }

    // Delete Particular Data
    private void deleteOne(long id) {
        Details.delete(Details.class , id);
    }

    // Update Data
    public void updateData(Details details){
        id = details.save();
        Log.v("updated_id_value", String.valueOf(id));
        recreate();
    }

    public void populate(List<Details> list) {

        ArrayList<String> al = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            al.add(list.get(i).name + "---" + list.get(i).age);
        }
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        ListView lv = (ListView) dialog.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, al);
        lv.setAdapter(adapter);
        dialog.show();
    }

    static class Holder {
        EditText edt_name, edt_age, edt_id;
        Button btn_insert, btn_read_all, btn_read_one, btn_delete_all, btn_delete, btn_update;
    }
}
