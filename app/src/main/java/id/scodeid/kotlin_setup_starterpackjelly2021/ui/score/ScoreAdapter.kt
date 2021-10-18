package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.scodeid.kotlin_setup_starterpackjelly2021.R
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_scores.*

class ScoreAdapter(
    private val items: MutableList<ScoresItem>,
    private val listener: (ScoresItem) -> Unit
) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemsData: MutableList<ScoresItem>) {
        items.clear()
        items.addAll(itemsData)
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: ScoresItem, listener: (ScoresItem) -> Unit) {

            //txt_name.text = item.name
            //item.image.let { Picasso.get().load(it).fit().into(img_main) }
            txt_score.text = item.score.toString()
            txt_user.text = item.user?.username.toString()
            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_scores, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        val TAG_LOG: String = ScoreAdapter::class.java.simpleName
    }

}