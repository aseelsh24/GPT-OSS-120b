package com.example.aichat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aichat.api.ApiRequest
import com.example.aichat.api.RetrofitClient
import com.example.aichat.databinding.ActivityMainBinding
import com.example.aichat.model.Message
import com.example.aichat.model.Sender
import com.example.aichat.ui.MessageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val messageList = mutableListOf<Message>()
    private lateinit var messageAdapter: MessageAdapter

    // IMPORTANT: Replace with your actual Hugging Face API Token.
    // The user will be instructed in the README on how to get this token
    // and provide it securely. For now, it's a placeholder.
    private val HUGGING_FACE_API_TOKEN = "Bearer YOUR_API_TOKEN_HERE"

    // The user can change the model by modifying this URL.
    // Using a well-known, powerful model as a default.
    private val API_URL = "https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageAdapter = MessageAdapter(messageList)
        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = messageAdapter
        }

        binding.buttonSend.setOnClickListener {
            handleSendButtonClick()
        }

        addMessage("Hello! I am an AI Chatbot. How can I assist you today?", Sender.BOT)
    }

    private fun handleSendButtonClick() {
        val userText = binding.editTextMessage.text.toString().trim()
        if (userText.isNotEmpty()) {
            addMessage(userText, Sender.USER)
            binding.editTextMessage.text.clear()
            addMessage("...", Sender.TYPING)

            // Launch a coroutine on the IO dispatcher for network operations
            CoroutineScope(Dispatchers.IO).launch {
                getApiResponse(userText)
            }
        }
    }

    private suspend fun getApiResponse(inputText: String) {
        try {
            val response = RetrofitClient.instance.getCompletion(
                url = API_URL,
                token = HUGGING_FACE_API_TOKEN,
                request = ApiRequest(inputs = inputText)
            )

            // Switch to the Main thread to update the UI
            withContext(Dispatchers.Main) {
                removeTypingIndicator()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    val botResponse = response.body()?.get(0)?.generated_text ?: "No response text found."
                    // Many models return the input prompt along with the generated text.
                    // This cleans up the response for a better user experience.
                    val cleanedResponse = botResponse.replace(inputText, "").trim()
                    addMessage(cleanedResponse, Sender.BOT)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    addMessage("API Error: ${response.code()} - $errorBody", Sender.BOT)
                }
            }
        } catch (e: Exception) {
            // Switch to the Main thread to update the UI
            withContext(Dispatchers.Main) {
                removeTypingIndicator()
                addMessage("Network Error: Check your connection or API token. Details: ${e.message}", Sender.BOT)
            }
        }
    }

    private fun addMessage(text: String, sender: Sender) {
        runOnUiThread {
            messageList.add(Message(text, sender))
            messageAdapter.notifyItemInserted(messageList.size - 1)
            binding.recyclerViewMessages.scrollToPosition(messageList.size - 1)
        }
    }

    private fun removeTypingIndicator() {
        // This must also run on the UI thread
        val typingMessageIndex = messageList.indexOfFirst { it.sender == Sender.TYPING }
        if (typingMessageIndex != -1) {
            messageList.removeAt(typingMessageIndex)
            messageAdapter.notifyItemRemoved(typingMessageIndex)
        }
    }
}
