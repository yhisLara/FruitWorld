package com.example.yhisl.fruitworld.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yhisl.fruitworld.R;
import com.example.yhisl.fruitworld.adapters.FruitAdapter;
import com.example.yhisl.fruitworld.models.Fruit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //listView, gridView y adapter
    private ListView listView;
    private GridView gridView;
    private FruitAdapter adapterListView;
    private FruitAdapter adapterGridView;

    //lista del modelo fruta
    private List<Fruit> fruits;

    //item en el option menu
    private MenuItem itemListView;
    private MenuItem itemGridView;

    //variables
    private int counter = 0;
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.enforceIconBar();

        this.fruits = getAllFruits();

        this.listView = (ListView) findViewById(R.id.listView);
        this.gridView = (GridView) findViewById(R.id.gridView);

        //adjuntar mismo metodo click para ambos
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        this.adapterListView = new FruitAdapter(this, R.layout.list_fruit, fruits);
        this.adapterGridView = new FruitAdapter(this, R.layout.grid_fruit, fruits);

        this.listView.setAdapter(adapterListView);
        this.gridView.setAdapter(adapterGridView);

        //registrar el context menu para ambos
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);



    }

    private void enforceIconBar(){
        getSupportActionBar().setIcon(R.mipmap.fruitworld);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private List<Fruit> getAllFruits(){
        List<Fruit> list = new ArrayList<Fruit>(){{
            add(new Fruit("Banana", "Concepción", R.mipmap.ic_banana));
            add(new Fruit("Strawberry", "Talcahuano",R.mipmap.ic_strawberry));
            add(new Fruit("Orange", "Santiago",R.mipmap.ic_citrus));
            add(new Fruit("Apple", "Puerto Montt" ,R.mipmap.ic_apple));
            add(new Fruit("Cherry", "Temuco",R.mipmap.ic_cherry));
            add(new Fruit("Pear", "Iquique",R.mipmap.ic_pear));
            add(new Fruit("Grapes", "Valdivia",R.mipmap.ic_grapes));
        }};
        return list;
    }

    private void addFruit(Fruit fruit){
        this.fruits.add(fruit);
        //aviso el cambio en ambos adaptadores
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }

    private void deleteFruit(int position){
        this.fruits.remove(position);
        //aviso el cambio en ambos adaptadores
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clickFruit(fruits.get(position));
    }

    private void clickFruit(Fruit fruit) {
        //diferenciar entre frutas conocidas y desconocidas
        if(fruit.getOrigen().equals("Unknown"))
            Toast.makeText(this, "Sorry, we don't have many info about " + fruit.getName(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The best Fruit from " + fruit.getOrigen()+ " is " + fruit.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        //dps de inflar, recoger las referencia a los botones que interesan
        this.itemListView = menu.findItem(R.id.list_view);
        this.itemGridView = menu.findItem(R.id.grid_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //eventos para los clicks en los botones del option menu
        switch (item.getItemId()){
            case R.id.add_fruit:
                this.addFruit(new Fruit("Added nº "+(++counter),"Unknown",R.mipmap.ic_tomate));
                return true;
            case R.id.list_view:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.grid_view:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //inflar el contex menu con el layout
        MenuInflater inflater = getMenuInflater();
        //antes de inflar, añadir header dependiendo del objeto
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)  menuInfo;
        menu.setHeaderTitle(this.fruits.get(info.position).getName());
        //inflar
        inflater.inflate(R.menu.context_menu_fruits, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //obtener info en el contexzto menu del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_fruit:
                this.deleteFruit(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void switchListGridView(int option){
        //metodo para cambiar entre grid y list
        if(option == SWITCH_TO_LIST_VIEW){
            //para cambiar a list view, y el list view esta en modo invisible
            if(this.listView.getVisibility() == View.INVISIBLE){
                //se esconde el grid view y se enseña su botón
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                //no  olvidar enseñar el lost y esconder su boton en el menu
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        }else if(option == SWITCH_TO_GRID_VIEW){
            //para cambiar el grid view , y el grid esta invisible
            if(this.gridView.getVisibility() == View.INVISIBLE){
                //esconder list view y enseñar boton
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
                //no olvidar enseñar el grid y esconder el boton en el menu
                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }
}
