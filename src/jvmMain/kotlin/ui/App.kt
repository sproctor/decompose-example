package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.fade
import ui.navigation.RootComponent
import ui.screens.customerselect.CustomerSelectScreen
import ui.screens.ticket.TicketScreen
import ui.screens.ticketlist.TicketListScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(root: RootComponent) {

    MaterialTheme {
        Children(
            routerState = root.routerState,
            animation = childAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.TicketList ->
                    TicketListScreen(
                        viewModel = child.ticketListComponent.viewModel,
                        navigateToTicket = child.ticketListComponent.navigateToTicket
                    )
                is RootComponent.Child.Ticket ->
                    TicketScreen(
                        viewModel = child.ticketComponent.viewModel,
                        navigateBack = child.ticketComponent.navigateBack,
                        selectCustomer = child.ticketComponent.selectCustomer
                    )
                is RootComponent.Child.CustomerSelect ->
                    CustomerSelectScreen(
                        customers = child.customerSelectComponent.customers,
                        navigateBack = child.customerSelectComponent.navigateBack,
                        onCustomerSelected = child.customerSelectComponent.onCustomerSelected
                    )
            }
        }
    }
}