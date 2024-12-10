import java.time.LocalDate

class AppointmentBuilder {
    private var id: Int = 0
    private lateinit var patient: Patient
    private lateinit var dentist: Dentist
    private lateinit var date: LocalDate
    private var notes: String? = null
    private val services = mutableListOf<String>()

    fun setId(id: Int) = apply { this.id = id }
    fun setPatient(patient: Patient) = apply { this.patient = patient }
    fun setDentist(dentist: Dentist) = apply { this.dentist = dentist }
    fun setDate(date: LocalDate) = apply { this.date = date }
    fun setNotes(notes: String?) = apply { this.notes = notes }
    fun addService(service: String) = apply { this.services.add(service) }

    fun build(): Appointment {
        return Appointment(id, patient, dentist, date, notes, services)
    }
}

// Декоратор для додаткових послуг
interface AppointmentService {
    fun getDescription(): String
}

class BasicAppointment(private val appointment: Appointment) : AppointmentService {
    override fun getDescription(): String {
        return "Basic appointment with ${appointment.dentist.name}"
    }
}

class WhiteningDecorator(private val appointmentService: AppointmentService) : AppointmentService {
    override fun getDescription(): String {
        return appointmentService.getDescription() + ", including teeth whitening"
    }
}

class XRayDecorator(private val appointmentService: AppointmentService) : AppointmentService {
    override fun getDescription(): String {
        return appointmentService.getDescription() + ", including X-ray"
    }
}

// Observer для сповіщення про нові прийоми
interface AppointmentObserver {
    fun onNewAppointment(appointment: Appointment)
}

class EmailNotifier : AppointmentObserver {
    override fun onNewAppointment(appointment: Appointment) {
        println("Email sent for new appointment with ID: ${appointment.id}")
    }
}

class SmsNotifier : AppointmentObserver {
    override fun onNewAppointment(appointment: Appointment) {
        println("SMS sent for new appointment with ID: ${appointment.id}")
    }
}

class DentalClinic {
    private val patients = mutableListOf<Patient>()
    private val dentists = mutableListOf<Dentist>()
    private val appointments = mutableListOf<Appointment>()
    private val observers = mutableListOf<AppointmentObserver>()

    fun addObserver(observer: AppointmentObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: AppointmentObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers(appointment: Appointment) {
        observers.forEach { it.onNewAppointment(appointment) }
    }

    fun addPatient(patient: Patient) {
        patients.add(patient)
    }

    fun addDentist(dentist: Dentist) {
        dentists.add(dentist)
    }

    fun addAppointment(appointment: Appointment) {
        appointments.add(appointment)
        notifyObservers(appointment)
    }

    fun findPatientsByName(name: String): List<Patient> {
        return patients.filter { it.name.contains(name, ignoreCase = true) }
    }

    fun getAppointmentsByDentist(dentistId: Int): List<Appointment> {
        return appointments.filter { it.dentist.id == dentistId }
    }

    fun getUpcomingAppointments(): List<Appointment> {
        return appointments.filter { it.date >= LocalDate.now() }.sortedBy { it.date }
    }

    fun printPatients() {
        patients.forEach {
            println("ID: ${it.id}, Name: ${it.name}, DOB: ${it.dateOfBirth}, Age: ${it.calculateAge()}, Phone: ${it.phoneNumber ?: "N/A"}")
        }
    }

    fun printAppointments() {
        appointments.forEach {
            println(
                "Appointment ID: ${it.id}, Patient: ${it.patient.name}, Dentist: ${it.dentist.name}, Date: ${it.date}, Notes: ${it.notes ?: "N/A"}, Services: ${it.services.joinToString()}"
            )
        }
    }
}

fun main() {
    val clinic = DentalClinic()

    // Підписники
    val emailNotifier = EmailNotifier()
    val smsNotifier = SmsNotifier()
    clinic.addObserver(emailNotifier)
    clinic.addObserver(smsNotifier)

    // Додавання пацієнтів
    clinic.addPatient(Patient(1, "John Doe", LocalDate.of(1985, 6, 15), "123-456-7890", "No known allergies"))
    clinic.addPatient(Patient(2, "Jane Smith", LocalDate.of(1990, 12, 5), null, "Diabetic"))

    // Додавання лікарів
    clinic.addDentist(Dentist(1, "Dr. Emily Brown", "Orthodontist"))
    clinic.addDentist(Dentist(2, "Dr. Michael Green", "Endodontist"))

    // Додавання прийому через Builder
    val appointment1 = AppointmentBuilder()
        .setId(1)
        .setPatient(clinic.findPatientsByName("John")[0])
        .setDentist(clinic.getAppointmentsByDentist(1).firstOrNull()?.dentist ?: Dentist(1, "Dr. Emily Brown", "Orthodontist"))
        .setDate(LocalDate.of(2024, 12, 20))
        .setNotes("Routine check-up")
        .addService("Cleaning")
        .build()

    clinic.addAppointment(appointment1)

    // Додавання прийому з декорацією
    val basicAppointment = BasicAppointment(appointment1)
    val decoratedAppointment = WhiteningDecorator(XRayDecorator(basicAppointment))
    println(decoratedAppointment.getDescription())

    // Виведення даних
    println("\nPatients:")
    clinic.printPatients()

    println("\nAppointments:")
    clinic.printAppointments()
}
