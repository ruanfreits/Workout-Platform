// package com.example.file_storage_api.Model;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;

// @Entity
// @Table(name="divisao_exercicios")
// public class DivisaoExercicios {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long divisaoExercisiosId;

//     @ManyToOne
//     @JoinColumn(name = "AlunoId", nullable = false)
//     private Long AlunoId;

//     @ManyToOne(optional = false)
//     @JoinColumn(name = "exerciseId", nullable = false)
//     private Long exerciseId;

//     @Column(nullable = false)
//     @Enumerated(EnumType.STRING)
//     private DiadaSemana DiaDaSemana;


//     public DivisaoExercicios(){
//     }

//     public DivisaoExercicios(Long AlunoId, Long exerciseId, DiadaSemana DiaDaSemana){
//         this.AlunoId = AlunoId;
//         this.exerciseId = exerciseId;
//         this.DiaDaSemana = DiaDaSemana;
//     }

//     public Long getId() {
//         return divisaoExercisiosId;
//     }

//     public void setId(Long divisaoExercisiosId) {
//         this.divisaoExercisiosId = divisaoExercisiosId;
//     }

//     public Long getAlunoId() {
//         return AlunoId;
//     }

//     public void setAlunoId(Long AlunoId) {
//         this.AlunoId = AlunoId;
//     }

//     public Long getexerciseId() {
//         return divisaoExercisiosId;
//     }

//     public void setexerciseId(Long exerciseId) {
//         this.exerciseId = exerciseId;
//     }

//     public DiadaSemana getDiaDaSemana(){
//         return DiaDaSemana;
//     }

//     public void setDiaDaSemana(DiadaSemana DiadaSemana){
//         this.DiaDaSemana = DiadaSemana;
//     }
// }
