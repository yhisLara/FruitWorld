package com.example.yhisl.fruitworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yhisl.fruitworld.R;
import com.example.yhisl.fruitworld.models.Fruit;

import java.util.List;

/**
 * Created by yhisl on 08/03/2017.
 */

public class FruitAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Fruit> list;

    public FruitAdapter(Context context, int layout, List<Fruit> list){

        this.context = context;
        this.layout = layout;
        this.list = list;

    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        //View Holder Pattern
        ViewHolder holder;
        //copiar la vista
        if(convertView == null){
            //solo si esta nulo, es decir, primera vez en ser renderizado, inflamos
            //y adjuntamos las referencias del layout en ua nueva isntancia de nuestro
            //ViewHolder, y lo insertamos dentro del convertView, para reciclar su uso
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.nombre =(TextView) convertView.findViewById(R.id.textViewN);
            holder.origen = (TextView) convertView.findViewById(R.id.textViewO);
            holder.icono = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else{
            //obtenemos la refencia que posteriomente pusimos dentro del converView
            //y asi, reciclamos su uso sin necesidad de buscar de nuevom, referencias con findViewById
            holder = (ViewHolder) convertView.getTag();
        }

        final Fruit currentFruit = (Fruit) getItem(position);
        holder.nombre.setText(currentFruit.getName());
        holder.origen.setText(currentFruit.getOrigen());
        holder.icono.setImageResource(currentFruit.getIcon());
        return convertView;
    }

    static class ViewHolder {
        private TextView nombre;
        private TextView origen;
        private ImageView icono;
    }

}
