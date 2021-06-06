package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertCustomerToCustomerDTO(customerService.saveCustomer(convertCustomerDTOtoCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer c : customerService.getAllCustomers()) {
            customerDTOList.add(convertCustomerToCustomerDTO(c));
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(customerService.getCustomerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeToEmployeeDTO(employeeService.saveEmployee(convertEmployeeDTOtoEmployee(employeeDTO)));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        List<Employee> availableEmployees = employeeService.findEmployeesForService(employeeRequestDTO.getSkills(), employeeRequestDTO.getDate());
        for (Employee e : availableEmployees) {
            employeeDTOS.add(convertEmployeeToEmployeeDTO(e));
        }
        return employeeDTOS;
    }

    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if (customer.getPets() == null) {
            List<Long> petIds = customerDTO.getPetIds();
            if (petIds != null) {
                List<Pet> pets = customer.getPets();
                for (Long petId : petIds) {
                    pets.add(petService.getPet(petId));
                }
                customer.setPets(pets);
            }
        }
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customerDTO.getPetIds() == null) {
            List<Pet> pets = customer.getPets();
            List<Long> petIds = new ArrayList<>();
            for (Pet p : pets) {
                petIds.add(p.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Employee convertEmployeeDTOtoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
}
