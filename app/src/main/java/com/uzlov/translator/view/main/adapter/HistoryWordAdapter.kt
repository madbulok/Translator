package com.uzlov.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.translator.R
import com.uzlov.translator.model.data.WordModel
import kotlinx.android.synthetic.main.item_history_word.view.*

class HistoryWordAdapter(private var onListItemClickListener: HistoryWordAdapter.OnListItemClickListener) :
    RecyclerView.Adapter<HistoryWordAdapter.RecyclerItemViewHolder>() {

    private var words: MutableList<WordModel> = mutableListOf()

    fun setData(data: List<WordModel>) {
        words.clear()
        words.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_word, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: WordModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.tvHistoryWord.text = data.text
                itemView.setOnClickListener { openSearchResult(data) }
            }
        }
    }

    private fun openSearchResult(wordModel: WordModel) {
        onListItemClickListener.onItemClick(wordModel)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: WordModel)
    }
}