package com.angio.angiobackend.api.uploads.entity;

import com.angio.angiobackend.api.uploads.type.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@Entity
@Table(name = "uploads", schema = "public")
public class StaticFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false, columnDefinition="INT default 1")
    private FileType type = FileType.IMAGE;

    @Column(name = "uri", nullable = false)
    private String filename;
}
