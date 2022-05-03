package ui.screens.ticketlist

import data.ticket.TicketWithCustomer

data class TicketListUiState(
    val tickets: List<TicketWithCustomer> = emptyList(),
    val navigateToTicket: Int? = null,
)