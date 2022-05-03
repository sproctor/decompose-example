package ui.screens.ticket

import com.arkivanov.decompose.ComponentContext

class TicketComponent(
    componentContext: ComponentContext,
    val viewModel: TicketViewModel,
    val navigateBack: () -> Unit,
    val selectCustomer: () -> Unit,
) : ComponentContext by componentContext