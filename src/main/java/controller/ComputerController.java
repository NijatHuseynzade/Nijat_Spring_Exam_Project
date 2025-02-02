package controller;

import model.Computer;
import repository.ComputerRepository;
import service.ComputerService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


}