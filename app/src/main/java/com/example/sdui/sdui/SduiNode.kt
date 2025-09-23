package com.example.sdui.sdui

data class SduiNode(
    val type: String,
    val props: Map<String, Any?>? = null,
    val children: List<SduiNode>? = null,
    val actions: List<SduiAction>? = null
)
