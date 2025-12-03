package com.example.sdui.sdui

data class SduiNode(
    val type: String,
    val props: Map<String, Any>?,
    val children: List<SduiNode>?,
    val actions: List<SduiAction>?
)
