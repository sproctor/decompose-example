package ui.screens.ticketlist

import com.arkivanov.decompose.ComponentContext

class TicketListComponent(
    componentContext: ComponentContext,
    val viewModel: TicketListViewModel,
    val navigateToTicket: (Int) -> Unit,
) : ComponentContext by componentContext