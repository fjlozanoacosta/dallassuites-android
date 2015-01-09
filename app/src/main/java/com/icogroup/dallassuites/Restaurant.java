package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/29/14.
 */
public class Restaurant extends Activity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableAdapter;
    String[] categorias = {"Desayuno", "Ensaladas", "De picar", "Sandwiches", "Pizzas", "Parrillas", "Resto del día", "A la plancha", "Snacks 24 horas", "Bebidas", "Postres" };
    String[] bebidas = {"Champagne y espumantes", "Vinos", "Whiskies", "Rones", "Vodka", "Gyn", "Aperitivos y tragos preparados", "Cocktails"};
    String[] keywordsCategorias = {"desayuno", "plancha", "resto", "parrilla", "ensalada", "picar", "sandwich", "pizza", "postre", "snack"};
    String[] keywordsBebidas = {"champagne", "espumante", "vino", "whisky", "ron", "vodka", "gyn", "aperitivo", "coctel", "otras", "batido", "café"};
    int[] imagesCategorias = {R.drawable.iconos_desayuno, R.drawable.iconos_ensalada, R.drawable.iconos_depicar, R.drawable.iconos_sandwiches, R.drawable.iconos_pizza, R.drawable.iconos_alaplancha,R.drawable.iconos_restodeldia, R.drawable.iconos_alaplancha, R.drawable.iconos_snaks, R.drawable.iconos_bebidas, R.drawable.iconos_postres};

    Typeface brandonreg;
    TextView tvCategoria, title, tvBebidas;
    ImageView ivCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        init();

        expandableListView.setAdapter(expandableAdapter);

        expandableListView.setGroupIndicator(null);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

           

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {

                return false;
            }
        });

    }

    private void init() {

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expandableAdapter = new ExpandableListAdapter(getApplicationContext());

        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        title = (TextView)findViewById(R.id.restaurant_title);
        title.setTypeface(brandonreg);



    }


    class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;

        public ExpandableListAdapter(Context context) {
            this._context = context;
        }

        @Override
        public int getGroupCount() {
            return categorias.length;
        }

        @Override
        public int getChildrenCount(int i) {

            if(categorias[i].contains("Bebidas"))
                return bebidas.length;
            else
                return 0;
        }

        @Override
        public Object getGroup(int i) {
            return categorias[i];
        }

        @Override
        public Object getChild(int i, int i2) {
            return bebidas[i];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i2) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.categorias, null);
            }

            tvCategoria = (TextView)convertView.findViewById(R.id.text_categoria);
            tvCategoria.setText(categorias[groupPosition]);
            tvCategoria.setTypeface(brandonreg);

            ivCategoria = (ImageView)convertView.findViewById(R.id.image_categoria);
            ivCategoria.setImageResource(imagesCategorias[groupPosition]);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.bebidas, null);

                tvBebidas = (TextView)convertView.findViewById(R.id.text_bebida);
                tvBebidas.setText(bebidas[childPosition]);
                tvBebidas.setTypeface(brandonreg);

            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i2) {
            return false;
        }
    }





}
