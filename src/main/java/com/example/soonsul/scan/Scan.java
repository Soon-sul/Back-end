package com.example.soonsul.scan;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.LiquorInfo;
import com.example.soonsul.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="scan")
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scan_id", nullable = false, unique = true)
    private Long scanId;

    @Column(name = "scanned_date", nullable = false)
    private LocalDate scannedDate;


    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;
}
