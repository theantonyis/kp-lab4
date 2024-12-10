import java.time.LocalDate
import java.time.Period

data class Patient(
    val id: Int,
    val name: String,
    val dateOfBirth: LocalDate,
    val phoneNumber: String?, // Nullable
    val medicalHistory: String?
) {
    fun calculateAge(): Int {
        return Period.between(dateOfBirth, LocalDate.now()).years
    }
}