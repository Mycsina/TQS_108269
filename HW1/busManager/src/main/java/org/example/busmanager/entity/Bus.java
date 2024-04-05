package org.example.busmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "bus")
public class Bus {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seats")
    private Integer seat_count;

    @OneToMany(mappedBy = "bus", orphanRemoval = true)
    @ToString.Exclude
    private Set<Seat> seats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bus", orphanRemoval = true)
    @ToString.Exclude
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Bus bus = (Bus) o;
        return getId() != null && Objects.equals(getId(), bus.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
