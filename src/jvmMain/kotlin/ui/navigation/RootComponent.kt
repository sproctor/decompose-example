package ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import data.customer.CustomerRepository
import data.ticket.TicketRepository
import ui.screens.customerselect.CustomerSelectComponent
import ui.screens.ticket.TicketComponent
import ui.screens.ticket.TicketViewModel
import ui.screens.ticketlist.TicketListComponent
import ui.screens.ticketlist.TicketListViewModel

class RootComponent(
    componentContext: ComponentContext,
    private val ticketRepository: TicketRepository,
    private val customerRepository: CustomerRepository,
) : ComponentContext by componentContext {

    private val router = router<Config, Child>(
        initialConfiguration = Config.TicketList,
        handleBackButton = true,
        childFactory = ::child,
    )

    val routerState: Value<RouterState<*, Child>> = router.state

    private fun child(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.TicketList -> Child.TicketList(ticketList(componentContext))
            is Config.Ticket -> Child.Ticket(ticket(componentContext, config.serialNumber))
            Config.CustomerSelect -> Child.CustomerSelect(customerSelect(componentContext))
        }
    }

    private fun ticketList(componentContext: ComponentContext): TicketListComponent {
        return TicketListComponent(
            componentContext = componentContext,
            viewModel = componentContext.instanceKeeper.getOrCreate { TicketListViewModel(ticketRepository, customerRepository) },
            navigateToTicket = { router.push(Config.Ticket(it)) }
        )
    }

    private fun ticket(componentContext: ComponentContext, serialNumber: Int): TicketComponent {
        return TicketComponent(
            componentContext = componentContext,
            viewModel = componentContext.instanceKeeper.getOrCreate {
                TicketViewModel(serialNumber, ticketRepository, customerRepository)
            },
            navigateBack = { router.pop() },
            selectCustomer = { router.push(Config.CustomerSelect) }
        )
    }

    private fun customerSelect(componentContext: ComponentContext): CustomerSelectComponent {
        return CustomerSelectComponent(
            componentContext = componentContext,
            customers = customerRepository.getCustomers(),
            navigateBack = { router.pop() },
            onCustomerSelected = { customerId ->
                router.pop {
                    val activeChild = router.activeChild.instance
                    if (activeChild is CustomerSelectListener) {
                        activeChild.customerSelected(customerId)
                    }
                }
            }
        )
    }

    sealed interface Child {
        data class TicketList(val ticketListComponent: TicketListComponent) : Child
        data class Ticket(val ticketComponent: TicketComponent) : Child, CustomerSelectListener {
            override fun customerSelected(id: Int) {
                ticketComponent.viewModel.setCustomer(id)
            }
        }
        data class CustomerSelect(val customerSelectComponent: CustomerSelectComponent) : Child
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object TicketList : Config

        @Parcelize
        data class Ticket(val serialNumber: Int) : Config

        @Parcelize
        object CustomerSelect : Config
    }
}