package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public String List_hotel(@PathVariable("id") Long id, Model model, HttpSession session){
        City city = cityService.getCityById(id);
        if(city == null){
            return "client/error/405";
        }
        if(city.getActive() == false){
            return "client/error/405";
        }

        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        if(checkin != null && checkout != null){
            model.addAttribute("checkin", checkin);
            model.addAttribute("checkout", checkout);
        }
        model.addAttribute("city_name", city.getName());
        model.addAttribute("count_hotel", hotelService.countHotelByCityId(id) + 462);
        model.addAttribute("list_hotel", hotelService.getAllHotelActiveIdCity(id));
        return "client/hotel/index";
    }
    @PostMapping("/search2")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam("ngayden") LocalDate checkin,
                         @RequestParam("ngaydi") LocalDate checkout, HttpSession session,
                         RedirectAttributes redirectAttributes){

        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);
        City city = cityService.searchCity(keyword);
        if(city == null){
            return "client/error/405";
        }
        redirectAttributes.addAttribute("id", city.getId());
        return "redirect:/hotel/{id}";
    }
    @GetMapping("/detail/{id}")
    public String Hotel_detail(@PathVariable("id") Long id, Model model, HttpSession session){

        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        if(checkin != null && checkout != null){
            model.addAttribute("checkin", checkin);
            model.addAttribute("checkout", checkout);

        }

        model.addAttribute("list_room", roomService.getAllRoomByHotelId(id));
        Long test = roomService.countRoomsByHotelId(id);
        model.addAttribute("count_room", roomService.countRoomsByHotelId(id));
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "client/hotel/detail";
    }
    @PostMapping("/search-detail")
    public String searchDetail(@RequestParam("ngayden") LocalDate checkin,
                               @RequestParam("ngaydi") LocalDate checkout, HttpSession session,
                               RedirectAttributes redirectAttributes){

        return "";
    }

    @GetMapping("/confirm/{id}")
    public String Confirm_info(@PathVariable("id") Long id, Model model,HttpSession session){
        //get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //get date
        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        User user = userService.findUserByUserName(username);
        Room room = roomService.getRoomById(id);
        Hotel hotel = hotelService.getHotelByIdRoom(room.getHotel().getId());
        City city = cityService.getCityByIdHotel(hotel.getCity().getId());

        long numDay = ChronoUnit.DAYS.between(checkin, checkout);
        double totalMoney =numDay * room.getRent();

        model.addAttribute("user", user);
        model.addAttribute("city_name", city.getName());
        model.addAttribute("room", room);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("totalMoney", totalMoney);
        return "client/hotel/confirm";
    }

}
