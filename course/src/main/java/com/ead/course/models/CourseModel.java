package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 250)
    private String description;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @Column(nullable = false)
    private UUID userInstructor;

    //Só vai mostrar esta campo durante uma atualização/criação
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //O direcionamento é bi-direcional com a entidade Module e é mapeado. Mesmo que não fosse mapeado essa relação seria
    //criada automatícamente criando tabelas extras para gerenciar este relacionamento, gerando queries desnecessárias
    //Carregamento Lazy é o mais indicado pois não carrega todos os dados dos relacionamento de uma vez, porém, não
    //pode ser alterado em tempo de execução
    //@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    //O FetchMode define como será retornado os dados dos relacionamentos, se for defino como JOIN o FetchType.LAZY será
    //ignorado, se for o SELECT será feito um select para cada item da sub-consulta e o SUBSELECT faz a consulta pela
    // entidade pai e uma para retornar todos os filhos
    @Fetch(FetchMode.SUBSELECT)
    //Set não é ordenado, não permite duplicados e permite o retorno de todas associações de uma vez
    //O List não permite isso e pode gerar multiplas queries durante uma atualização
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ModuleModel> modules;
}
