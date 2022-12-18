package `in`.hahow.android_recruit_project.adapter

import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.data.SubjectItem
import `in`.hahow.android_recruit_project.databinding.ItemSubjectBinding
import `in`.hahow.android_recruit_project.utils.DatePattern
import `in`.hahow.android_recruit_project.utils.convertStringToDate
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*
import java.util.concurrent.TimeUnit

class SubjectAdapter : RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    enum class Status(val text: String) {
        INCUBATING("募資中"),
        PUBLISHED("已開課"),
        SUCCESS("募資成功")
    }

    private lateinit var context: Context

    var list: List<SubjectItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun setNumSoldTicketsTextView(numSoldTickets: Int, successNumSoldTickets: Int) {
            holder.binding.numSoldTicketsTextView.text = String.format(
                context.getString(
                    R.string.num_sold_tickets_proportion
                ), numSoldTickets, successNumSoldTickets
            )
        }

        fun setNumSoldTicketsProgressBar(numSoldTickets: Int, successNumSoldTickets: Int) {
            val process =
                (numSoldTickets.toDouble() / successNumSoldTickets.toDouble()) * 100
            holder.binding.numSoldTicketsProgressBar.apply {
                progress = process.toInt()
                progressTintList = ContextCompat.getColorStateList(context, R.color.yellow)
                progressBackgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
            }
        }

        fun setProposalDueTimeTextView(proposalDueTime: String) {
            val proposalDueDate = proposalDueTime.convertStringToDate(DatePattern.yyyyMMddTmmhhss)
            proposalDueDate?.let {
                val diff = it.time - Date().time
                val diffDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                if (diffDay > 0) {
                    holder.binding.proposalDueTimeTextView.text =
                        String.format(context.getString(R.string.count_down_day), diffDay)
                } else {
                    val diffHour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
                    if (diffHour > 0) {
                        holder.binding.proposalDueTimeTextView.text = String.format(
                            context
                                .getString(R.string.count_down_hour), diffHour
                        )
                    } else {
                        val diffMin = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
                        if (diffMin > 0) {
                            holder.binding.proposalDueTimeTextView.text = String.format(
                                context
                                    .getString(R.string.count_down_min), diffMin
                            )
                        }
                    }
                }
            }
        }

        holder.binding.apply {
            val subjectItem = list[position]
            val numSoldTickets = subjectItem.numSoldTickets
            val successNumSoldTickets = subjectItem.successCriteria.numSoldTickets
            val status = Status.valueOf(subjectItem.status)

            Glide.with(root)
                .load(subjectItem.coverImageUrl)
                .transform(
                    CenterInside(),
                    RoundedCorners(context.resources.getDimensionPixelOffset(R.dimen.db12))
                )
                .into(coverImageView)

            titleTextView.text = subjectItem.title

            statusTextView.text = status.text
            when (status) {
                Status.INCUBATING -> {
                    statusTextView.setBackgroundResource(R.drawable.bg_yellow_12dp)
                    setNumSoldTicketsTextView(numSoldTickets, successNumSoldTickets)
                    setNumSoldTicketsProgressBar(numSoldTickets, successNumSoldTickets)
                }
                Status.PUBLISHED -> {
                    statusTextView.setBackgroundResource(R.drawable.bg_blue_12dp)
                    numSoldTicketsTextView.text = "100%"
                    numSoldTicketsProgressBar.progress = 100
                    numSoldTicketsProgressBar.progressTintList =
                        ContextCompat.getColorStateList(context, R.color.blue)
                }
                Status.SUCCESS -> {
                    statusTextView.setBackgroundResource(R.drawable.bg_yellow_12dp)
                    setNumSoldTicketsTextView(numSoldTickets, successNumSoldTickets)
                    setNumSoldTicketsProgressBar(numSoldTickets, successNumSoldTickets)
                }

            }

            setProposalDueTimeTextView(subjectItem.proposalDueTime)

        }
    }

    override fun getItemCount(): Int = list.size

}