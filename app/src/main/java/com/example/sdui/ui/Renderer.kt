package com.example.sdui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sdui.sdui.SduiAction
import com.example.sdui.sdui.SduiNode

@Composable
fun Renderer(
    nodes: List<SduiNode>,
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        nodes.forEach { node ->
            NodeView(node = node, onNavigate = onNavigate)
        }
    }
}

@Composable
private fun NodeView(
    node: SduiNode,
    onNavigate: (route: String, params: Map<String, Any?>?) -> Unit
) {
    val props = node.props ?: emptyMap()
    val children = node.children ?: emptyList()
    val actions = node.actions ?: emptyList()

    fun handleAction(kind: String = "navigate") {
        val act: SduiAction? = actions.firstOrNull { it.type == kind }
        val route = (act?.params?.get("route") as? String) ?: return
        val params = act.params?.get("params") as? Map<String, Any?>
        onNavigate(route, params)
    }

    when (node.type) {
        "banner" -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val imageUrl = props["imageUrl"] as? String
                if (imageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "banner",
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = (props["title"] as? String) ?: "Banner",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                val subtitle = props["subtitle"] as? String
                if (subtitle != null) {
                    Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        "card_list" -> {
            val columns = (props["columns"] as? Number)?.toInt() ?: 2
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(min = 100.dp)
            ) {
                items(children) { child ->
                    NodeView(node = child, onNavigate = onNavigate)
                }
            }
        }

        "card" -> {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { handleAction("navigate") }
            ) {
                Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    val imageUrl = props["imageUrl"] as? String
                    if (imageUrl != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "card",
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(text = (props["title"] as? String) ?: "Card", fontWeight = FontWeight.SemiBold)
                    val desc = props["description"] as? String
                    if (desc != null) Text(text = desc, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        "button" -> {
            Button(onClick = { handleAction("navigate") }) {
                Text(text = (props["text"] as? String) ?: "Ação")
            }
        }

        "header" -> {
            Text(
                text = (props["title"] as? String) ?: "Título",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        "hero" -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val imageUrl = props["imageUrl"] as? String
                if (imageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "hero",
                        modifier = Modifier.fillMaxWidth().height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                val title = props["title"] as? String
                if (title != null) Text(text = title, style = MaterialTheme.typography.titleMedium)
            }
        }

        "text" -> {
            Text(text = (props["content"] as? String) ?: "", style = MaterialTheme.typography.bodyMedium)
        }

        else -> {
            Text(text = "Componente desconhecido: ${node.type}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
