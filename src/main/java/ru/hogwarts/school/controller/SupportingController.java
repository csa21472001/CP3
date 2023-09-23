package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import ru.hogwarts.school.service.ThreadService;
//import ru.hogwarts.school.service.ThreadServiceImpl;

import java.util.stream.Stream;

@RestController
@RequestMapping("/support")
public class SupportingController {

//    private ThreadServiceImpl threadService;
//
//    public SupportingController(ThreadServiceImpl threadService) {
//        this.threadService = threadService;
//    }

    @GetMapping
    public Long sumNumber() {
        long start = System.currentTimeMillis();
        long sum = Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000L)
                .reduce(0L, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return sum;
    }
//
//    @GetMapping("/thread")
//    public void startThreads() {
//        threadService.thread();
//    }
//
//    @GetMapping("/threadSync")
//    public void startSyncThreads() {
//        threadService.threadSync();
//    }

}
