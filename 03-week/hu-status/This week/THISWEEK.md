# Modelado del Bounded Context: Appointment (Citas y Horarios)

A continuación se presenta el modelado detallado para el contexto delimitado de Citas en **BarberSaaS**[cite: 1]:

## 1. Aggregate Root (Raíz de Agregado)
* **`Appointment` (Cita):** Es la raíz de agregado principal que controla todo el ciclo de vida de la reserva, asegurando que las reglas de negocio, los estados y los bloqueos concurrentes se mantengan consistentes.

---

## 2. Entities & Value Objects (Entidades y Objetos de Valor)
* **Entidades:**
  * `Appointment` (Raíz): Posee identidad única (`id`) y mantiene referencias por ID al cliente, barbero, servicio y barbería (`barbershop_id`)[cite: 1].
* **Value Objects (Objetos Inmutables):**
  * `TimeSlot` (Franja Horaria): Compuesto por `startTime` y `endTime`. Define el espacio temporal que ocupa la cita.
  * `AppointmentStatus` (Enum de Estado): Representa el ciclo de vida de la reserva (`PENDING`, `CONFIRMED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`, `NO_SHOW`)[cite: 1].
  * `PriceDetails` (Detalles de Precio): Contiene el `priceAtBooking` (precio capturado en el momento de la reserva)[cite: 1] y el indicador de si se aplicó un `RewardCoupon` gratuito.

---

## 3. Invariants (Invariantes / Reglas de Negocio a Proteger)
1. **Anti-Double-Booking (Cero Solapamientos):** Dos citas para el mismo barbero (`barber_id`) no pueden solaparse en el mismo intervalo de tiempo (`TimeSlot`). Esta invariante se protege físicamente mediante un bloqueo pesimista a nivel de base de datos (`PESSIMISTIC_WRITE`)[cite: 1].
2. **Restricción de Cancelación:** Una cita solo puede ser cancelada por el cliente si el tiempo actual respeta la política de horas de cancelación configurada por la barbería (`cancellationPolicyHours`)[cite: 1].
3. **Coherencia de Cupones:** Si se utiliza un cupón de recompensa (`RewardCoupon`), el `priceAtBooking` debe establecerse automáticamente en `0` y el cupón no puede ser reutilizado[cite: 1].
4. **Aislamiento Multi-Tenant:** Ninguna cita puede ser creada, leída o modificada si su `barbershop_id` no coincide con el contexto del inquilino autenticado (`TenantContext`)[cite: 1].

---

## 4. Domain Events Emitted (Eventos de Dominio Emitidos)
Cuando ocurren cambios de estado significativos, el agregado emite eventos para que otros contextos (como `Loyalty` o `Notification`) reaccionen de forma asíncrona:
* **`AppointmentCreatedEvent`:** Emitido al reservar exitosamente una cita. Desencadena el envío de notificaciones de confirmación al cliente y al barbero[cite: 1].
* **`AppointmentCancelledEvent`:** Emitido cuando el cliente o administrador cancela la cita. Permite liberar la franja horaria y notificar a las partes[cite: 1].
* **`AppointmentCompletedEvent`:** Emitido cuando la cita pasa al estado de completada. Este evento es utilizado por el contexto de `Loyalty` para permitir la acumulación de un nuevo *sticker* al cliente[cite: 1].