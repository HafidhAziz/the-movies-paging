package com.example.sharingsessionpaging.data.network.exceptions

import java.io.IOException

class NotFoundException : IOException() {

    override val message: String
        get() = "Not Found"
}
