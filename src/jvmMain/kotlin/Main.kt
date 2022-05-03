// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import data.customer.CustomerRepository
import data.ticket.TicketRepository
import ui.App
import ui.navigation.RootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val root = RootComponent(
        componentContext = DefaultComponentContext(lifecycle),
        ticketRepository = TicketRepository(),
        customerRepository = CustomerRepository()
    )

    application {
        val windowState = rememberWindowState()

        // Bind the registry to the life cycle of the window
        LifecycleController(lifecycle, windowState)

        Window(state = windowState, onCloseRequest = ::exitApplication) {
            App(root)
        }
    }
}
