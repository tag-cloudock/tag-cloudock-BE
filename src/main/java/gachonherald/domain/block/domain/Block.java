package gachonherald.domain.block.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import gachonherald.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User blocking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User blocked;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}