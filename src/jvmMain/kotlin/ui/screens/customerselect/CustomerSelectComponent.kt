package ui.screens.customerselect

import com.arkivanov.decompose.ComponentContext
import data.customer.Customer

class CustomerSelectComponent(
    componentContext: ComponentContext,
    val customers: List<Customer>,
    val navigateBack: () -> Unit,
    val onCustomerSelected: (Int) -> Unit
) : ComponentContext by componentContext