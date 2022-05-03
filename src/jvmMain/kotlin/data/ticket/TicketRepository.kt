package data.ticket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TicketRepository {

    private var nextSerialNumber = 1

    private val tickets = MutableStateFlow<List<Ticket>>(emptyList())

    @Synchronized
    fun createTicket(): Ticket {
        val ticket = Ticket(nextSerialNumber, null)
        nextSerialNumber += 1
        tickets.value += ticket
        return ticket
    }

    fun observeTickets(): Flow<List<Ticket>> {
        return tickets
    }

    fun observeTicket(serialNumber: Int): Flow<Ticket> {
        return tickets
            .map { list ->
                list.first { it.serialNumber == serialNumber }
            }
            .distinctUntilChanged()
    }

    fun setTicketCustomer(serialNumber: Int, customerId: Int) {
        tickets.value = tickets.value.map { ticket ->
            if (ticket.serialNumber == serialNumber) {
                Ticket(serialNumber, customerId)
            } else {
                ticket
            }
        }
    }
}