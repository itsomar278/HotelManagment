package Omar.HotelWebServer.dataAccess.model.classes;

import Omar.HotelWebServer.dataAccess.model.enums.PaymentMethod;
import Omar.HotelWebServer.dataAccess.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @NotNull
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
}
