package com.example.aichat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aichat.R
import com.example.aichat.model.Message
import com.example.aichat.model.Sender

class MessageAdapter(private val messages: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private const val VIEW_TYPE_USER = 1
    private const val VIEW_TYPE_BOT = 2

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].sender) {
            Sender.USER -> VIEW_TYPE_USER
            else -> VIEW_TYPE_BOT // BOT and TYPING will use the same layout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_USER) R.layout.item_message_user else R.layout.item_message_bot
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)

        fun bind(message: Message) {
            textViewMessage.text = message.text
        }
    }
}
