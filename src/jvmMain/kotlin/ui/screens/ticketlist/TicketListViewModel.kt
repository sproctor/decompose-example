package ui.screens.ticketlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.customer.CustomerRepository
import data.ticket.TicketRepository
import data.ticket.TicketWithCustomer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ui.util.ViewModelInstance

class TicketListViewModel(
    private val ticketRepository: TicketRepository,
    private val customerRepository: CustomerRepository
) : ViewModelInstance() {

    var uiState by mutableStateOf(TicketListUiState())
        private set

    init {
        ticketRepository.observeTickets()
            .onEach { tickets ->
                uiState = uiState.copy(
                    tickets = tickets.map { ticket ->
                        TicketWithCustomer(
                            serialNumber = ticket.serialNumber,
                            customer = ticket.customerId?.let { customerRepository.getCustomer(it) }
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    fun createTicketClicked() {
        val ticket = ticketRepository.createTicket()
        uiState = uiState.copy(navigateToTicket = ticket.serialNumber)
    }

    fun didNavigation() {
        uiState = uiState.copy(navigateToTicket = null)
    }
}