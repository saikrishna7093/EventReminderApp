package com.saikrishnapannela.eventreminder



data class AddEventData(
    var eventTitle: String = "",
    var description: String = "",
    var category: String = "",
    var eventName: String = "",
    var date: String = "",
    var time: String = "",
    var userMail: String = "dummy@gmail.com",
    var result : String = "Not Marked",
    var eventId: String = "",
)