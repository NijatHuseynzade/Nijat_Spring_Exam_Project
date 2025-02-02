package model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "computers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand; // Марка

    @Column(nullable = false)
    private String model; // Модель

    @Column(nullable = false)
    private double price; // Цена

    @Column(nullable = false)
    private String specifications; // Характеристики (JSON-формат или просто текст)

    @Column(nullable = false)
    private boolean inStock; // Наличие

    @Column(length = 1000)
    private String imageUrl; // Ссылка на картинку

}