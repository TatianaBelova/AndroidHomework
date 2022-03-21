package course.androidhomework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


internal class ContactsRecyclerAdapter(private var itemsList: List<ContactModel>) :
    RecyclerView.Adapter<ContactsRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemNameTextView.text = item.name
        holder.itemPhoneTextView.text = item.phone
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    internal inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemNameTextView: TextView = view.findViewById(R.id.name)
        var itemPhoneTextView: TextView = view.findViewById(R.id.pnone)
    }
}