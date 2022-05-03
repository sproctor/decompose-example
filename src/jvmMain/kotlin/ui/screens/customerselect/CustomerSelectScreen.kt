package ui.screens.customerselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.customer.Customer

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomerSelectScreen(
    customers: List<Customer>,
    navigateBack: () -> Unit,
    onCustomerSelected: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select a customer") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column {
            customers.forEachIndexed { index, customer ->
                ListItem(
                    modifier = Modifier.clickable { onCustomerSelected(customer.id) }
                ) {
                    Text(customer.name)
                }
                if (index != customers.size - 1) {
                    Divider()
                }
            }
        }
    }
}