package model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String email;
    private String address;

    @ManyToMany
    @JoinTable(
        name = "order_computers",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "computer_id")
    )
    private List<Computer> computers;

    private double totalPrice;
    
    public void setAddress(String address) { this.address = address; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setEmail(String email) { this.email = email; }
    public void setComputers(List<Computer> computers) { this.computers = computers; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

}