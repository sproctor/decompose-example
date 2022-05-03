package data.ticket

import data.customer.Customer

data class TicketWithCustomer(
    val serialNumber: Int,
    val customer: Customer?,
)