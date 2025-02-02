package controller;

import model.Computer;
import model.Order;
import repository.ComputerRepository;
import repository.OrderRepository;
import service.ComputerService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my-computers")
public class ComputerController {

    private final ComputerRepository computerRepository;

    public ComputerController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @GetMapping
    public String myComputers(Model model) {
        model.addAttribute("computers", computerRepository.findAll());
        return "my-computers";
    }

    @PostMapping("/add")
    public String addComputer(@RequestParam String name, @RequestParam String image, @RequestParam double price) {
        computerRepository.save(new Computer(null, name, image, price));
        return "redirect:/my-computers";
    }

    @GetMapping("/delete/{id}")
    public String deleteComputer(@PathVariable Long id) {
        computerRepository.deleteById(id);
        return "redirect:/my-computers";
    }
    
    @PostMapping("/edit")
    public String editComputer(@RequestParam Long id, @RequestParam String name, 
                               @RequestParam String image, @RequestParam double price) {
        Computer computer = computerRepository.findById(id).orElseThrow();
        computer.setName(name);
        computer.setImage(image);
        computer.setPrice(price);
        computerRepository.save(computer);
        return "redirect:/my-computers";
    }
    
    @GetMapping("/buy-computers")
    public String showBuyComputersPage(Model model) {
        List<Computer> computers = computerRepository.findAll();
        model.addAttribute("computers", computers);
        return "buy-computers";
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        List<Computer> cart = (List<Computer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long id, HttpSession session) {
        List<Computer> cart = (List<Computer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        Computer computer = computerRepository.findById(id).orElseThrow();
        cart.add(computer);
        session.setAttribute("cart", cart);
        return "redirect:/buy-computers";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id, HttpSession session) {
        List<Computer> cart = (List<Computer>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(computer -> computer.getId().equals(id));
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
    
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public String placeOrder(@RequestParam String customerName, 
                             @RequestParam String email, 
                             @RequestParam String address, 
                             HttpSession session) {
        List<Computer> cart = (List<Computer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart?error=empty";
        }

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setEmail(email);
        order.setAddress(address);
        order.setComputers(cart);
        
        double totalPrice = cart.stream().mapToDouble(Computer::getPrice).sum();
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        session.removeAttribute("cart"); // Очистка корзины после заказа
        return "redirect:/order-success";
    }
    
    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }



}