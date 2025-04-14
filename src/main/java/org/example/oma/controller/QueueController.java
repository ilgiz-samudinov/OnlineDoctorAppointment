package org.example.oma.controller;

import org.example.oma.model.Queue;
import org.example.oma.model.QueueStatus;
import org.example.oma.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;
    @Autowired
    public QueueController(QueueService queueService){
        this.queueService = queueService;
    }
    @GetMapping("/all")
    public String getAllQueue(Model model){
        List<Queue> queues = queueService.findAllQueue();
        model.addAttribute("queues", queues);
        return "queue-list";
    }

    @PostMapping("/update")
    public String updateStatus(@RequestParam("id") Long id, @RequestParam("newStatus") QueueStatus newStatus) {
        queueService.updateStatus(id, newStatus);
        return "redirect:/queue/all";
    }

}
