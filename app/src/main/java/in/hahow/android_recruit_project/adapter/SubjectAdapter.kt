package `in`.hahow.android_recruit_project.adapter

import `in`.hahow.android_recruit_project.data.SubjectItem
import `in`.hahow.android_recruit_project.databinding.ItemSubjectBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SubjectAdapter : RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    var list: List<SubjectItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(binding: ItemSubjectBinding) : RecyclerView.ViewHolder
        (binding.root) {
        var binding: ItemSubjectBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSubjectBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subjectItem = list[position]
        holder.binding.apply {
            Glide.with(root)
                .load(subjectItem.coverImageUrl)
                .into(coverImageView)
            titleTextView.text = subjectItem.title

        }
    }

    override fun getItemCount(): Int = list.size


}