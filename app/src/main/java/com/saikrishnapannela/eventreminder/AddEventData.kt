package com.saikrishnapannela.eventreminder



data class AddEventData(
    var eventName: String = "",
    var description: String = "",
    var category: String = "",
    var date: String = "",
    var time: String = "",
    var userMail: String = "dummy@gmail.com"
)