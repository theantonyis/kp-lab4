import java.time.LocalDate

data class Appointment(
    val id: Int,
    val patient: Patient,
    val dentist: Dentist,
    val date: LocalDate,
    val notes: String?,
    val services: List<String>
)