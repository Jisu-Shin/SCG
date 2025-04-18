package com.example.SCG.web.controller;


import com.example.SCG.client.CustServiceClient;
import com.example.SCG.client.BookingServiceClient;
import com.example.SCG.client.ItemServiceClient;
import com.example.SCG.web.dto.BookingCreateRequestDto;
import com.example.SCG.web.dto.BookingListResponseDto;
import com.example.SCG.web.dto.BookingSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceClient bookingServiceClient;
    private final ItemServiceClient itemServiceClient;
    private final CustServiceClient custServiceClient;

    @GetMapping("")
    public String bookingList(Model model, @ModelAttribute("bookingSearch") BookingSearch bookingSearch) {
        log.info("######bookingController 진입");

        model.addAttribute("bookings", bookingServiceClient.searchBooking(bookingSearch));
        model.addAttribute("bookingSearch", bookingSearch);
        return "booking-getList";
    }

    @GetMapping("/new")
    public String createBookingForm(Model model) {
        log.info("######bookingController.createBookingForm 진입");

        model.addAttribute("custs", custServiceClient.getCustAll());
        model.addAttribute("items", itemServiceClient.getItemAll());

        return "booking-createForm";
    }

    @PostMapping("/new")
    public Mono<String> booking(BookingCreateRequestDto requestDto) {
        return bookingServiceClient.book(requestDto)
                .map(i->"redirect:/")
                .onErrorReturn("booking-createForm");
    }
}
