package data.customer

class CustomerRepository {

    private val customers = listOf(
        Customer(1, "Sean Proctor"),
        Customer(2, "Jane Doe"),
        Customer(3, "John Smith")
    )

    fun getCustomers(): List<Customer> = customers

    fun getCustomer(id: Int): Customer {
        return customers.first { it.id == id }
    }
}