package ui.screens.ticketlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TicketListScreen(
    viewModel: TicketListViewModel,
    navigateToTicket: (Int) -> Unit
) {
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            TopAppBar { Text("Ticket List") }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Create ticket") },
                onClick = { viewModel.createTicketClicked() }
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            uiState.tickets.forEach { ticket ->
                ListItem(
                    modifier = Modifier.clickable { navigateToTicket(ticket.serialNumber) },
                    text = { Text("${ticket.serialNumber} ${ticket.customer?.name ?: "(no customer)"}") },
                )
            }
        }
    }
    uiState.navigateToTicket?.let {
        navigateToTicket(it)
        viewModel.didNavigation()
    }
}