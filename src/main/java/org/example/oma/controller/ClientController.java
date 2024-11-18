    package org.example.oma.controller;
    
    import jakarta.servlet.http.HttpSession;
    import org.example.oma.model.Client;
    import org.example.oma.service.ClientService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    
    import java.util.Optional;
    
    @Controller
    @RequestMapping("/client")
    public class ClientController {
        private final ClientService clientService;
    
        @Autowired
        public ClientController(ClientService clientService){
            this.clientService = clientService;
        }
    
        @GetMapping("/create")
        public String showCreateClientForm(Model model){
            model.addAttribute("client", new Client());
            return "client-form";
        }
    
        @PostMapping("/create")
        public String createClient(@ModelAttribute("client") Client client,
                                   RedirectAttributes redirectAttributes, HttpSession session){
            try{
                clientService.saveClient(client);
                session.setAttribute("clientId", client.getId());
                redirectAttributes.addFlashAttribute("message", "Клиент успешно зарегистрирован!");
                redirectAttributes.addFlashAttribute("messageType", "success");
            }
            catch (Exception e){
                redirectAttributes.addFlashAttribute("message", "произошла ошибка при создании");
                redirectAttributes.addFlashAttribute("messageType", "error");
            }

            return "redirect:/appointment/select-client";
        }
//
//        @PostMapping("/create")
//        public String createClient(@ModelAttribute("client") Client client){
//            clientService.saveClient(client);
//            return "redirect:/appointment/check";
//        }
    
        @GetMapping("/all")
        public String getAllClients(Model model){
            model.addAttribute("clients", clientService.getAllClients());
            return "client-list";
        }
    

    
        @GetMapping("/edit/{id}")
        public String showUpdateClientForm(@PathVariable("id") Long id, Model model){
            Optional<Client> client = clientService.getClientById(id);
            if(client.isPresent()){
                model.addAttribute("client", client.get());
                return "client-edit-form";
            } else {
                // Возвращаемся на страницу со списком клиентов или на страницу ошибки
                return "redirect:/client/all"; // или возвращаем страницу с ошибкой
            }
        }
    
    
        @PostMapping("/edit/{id}")
        public String updateClient(@PathVariable("id") Long id, @ModelAttribute("client")Client client){
            clientService.updateClient(id,client);
            return "redirect:/client/all";
        }
    
    
        @PostMapping("/delete/{id}")
        public String deleteClient(@PathVariable("id") Long id) {
            clientService.deleteClient(id);
            return "redirect:/client/all";
        }
    
    }
