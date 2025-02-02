package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import model.Computer;
import service.ComputerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/computers")
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public List<Computer> getAllComputers() {
        return computerService.getAllComputers();
    }

    @GetMapping("/{id}")
    public Optional<Computer> getComputerById(@PathVariable Long id) {
        return computerService.getComputerById(id);
    }

    @PostMapping
    public Computer addComputer(@RequestBody Computer computer) {
        return computerService.saveComputer(computer);
    }

    @DeleteMapping("/{id}")
    public String deleteComputer(@PathVariable Long id) {
        computerService.deleteComputer(id);
        return "Компьютер удалён!";
    }
}