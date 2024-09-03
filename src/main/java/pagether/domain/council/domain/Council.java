package pagether.domain.council.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pagether.domain.council_item.domain.CouncilItem;
import pagether.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Council {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer councilId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @JsonIgnore
    private User manager;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long heart = 0L;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String operatingHours;

    @Column(nullable = false)
    private String usageGuidelines;

    @Column(nullable = false)
    private Boolean isCouncilSelfManage;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToMany(mappedBy = "council", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CouncilItem> items = new ArrayList<>();
}