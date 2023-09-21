package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/support")
public class SupportingController {
    @GetMapping
    public Long sumNumber() {
        long start = System.currentTimeMillis();
        long sum =  Stream.iterate(1L, a -> a+1)
                .limit(1_000_000L)
                .reduce(0L, (a, b) -> a + b );
        long finish = System.currentTimeMillis();
        System.out.println(finish-start);
        return sum;
    }



}
