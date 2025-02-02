package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Computer;
import repository.ComputerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    public Optional<Computer> getComputerById(Long id) {
        return computerRepository.findById(id);
    }

    public Computer saveComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    public void deleteComputer(Long id) {
        computerRepository.deleteById(id);
    }
}