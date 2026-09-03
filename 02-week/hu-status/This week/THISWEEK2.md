# MVP 1 Backlog Stories & Acceptance Criteria

## Story 1: Multi-Tenant Tenant Context & Security Isolation
* **User Story:** As a backend system, I want every incoming HTTP request to extract and validate the tenant scope (`barbershop_id`) from the JWT token, so that cross-tenant data leaks are programmatically impossible.
* **Acceptance Criteria:**
  1. Given a valid JWT token with a specific `barbershopId` claim, when an authenticated request hits any protected endpoint, then the `TenantContext` ThreadLocal is populated successfully[cite: 1].
  2. Given a request executed within a service method, when a database query is triggered, then it is automatically scoped to filter by the current tenant's `barbershop_id`[cite: 1].
  3. Given the completion of the HTTP request (success or failure), when the request lifecycle ends, then the `TenantContext` is cleared in a `finally` block to prevent thread-pool pollution[cite: 1].

---

## Story 2: Pessimistic Concurrency Lock for Appointment Booking
* **User Story:** As a client or barbershop admin, I want the system to prevent double-bookings at the database level when simultaneous reservation attempts occur, so that schedule integrity is guaranteed.
* **Acceptance Criteria:**
  1. Given two concurrent booking requests for the exact same barber, date, and time slot, when both execute simultaneously, then the database applies a `PESSIMISTIC_WRITE` lock causing one transaction to wait or succeed while the conflicting transaction is safely handled[cite: 1].
  2. Given a successful booking creation, when the record is persisted, then its state is set to `PENDING` or `CONFIRMED` and the chosen slot is marked unavailable for subsequent requests[cite: 1].
  3. Given an attempt to book an already occupied slot, when the write operation executes, then the system rejects it and returns a descriptive conflict error message in Spanish[cite: 1].

---

## Story 3: Asynchronous Notification Dispatch with Graceful Degradation
* **User Story:** As a system administrator, I want notification triggers (FCM push and email) to persist messages in the database before dispatching externally, so that third-party service outages never block core business operations.
* **Acceptance Criteria:**
  1. Given an appointment lifecycle event (e.g., booking confirmation), when the notification is triggered, then a record is immediately written to the `notifications` table[cite: 1].
  2. Given that the notification is persisted, when the external Firebase Cloud Messaging (FCM) or SMTP service fails or times out, then the main booking transaction still commits successfully without rolling back[cite: 1].
  3. Given a successful database persistence, when the user opens the mobile application inbox, then they can view their notification history regardless of push delivery success[cite: 1].

---

## Story 4: Loyalty Sticker Accumulation and Automatic Reward Coupon Generation
* **User Story:** As a barber or admin, I want to grant loyalty stickers upon completing a service and automatically issue a 100% discount coupon when the threshold is reached, so that client retention workflows are fully automated.
* **Acceptance Criteria:**
  1. Given a completed appointment, when an admin or barber grants a sticker, then the client's `loyalty_cards` sticker count increments by 1 for that specific barbershop[cite: 1].
  2. Given that the sticker count reaches the configured `stickers_required` threshold, when the reward is redeemed, then an `ACTIVE` `RewardCoupon` is generated automatically for the client[cite: 1].
  3. Given an active reward coupon, when the client creates their next booking at that barbershop, then the `priceAtBooking` is automatically set to `0` and the coupon status updates to `USED`[cite: 1].