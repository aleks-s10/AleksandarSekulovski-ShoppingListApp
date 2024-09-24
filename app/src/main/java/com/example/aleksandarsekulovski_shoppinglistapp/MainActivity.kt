package com.example.aleksandarsekulovski_shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aleksandarsekulovski_shoppinglistapp.ui.theme.AleksandarSekulovskiShoppingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AleksandarSekulovskiShoppingListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingListApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: String,
    var isChecked: Boolean = false
)

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    var items by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Shopping List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            OutlinedTextField(
                value = newItemQuantity,
                onValueChange = { newItemQuantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Button(
                onClick = {
                    if (newItemName.isNotBlank() && newItemQuantity.isNotBlank()) {
                        val newItem = ShoppingItem(
                            id = items.size,
                            name = newItemName,
                            quantity = newItemQuantity
                        )
                        items = items + newItem
                        newItemName = ""
                        newItemQuantity = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items) { item ->
                ShoppingListItem(
                    item = item,
                    onCheckedChange = { id ->
                        items = items.map {
                            if (it.id == id) it.copy(isChecked = !it.isChecked) else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onCheckedChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { onCheckedChange(item.id) }
        )
        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    AleksandarSekulovskiShoppingListAppTheme {
        ShoppingListApp()
    }
}