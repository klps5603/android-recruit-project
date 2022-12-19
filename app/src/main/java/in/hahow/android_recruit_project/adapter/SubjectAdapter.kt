package `in`.hahow.android_recruit_project.adapter

import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.data.SubjectItem
import `in`.hahow.android_recruit_project.databinding.ItemSubjectBinding
import `in`.hahow.android_recruit_project.utils.DatePattern
import `in`.hahow.android_recruit_project.utils.convertStringToDate
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*
import java.util.concurrent.TimeUnit

class SubjectAdapter : RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    enum class Status(val text: String) {
        NONE(""),
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

        /**
         * status 為募資中、募資完成
         * 目前募資人數 大於（含）目標募資人數時，顯示達標 XX%
         * 目前募資人數 小於 目標募資人數時，顯示 XX / XX 人
         */
        fun setNumSoldTicketsTextView(numSoldTickets: Int, successNumSoldTickets: Int) {
            holder.binding.numSoldTicketsTextView.apply {
                text = if (numSoldTickets >= successNumSoldTickets) {
                    val overTarget =
                        ((numSoldTickets.toDouble() / successNumSoldTickets.toDouble()) * 100).toInt()
                    String.format(
                        context.getString(
                            R.string.over_target
                        ), "$overTarget%"
                    )
                } else {
                    String.format(
                        context.getString(
                            R.string.num_sold_tickets_proportion
                        ), numSoldTickets, successNumSoldTickets
                    )
                }
            }

        }

        /**
         * status 為募資中、募資完成
         * 以目前募資人數、目標募資人數，計算達標進度條
         */
        fun setNumSoldTicketsProgressBar(numSoldTickets: Int, successNumSoldTickets: Int) {
            val process =
                (numSoldTickets.toDouble() / successNumSoldTickets.toDouble()) * 100
            holder.binding.numSoldTicketsProgressBar.apply {
                progress = process.toInt()
                progressTintList = ContextCompat.getColorStateList(context, R.color.yellow)
                progressBackgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
            }
        }

        /**
         * status 為已開課，顯示影片長度
         * status 為募資中、募資完成，到期日比今天，大於（含）一天時，顯示倒數 XXX 天
         * 到期日比今天小於一天，且 status 為 募資中，顯示即將截止
         */
        fun setProposalDueTimeTextView(status: Status, subjectItem: SubjectItem) {
            holder.binding.proposalDueTimeTextView.apply {
                if (status == Status.PUBLISHED) {
                    val totalVideoLengthInSeconds = subjectItem.totalVideoLengthInSeconds
                    val totalVideoLengthInMin = totalVideoLengthInSeconds / 60
                    text =
                        String.format(context.getString(R.string.video_time), totalVideoLengthInMin)
                } else {
                    val proposalDueDate =
                        subjectItem.proposalDueTime.convertStringToDate(DatePattern.yyyyMMddTmmhhss)
                    if (proposalDueDate != null) {
                        val diff = proposalDueDate.time - Date().time
                        val diffDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                        text = if (diffDay > 0) {
                            String.format(context.getString(R.string.count_down_day), diffDay)
                        } else {
                            when (status) {
                                Status.INCUBATING -> {
                                    context.getText(R.string.due_soon)
                                }
                                else -> {
                                    ""
                                }
                            }
                        }
                    } else {
                        text = ""
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
                    CenterCrop(),
                    RoundedCorners(context.resources.getDimensionPixelOffset(R.dimen.db12))
                )
                .into(coverImageView)

            titleTextView.text = subjectItem.title

            statusTextView.text = status.text
            when (status) {
                Status.INCUBATING -> {
                    statusTextView.visibility = View.VISIBLE
                    statusTextView.setBackgroundResource(R.drawable.bg_yellow_12dp)
                    setNumSoldTicketsTextView(numSoldTickets, successNumSoldTickets)
                    setNumSoldTicketsProgressBar(numSoldTickets, successNumSoldTickets)
                }
                Status.PUBLISHED -> {
                    statusTextView.visibility = View.VISIBLE
                    statusTextView.setBackgroundResource(R.drawable.bg_blue_12dp)
                    numSoldTicketsTextView.text = "100%"
                    numSoldTicketsProgressBar.progress = 100
                    numSoldTicketsProgressBar.progressTintList =
                        ContextCompat.getColorStateList(context, R.color.blue)
                }
                Status.SUCCESS -> {
                    statusTextView.visibility = View.VISIBLE
                    statusTextView.setBackgroundResource(R.drawable.bg_yellow_12dp)
                    setNumSoldTicketsTextView(numSoldTickets, successNumSoldTickets)
                    setNumSoldTicketsProgressBar(numSoldTickets, successNumSoldTickets)
                }
                else -> {
                    statusTextView.visibility = View.GONE
                }

            }

            setProposalDueTimeTextView(status, subjectItem)

        }
    }

    override fun getItemCount(): Int = list.size

}