package com.example.guiaturistico.ui.sdui

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sdui.sdui.SduiNode

object SduiRenderer {

    fun render(context: Context, container: LinearLayout, nodes: List<SduiNode>) {
        container.removeAllViews()
        nodes.forEach { node ->
            when (node.type) {
                "header" -> {
                    val tv = TextView(context)
                    tv.textSize = 22f
                    tv.text = node.props?.get("title")?.toString().orEmpty()
                    container.addView(tv)
                }
                "hero" -> {
                    val iv = ImageView(context)
                    val url = node.props?.get("imageUrl")?.toString()
                    if (!url.isNullOrBlank()) {
                        Glide.with(context).load(url).into(iv)
                    }
                    container.addView(iv)
                }
                "text" -> {
                    val tv = TextView(context)
                    tv.text = node.props?.get("content")?.toString().orEmpty()
                    container.addView(tv)
                }
                "button" -> {
                    val btn = Button(context)
                    btn.text = node.props?.get("text")?.toString().orEmpty()
                    container.addView(btn)
                }
                "card_list" -> {
                    // Simplificação: só escreve um título; em produção, renderize grid/RecyclerView
                    val tv = TextView(context)
                    tv.text = "Lista de cards"
                    container.addView(tv)
                }
            }
            node.children?.let { render(context, container, it) }
        }
    }
}