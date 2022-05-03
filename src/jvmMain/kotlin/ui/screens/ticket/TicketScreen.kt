package ui.screens.ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import data.ticket.TicketWithCustomer

@Composable
fun TicketScreen(
    viewModel: TicketViewModel,
    navigateBack: () -> Unit,
    selectCustomer: () -> Unit,
) {
    val ticket = viewModel.uiState.ticket
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ticket: ${ticket?.serialNumber ?: "-"}") },
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
        if (ticket == null) {
            Text("Loading...")
        } else {
            TicketUi(ticket, selectCustomer)
        }
    }
}

@Composable
fun TicketUi(
    ticket: TicketWithCustomer,
    selectCustomer: () -> Unit
) {
    Column {
        Text("Serial number: ${ticket.serialNumber}")
        Row {
            Button(
                onClick = selectCustomer
            ) {
                Text("Change customer")
            }
            val customerName = ticket.customer?.name ?: "(No customer selected"
            Text(customerName)
        }
    }
}