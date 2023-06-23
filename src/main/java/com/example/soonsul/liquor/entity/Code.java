package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="code")
public class Code {

    @Id
    @Column(name = "code_id", nullable = false, unique = true)
    private String codeId;

    @Column(name = "code_name", nullable = false, unique = true)
    private String codeName;

}
