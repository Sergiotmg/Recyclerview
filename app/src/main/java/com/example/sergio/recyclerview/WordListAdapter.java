package com.example.sergio.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {
    //To hold your data in the adapter:
    private final LinkedList<String> mWordList;//los items que iremos mostrando
    private LayoutInflater mInflater;//para crear distintos layout
    private Context context; // nos hara context.getresources para coger los colores de fondo
    //constructor
    public WordListAdapter(Context context,LinkedList<String> wordList) {//context no se deberia
        // estar pasando de una clase a otra. mejor no ir pasando las variable context por ahi por
        //problemas de memoria
        mInflater = LayoutInflater.from(context);// si hay 20 creara 20
        this.mWordList = wordList;
        this.context=context;
    }

    //una clase dentro de otra clase+

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView icon;
        public final TextView mTxtWord;
        public final LinearLayout linearLayout;
        final WordListAdapter mAdapter;
        // constructor para el wordViewHolder
        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            //buscar !
            linearLayout =itemView.findViewById(R.id.LinearLayout);
            mTxtWord = itemView.findViewById(R.id.txtWord);
            icon = itemView.findViewById(R.id.icon);
            this.mAdapter = adapter;//no usamos madapter por ahora pero se usara par ale onClikc
            //qe click en tot el cuadrado se pone this porque dicha clase implementa onclickListener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();//E en todos los view HOlders
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.
            mWordList.set(mPosition, "Clickado! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
            //notifyItemChanged, que uno ha cambiado

        }
    }

    //es obligatorio hacerle overrides en dicha clase
    @NonNull
    @Override
    // vamos a crear un view holer para cada uno de los item ( sostenedor de vista)
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {//int la posicion item
        View mItemView = mInflater.
                //generamos vista para inflar el layout, el padre y attach: se
                //ate a padre=false
                inflate(R.layout.wordlist_item,parent, false);// como false siempre
        return new WordViewHolder(mItemView, this);// generamos un nuevo objeto con
        //siempre sera = esta ultima linea , siempre devovleremos ese WordViewHolder
    }

    @Override
    //bind= enlazar unir view holder con la vista y que muestre lo q toca
    // se puede poner el onclick dentro del onBind pero no es recomendable porque cada vez que
    //deslizamos se vuelven a generar todos los onclick
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.mTxtWord.setText(mCurrent);
        //holder.mTxtWord.setText(mCurrent.getName()); si fuera un cliente mCurrent
        int drawable;//R.algo devuelve un entero
        int color;
        // NO PONER MUCHA LOGICA EN ESTE METODO
        if (position%2==0){
            drawable=R.drawable.i2;
            color=R.color.blue;
        }else{
            drawable=R.drawable.i1;
            color=R.color.red;
        }
        holder.linearLayout.setBackgroundColor(context.getResources().getColor(color));
        holder.icon.setImageResource(drawable);
        // para solo para ese text view  PARA QUE SE EXPANDA SOLO PARA ESE mTxtWord
        holder.mTxtWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    //el tamaño de la vista del redycler
    public int getItemCount() {
        return mWordList.size();
    }


}
