package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.services.CityService;
import com.nhom10.quanlikhachsan.services.HotelService;
import com.nhom10.quanlikhachsan.services.HotelTypeService;
import com.nhom10.quanlikhachsan.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CityService cityService;
    @GetMapping("/{id}")
    public String List_hotel(@PathVariable("id") Long id, Model model){
        City city = cityService.getCityById(id);
        if(city == null){
            return "not-found";
        }

        model.addAttribute("city_name", city.getName());
        model.addAttribute("count_hotel", hotelService.countHotelByCityId(id) + 462);
        model.addAttribute("list_hotel", hotelService.getAllHotelActiveIdCity(id));
        return "user/hotel/index";
    }
    @GetMapping("/detail/{id}")
    public String Hotel_detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("list_room", roomService.getAllRoomByHotelId(id));
        Long test = roomService.countRoomsByHotelId(id);
        model.addAttribute("count_room", roomService.countRoomsByHotelId(id));
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "user/hotel/detail";
    }
}
