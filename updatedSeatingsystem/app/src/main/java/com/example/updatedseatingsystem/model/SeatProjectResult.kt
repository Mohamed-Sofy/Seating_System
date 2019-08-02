package com.example.updatedseatingsystem.model


data class SeatProjectResult(
    var job_id: Job,
    var project_id: Project,
    var seat_id: Seat,
    var data_id: Data
)

data class SeatManagerResult(
    var job_id: Job,
    var project_id: Project,
    var seat_id: Seat,
    var data_id: Data
)

data class SeatProjectResponse(var result: Array<SeatProjectResult>, var manager : String)


data class SeatManagerResponse(var result: Array<SeatManagerResult>)

