package com.example.aichat.model

data class Message(
    val text: String,
    val sender: Sender
)

enum class Sender {
    USER, BOT, TYPING
}
