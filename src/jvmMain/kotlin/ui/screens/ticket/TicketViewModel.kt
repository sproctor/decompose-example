package ui.screens.ticket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.customer.CustomerRepository
import data.ticket.TicketRepository
import data.ticket.TicketWithCustomer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ui.util.ViewModelInstance

class TicketViewModel(
    private val serialNumber: Int,
    private val ticketRepository: TicketRepository,
    private val customerRepository: CustomerRepository,
) : ViewModelInstance() {

    var uiState by mutableStateOf(TicketUiState())
        private set

    init {
        ticketRepository.observeTicket(serialNumber)
            .onEach { ticket ->
                uiState = uiState.copy(
                    ticket = TicketWithCustomer(
                        serialNumber = ticket.serialNumber,
                        customer = ticket.customerId?.let { customerRepository.getCustomer(it) }
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun setCustomer(customerId: Int) {
        ticketRepository.setTicketCustomer(serialNumber = serialNumber, customerId = customerId)
    }
}