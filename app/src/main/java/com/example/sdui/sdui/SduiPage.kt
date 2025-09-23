package com.example.sdui.sdui

data class SduiPage(
    val version: String,
    val components: List<SduiNode>,
    val metadata: Map<String, Any?>? = null
)
